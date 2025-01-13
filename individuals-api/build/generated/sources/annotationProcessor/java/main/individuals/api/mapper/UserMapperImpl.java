package individuals.api.mapper;

import individuals.api.entity.UserEntity;
import individuals.common.dto.UserDto;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T08:43:18+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        if ( entity.getId() != null ) {
            userDto.setId( entity.getId().toString() );
        }
        userDto.setFirstName( entity.getFirstName() );
        userDto.setLastName( entity.getLastName() );
        userDto.setEmail( entity.getEmail() );

        return userDto;
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        if ( dto.getId() != null ) {
            userEntity.setId( UUID.fromString( dto.getId() ) );
        }
        userEntity.setEmail( dto.getEmail() );
        userEntity.setFirstName( dto.getFirstName() );
        userEntity.setLastName( dto.getLastName() );

        return userEntity;
    }
}
