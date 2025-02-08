package individuals.personservice.service;
import individuals.common.dto.AddressDto;
import individuals.common.dto.CountryDto;
import individuals.personservice.entity.Country;
import individuals.personservice.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final CountryRepository countryRepository;

    @Transactional
    public AddressDto createAddress(AddressDto addressDto) {
        Country country = countryRepository.findById(addressDto.getCountry().getId())
                .orElseThrow(() -> new RuntimeException("Страна не найдена"));

        return AddressDto.builder()
                .id(UUID.randomUUID())
                .country(CountryDto.builder()
                        .id(country.getId())
                        .name(country.getName())
                        .build())
                .address(addressDto.getAddress())
                .build();
    }
}