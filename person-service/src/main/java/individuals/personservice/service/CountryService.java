package individuals.personservice.service;

import individuals.common.dto.CountryDto;
import individuals.personservice.model.Country;
import individuals.personservice.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CountryDto convertToDto(Country country) {
        CountryDto dto = new CountryDto();
        dto.setId(String.valueOf(country.getId()));  // Используем Long ID
        dto.setName(country.getName());
        dto.setCountryCode(country.getCode());
        return dto;
    }
}