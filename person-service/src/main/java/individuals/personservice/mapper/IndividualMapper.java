package individuals.personservice.mapper;

import individuals.common.dto.IndividualDto;
import individuals.personservice.entity.Individual;
import individuals.personservice.entity.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface IndividualMapper {
    IndividualDto toDto(Individual individual);
    Individual toEntity(IndividualDto individualDto);

                //обновление существующей сущности
    default Individual updateEntityFromDto(IndividualDto dto, @MappingTarget Individual entity) {
        if (dto == null) {
            return entity;
        }

        // Обновление  поля
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setVerifiedAt(dto.getVerifiedAt());
        entity.setArchivedAt(dto.getArchivedAt());
        entity.setStatus(dto.getStatus());

           //Если  надо обновить связанного пользователя
        if (dto.getUser() != null) {
            User user = entity.getUser();
            if (user == null) {
                user = new User();
                entity.setUser(user);
            }
            user.setEmail(dto.getUser().getEmail());
            user.setFirstName(dto.getUser().getFirstName());
            user.setLastName(dto.getUser().getLastName());
        }

        return entity;
    }
}