package individuals.personservice.mapper;

import individuals.common.dto.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    default Role map(Role role) {
        return role != null ? role : Role.USER;
    }
}