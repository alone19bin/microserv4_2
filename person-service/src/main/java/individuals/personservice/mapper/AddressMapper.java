package individuals.personservice.mapper;

import individuals.common.dto.AddressDto;
import individuals.personservice.entity.Address;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AddressMapper {
    @Mapping(target = "country", source = "country")
    AddressDto toDto(Address address);

    @InheritInverseConfiguration
    Address toEntity(AddressDto addressDto);
}