package individuals.api.controller;

import individuals.api.service.CountryService;
import individuals.common.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    //получение всех стран
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<CountryDto> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }



            //Получение страны по коду
        @GetMapping("/code/{code}")
    public ResponseEntity<CountryDto> getCountryByCode(@PathVariable String code) {
        CountryDto country = countryService.getCountryByCode(code);
        return ResponseEntity.ok(country);
    }

        // создание новой страны
    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
        CountryDto createdCountry = countryService.createCountry(countryDto);
        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
    }


        // обновление информации о стране
    @PutMapping("/{countryId}")
    public ResponseEntity<CountryDto> updateCountry(
            @PathVariable Integer countryId,
            @RequestBody CountryDto countryDto
    ) {
        CountryDto updatedCountry = countryService.updateCountry(countryId, countryDto);
        return ResponseEntity.ok(updatedCountry);
    }

    //деактивация страны
    @DeleteMapping("/{countryId}")
    public ResponseEntity<Void> deactivateCountry(@PathVariable Integer countryId) {
        countryService.deactivateCountry(countryId);
        return ResponseEntity.noContent().build();
    }
}