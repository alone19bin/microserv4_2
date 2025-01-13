package individuals.api.mapper;
import individuals.common.dto.CountryDto;
import individuals.api.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {
    CountryDto toDto(CountryEntity entity);
    CountryEntity toEntity(CountryDto dto);
}