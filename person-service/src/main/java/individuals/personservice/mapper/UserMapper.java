package individuals.personservice.mapper;

import individuals.common.dto.UserDto;
import individuals.personservice.entity.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    UserDto toDto(User user);

    @InheritInverseConfiguration
    User toEntity(UserDto userDto);
}