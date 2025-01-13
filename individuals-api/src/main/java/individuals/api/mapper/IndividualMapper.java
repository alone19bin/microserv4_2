package individuals.api.mapper;

import individuals.api.entity.IndividualEntity;
import individuals.common.dto.IndividualDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IndividualMapper {
    IndividualDto toDto(IndividualEntity entity);
    IndividualEntity toEntity(IndividualDto dto);
}