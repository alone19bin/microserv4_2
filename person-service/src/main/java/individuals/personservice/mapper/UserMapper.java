package individuals.personservice.mapper;



import individuals.common.dto.UserDto;
import individuals.personservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public User toEntity(individuals.common.dto.UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .role(userDto.getRole() != null ? userDto.getRole() : individuals.common.dto.Role.USER)
                .build();
    }

    public individuals.common.dto.UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return individuals.common.dto.UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}