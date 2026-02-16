package br.com.curadoria.core.ports.repositories;

import java.util.List;
import java.util.Optional;

import br.com.curadoria.core.entities.User;
import br.com.curadoria.core.ports.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

	@Query(nativeQuery = true, value = """
				SELECT tb_user.email AS username, tb_user.password, tb_user.data_expiracao_assinatura AS dataExpiracaoAssinatura, tb_role.id AS roleId, tb_role.authority
				FROM tb_user
				INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
				INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
				WHERE tb_user.email = ?1
			""")
	List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

	Optional<User> findByEmail(String email);

	@Query(nativeQuery = true, value = """
				UPDATE tb_user
				SET apple_user_id = ?3, data_expiracao_assinatura = DATE_ADD(CURDATE(), INTERVAL ?2 DAY)
				WHERE user_id = ?1
			""")
	void atualizaPlanoAssinatura(String userId, Long qtdDias, Integer appleUserId);
}
