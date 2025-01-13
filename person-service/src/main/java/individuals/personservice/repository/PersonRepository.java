package individuals.personservice.repository;

import individuals.personservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// для взаимодействия с бд  и выполнения операций с персональными данным
@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
}
