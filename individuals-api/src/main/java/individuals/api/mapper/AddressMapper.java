package individuals.api.mapper;

import individuals.api.entity.AddressEntity;
import individuals.common.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    AddressDto toDto(AddressEntity entity);
    AddressEntity toEntity(AddressDto dto);
}