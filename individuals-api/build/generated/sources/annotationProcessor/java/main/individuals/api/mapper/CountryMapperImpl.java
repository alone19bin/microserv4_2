package individuals.api.mapper;

import individuals.api.entity.CountryEntity;
import individuals.common.dto.CountryDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T08:43:18+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public CountryDto toDto(CountryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CountryDto countryDto = new CountryDto();

        countryDto.setId( entity.getId() );
        countryDto.setName( entity.getName() );
        countryDto.setAlpha2( entity.getAlpha2() );
        countryDto.setAlpha3( entity.getAlpha3() );
        countryDto.setCreated( entity.getCreated() );
        countryDto.setUpdated( entity.getUpdated() );
        countryDto.setStatus( entity.getStatus() );

        return countryDto;
    }

    @Override
    public CountryEntity toEntity(CountryDto dto) {
        if ( dto == null ) {
            return null;
        }

        CountryEntity countryEntity = new CountryEntity();

        countryEntity.setId( dto.getId() );
        countryEntity.setCreated( dto.getCreated() );
        countryEntity.setUpdated( dto.getUpdated() );
        countryEntity.setName( dto.getName() );
        countryEntity.setAlpha2( dto.getAlpha2() );
        countryEntity.setAlpha3( dto.getAlpha3() );
        countryEntity.setStatus( dto.getStatus() );

        return countryEntity;
    }
}
