package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.PlanoAssinaturaDTO;
import br.com.curadoria.adapter.http.dto.UserDTO;
import br.com.curadoria.core.entities.*;
import br.com.curadoria.core.ports.projections.UserDetailsProjection;
import br.com.curadoria.core.ports.repositories.*;
import br.com.curadoria.core.services.helpers.ValidadorEmailHelper;
import br.com.curadoria.core.services.mapper.UserMapper;
import com.amazonaws.services.pinpoint.model.BadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements UserDetailsService {

	public static final long ROLE_ADMIN = 1l;
	public static final long ROLE_LOJAS = 2l;
	public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado.";

	@Autowired
	private UserRepository repository;

	@Autowired
	private SenhaService senhaService;

	@Autowired
	private AppleService appleService;

	@Autowired
	private UserMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.isEmpty())
			throw new UsernameNotFoundException("Email não encontrado.");

		Date dataExpiracao = result.get(0).getDataExpiracaoAssinatura();
		List<GrantedAuthority> authorities = result.stream()
				.map(p -> new SimpleGrantedAuthority(p.getAuthority()))
				.collect(Collectors.toList());

		return new CustomUserDetails(
				result.get(0).getUsername(),
				result.get(0).getPassword(),
				authorities,
				dataExpiracao
		);
	}

	public User authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			return repository.findByEmail(username).get();
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuário inválido.");
		}
	}

	@Transactional(readOnly = true)
	public UserDTO getClient() {
		User entity = authenticated();
		if (entity.hasRole("ROLE_LOJAS") || !entity.hasRole("ROLE_ADMIN"))
			throw new UsernameNotFoundException("Usuário não é um adminstrador do sistema.");

		return mapper.map(entity);
	}

	@Transactional
	public UserDTO postLoja(UserDTO dto) {
		User entity = mapper.map(dto);

		entity.setPassword(senhaService.encriptarSenha(entity.getPassword()));
		if (!ValidadorEmailHelper.validaEmail(entity.getEmail()))
			throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);

		Set roles = new HashSet<Role>();
		roles.add(Role.builder().id(ROLE_LOJAS).build());
		entity.setRoles(roles);
		entity.setId(java.util.UUID.randomUUID().toString());

		entity = repository.save(entity);
		return mapper.map(entity);
	}

	public void postEfetivarPlanoAssinatura(PlanoAssinaturaDTO dto) throws JsonProcessingException {
		if(appleService.validaReciboApple(dto.getAppleUserId(), dto.getReceipt()))
			repository.atualizaPlanoAssinatura(dto.getUserId(), dto.getQtdDias(), dto.getAppleUserId());
		else
			throw new BadRequestException("Plano de assinatura não validado.");
	}
}
