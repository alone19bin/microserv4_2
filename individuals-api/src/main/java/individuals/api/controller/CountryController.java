package individuals.api.controller;

import individuals.common.dto.CountryDto;
import individuals.personservice.mapper.CountryMapper;
import individuals.personservice.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    private final CountryMapper countryMapper;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok(
                countryService.getAllCountries().stream()
                        .map(countryMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}