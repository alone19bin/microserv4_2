package individuals.personservice.mapper;



import individuals.common.dto.IndividualDto;
import individuals.personservice.model.Individual;
import individuals.personservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IndividualMapper {

    @Mapping(target = "userId", expression = "java(individual.getUser() != null ? individual.getUser().getId() : null)")
    @Mapping(target = "email", expression = "java(individual.getUser() != null ? individual.getUser().getEmail() : null)")
    @Mapping(target = "firstName", expression = "java(individual.getUser() != null ? individual.getUser().getFirstName() : null)")
    @Mapping(target = "lastName", expression = "java(individual.getUser() != null ? individual.getUser().getLastName() : null)")
    @Mapping(target = "passportNumber", source = "passportNumber")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    IndividualDto toDto(Individual individual);

    default Individual toEntity(IndividualDto individualDto) {
        if (individualDto == null) return null;

        Individual individual = new Individual();
        individual.setId(individualDto.getUserId());

        // Создаем объект User
        User user = new User();
        user.setId(individualDto.getUserId());
        user.setEmail(individualDto.getEmail());
        user.setFirstName(individualDto.getFirstName());
        user.setLastName(individualDto.getLastName());

        individual.setUser(user);
        individual.setPassportNumber(individualDto.getPassportNumber());
        individual.setPhoneNumber(individualDto.getPhoneNumber());

        return individual;
    }
}