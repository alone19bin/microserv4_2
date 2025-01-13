package individuals.api.mapper;

import individuals.api.entity.AddressEntity;
import individuals.api.entity.CountryEntity;
import individuals.common.dto.AddressDto;
import individuals.common.dto.CountryDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T08:43:18+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressDto toDto(AddressEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AddressDto addressDto = new AddressDto();

        addressDto.setId( entity.getId() );
        addressDto.setAddress( entity.getAddress() );
        addressDto.setCity( entity.getCity() );
        addressDto.setState( entity.getState() );
        addressDto.setCountry( countryEntityToCountryDto( entity.getCountry() ) );

        return addressDto;
    }

    @Override
    public AddressEntity toEntity(AddressDto dto) {
        if ( dto == null ) {
            return null;
        }

        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setId( dto.getId() );
        addressEntity.setCountry( countryDtoToCountryEntity( dto.getCountry() ) );
        addressEntity.setAddress( dto.getAddress() );
        addressEntity.setCity( dto.getCity() );
        addressEntity.setState( dto.getState() );

        return addressEntity;
    }

    protected CountryDto countryEntityToCountryDto(CountryEntity countryEntity) {
        if ( countryEntity == null ) {
            return null;
        }

        CountryDto countryDto = new CountryDto();

        countryDto.setId( countryEntity.getId() );
        countryDto.setName( countryEntity.getName() );
        countryDto.setAlpha2( countryEntity.getAlpha2() );
        countryDto.setAlpha3( countryEntity.getAlpha3() );
        countryDto.setCreated( countryEntity.getCreated() );
        countryDto.setUpdated( countryEntity.getUpdated() );
        countryDto.setStatus( countryEntity.getStatus() );

        return countryDto;
    }

    protected CountryEntity countryDtoToCountryEntity(CountryDto countryDto) {
        if ( countryDto == null ) {
            return null;
        }

        CountryEntity countryEntity = new CountryEntity();

        countryEntity.setId( countryDto.getId() );
        countryEntity.setCreated( countryDto.getCreated() );
        countryEntity.setUpdated( countryDto.getUpdated() );
        countryEntity.setName( countryDto.getName() );
        countryEntity.setAlpha2( countryDto.getAlpha2() );
        countryEntity.setAlpha3( countryDto.getAlpha3() );
        countryEntity.setStatus( countryDto.getStatus() );

        return countryEntity;
    }
}
