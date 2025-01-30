package individuals.personservice.service;

import individuals.common.dto.AddressDto;
import individuals.common.dto.CountryDto;
import individuals.personservice.model.Address;
import individuals.personservice.model.Country;
import individuals.personservice.repository.AddressRepository;
import individuals.personservice.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;

    public AddressDto createAddress(AddressDto addressDto) {
        Country country = countryRepository.findById(UUID.fromString(addressDto.getCountry().getId()))
                .orElseThrow(() -> new EntityNotFoundException("Country not found"));

        Address address = new Address();
        address.setCountryEntity(country);
        address.setAddress(addressDto.getAddress());
        address.setZipCode(addressDto.getZipCode());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCreated(LocalDateTime.now());
        address.setUpdated(LocalDateTime.now());
        address.setArchived(LocalDateTime.now());

        Address savedAddress = addressRepository.save(address);

        return AddressDto.builder()
                .id(savedAddress.getId().toString())
                .address(savedAddress.getAddress())
                .zipCode(savedAddress.getZipCode())
                .city(savedAddress.getCity())
                .state(savedAddress.getState())
                .country(CountryDto.builder()
                        .id(savedAddress.getCountryEntity().getId().toString())
                        .name(savedAddress.getCountryEntity().getName())
                        .build())
                .created(savedAddress.getCreated())
                .updated(savedAddress.getUpdated())
                .archived(savedAddress.getArchived())
                .build();
    }
}