package individuals.personservice.mapper;


import individuals.common.dto.CountryDto;
import individuals.personservice.entity.Country;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CountryMapper {
    CountryDto toDto(Country country);
    Country toEntity(CountryDto countryDto);
}