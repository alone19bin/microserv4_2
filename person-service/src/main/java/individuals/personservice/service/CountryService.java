package individuals.personservice.service;

import individuals.personservice.entity.Country;
import individuals.personservice.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Country> findByCode(String code) {
        return countryRepository.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Optional<Country> findByName(String name) {
        return countryRepository.findByName(name);
    }

    @Transactional
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Transactional
    public void delete(Country country) {
        countryRepository.delete(country);
    }
}