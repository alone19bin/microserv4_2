package individuals.api.repository;

import individuals.api.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    CountryEntity findByAlpha2(String alpha2);
    CountryEntity findByAlpha3(String alpha3);
}