package individuals.personservice.mapper;

import individuals.common.dto.CountryDto;
import individuals.personservice.entity.Country;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T17:21:24+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public CountryDto toDto(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDto.CountryDtoBuilder countryDto = CountryDto.builder();

        countryDto.id( country.getId() );
        countryDto.name( country.getName() );

        return countryDto.build();
    }

    @Override
    public Country toEntity(CountryDto countryDto) {
        if ( countryDto == null ) {
            return null;
        }

        Country country = new Country();

        country.setId( countryDto.getId() );
        country.setName( countryDto.getName() );

        return country;
    }
}
