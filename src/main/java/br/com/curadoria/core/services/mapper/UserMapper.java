package br.com.curadoria.core.services.mapper;

import br.com.curadoria.adapter.http.dto.UserDTO;
import br.com.curadoria.core.entities.Role;
import br.com.curadoria.core.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    @Mapping(source = "senha", target = "password")
    @Mapping(target = "roles", ignore = true)
    User map(UserDTO dto);

    @Mapping(source = "password", target = "senha")
    @Mapping(source = "roles", target = "roles")
    UserDTO map(User entity);

    default List<String> map(Set<Role> roles) {
        List<String> sRoles = new ArrayList<>();

        if(roles != null)
            for (GrantedAuthority role : roles)
                sRoles.add(role.getAuthority());

        return sRoles;
    }
}
