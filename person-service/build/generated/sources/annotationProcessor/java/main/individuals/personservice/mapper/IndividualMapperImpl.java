package individuals.personservice.mapper;

import individuals.common.dto.IndividualDto;
import individuals.personservice.entity.Individual;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T17:40:13+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class IndividualMapperImpl implements IndividualMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IndividualDto toDto(Individual individual) {
        if ( individual == null ) {
            return null;
        }

        IndividualDto.IndividualDtoBuilder individualDto = IndividualDto.builder();

        individualDto.id( individual.getId() );
        individualDto.user( userMapper.toDto( individual.getUser() ) );
        individualDto.passportNumber( individual.getPassportNumber() );
        individualDto.phoneNumber( individual.getPhoneNumber() );
        individualDto.email( individual.getEmail() );
        individualDto.verifiedAt( individual.getVerifiedAt() );
        individualDto.archivedAt( individual.getArchivedAt() );
        individualDto.status( individual.getStatus() );

        return individualDto.build();
    }

    @Override
    public Individual toEntity(IndividualDto individualDto) {
        if ( individualDto == null ) {
            return null;
        }

        Individual individual = new Individual();

        individual.setId( individualDto.getId() );
        individual.setUser( userMapper.toEntity( individualDto.getUser() ) );
        individual.setPassportNumber( individualDto.getPassportNumber() );
        individual.setPhoneNumber( individualDto.getPhoneNumber() );
        individual.setEmail( individualDto.getEmail() );
        individual.setVerifiedAt( individualDto.getVerifiedAt() );
        individual.setArchivedAt( individualDto.getArchivedAt() );
        individual.setStatus( individualDto.getStatus() );

        return individual;
    }
}
