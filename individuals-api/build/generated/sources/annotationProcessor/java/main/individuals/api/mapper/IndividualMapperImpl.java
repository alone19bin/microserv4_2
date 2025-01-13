package individuals.api.mapper;

import individuals.api.entity.IndividualEntity;
import individuals.common.dto.IndividualDto;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T08:43:18+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class IndividualMapperImpl implements IndividualMapper {

    @Override
    public IndividualDto toDto(IndividualEntity entity) {
        if ( entity == null ) {
            return null;
        }

        IndividualDto individualDto = new IndividualDto();

        if ( entity.getUserId() != null ) {
            individualDto.setUserId( entity.getUserId().toString() );
        }
        individualDto.setPassportNumber( entity.getPassportNumber() );
        individualDto.setPhoneNumber( entity.getPhoneNumber() );
        individualDto.setEmail( entity.getEmail() );

        return individualDto;
    }

    @Override
    public IndividualEntity toEntity(IndividualDto dto) {
        if ( dto == null ) {
            return null;
        }

        IndividualEntity individualEntity = new IndividualEntity();

        if ( dto.getUserId() != null ) {
            individualEntity.setUserId( UUID.fromString( dto.getUserId() ) );
        }
        individualEntity.setPassportNumber( dto.getPassportNumber() );
        individualEntity.setPhoneNumber( dto.getPhoneNumber() );
        individualEntity.setEmail( dto.getEmail() );

        return individualEntity;
    }
}
