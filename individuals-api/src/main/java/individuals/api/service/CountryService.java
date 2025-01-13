package individuals.api.service;

import individuals.api.entity.CountryEntity;
import individuals.api.repository.CountryRepository;
import individuals.common.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    // Получение всех стран
    @Transactional(readOnly = true)
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //получение страны по коду
    @Transactional(readOnly = true)
    public CountryDto getCountryByCode(String code) {
        CountryEntity country = countryRepository.findByAlpha2(code);
        if (country == null) {
            country = countryRepository.findByAlpha3(code);
        }

        if (country == null) {
            throw new RuntimeException("Страна не найдена");
        }

        return convertToDto(country);
    }

    //создание новой страны
    @Transactional
    public CountryDto createCountry(CountryDto countryDto) {
        CountryEntity country = new CountryEntity();
        country.setName(countryDto.getName());
        country.setAlpha2(countryDto.getAlpha2());
        country.setAlpha3(countryDto.getAlpha3());
        country.setStatus("ACTIVE");
        country.setCreated(LocalDateTime.now());
        country.setUpdated(LocalDateTime.now());

        return convertToDto(countryRepository.save(country));
    }

      //обновление информации о стране
    @Transactional
    public CountryDto updateCountry(Integer countryId, CountryDto countryDto) {
        CountryEntity existingCountry = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Страна не найдена"));

        existingCountry.setName(countryDto.getName());
        existingCountry.setAlpha2(countryDto.getAlpha2());
        existingCountry.setAlpha3(countryDto.getAlpha3());
        existingCountry.setUpdated(LocalDateTime.now());

        return convertToDto(countryRepository.save(existingCountry));
    }

            //    Деактивация страны
    @Transactional
    public void deactivateCountry(Integer countryId) {
        CountryEntity country = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Страна не найдена"));

        country.setStatus("INACTIVE");
        country.setUpdated(LocalDateTime.now());
        countryRepository.save(country);
    }

            //          преобразование сущности в DTO
    private CountryDto convertToDto(CountryEntity country) {
        CountryDto dto = new CountryDto();
        dto.setId(country.getId());
        dto.setName(country.getName());
        dto.setAlpha2(country.getAlpha2());
        dto.setAlpha3(country.getAlpha3());
        dto.setCreated(country.getCreated());
        dto.setUpdated(country.getUpdated());
        dto.setStatus(country.getStatus());
        return dto;
    }
}