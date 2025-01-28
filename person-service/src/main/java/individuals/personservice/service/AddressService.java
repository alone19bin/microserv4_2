package individuals.api.service;

import individuals.api.entity.AddressEntity;
import individuals.api.entity.CountryEntity;
import individuals.api.repository.AddressRepository;
import individuals.api.repository.CountryRepository;
import individuals.common.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;

    @Transactional
    public AddressEntity createAddress(AddressDto addressDto) {
        AddressEntity address = new AddressEntity();

        //   проверка страны
        if (addressDto.getCountry() != null && addressDto.getCountry().getId() != null) {
            CountryEntity country = countryRepository.findById(addressDto.getCountry().getId())
                    .orElseThrow(() -> new RuntimeException("Страна не найдена"));
            address.setCountry(country);
        }

        address.setAddress(addressDto.getAddress());
        address.setZipCode(addressDto.getPostalCode());  //
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());

        address.setCreated(LocalDateTime.now());
        address.setUpdated(LocalDateTime.now());
        address.setArchived(LocalDateTime.now().plusYears(10));

        return addressRepository.save(address);
    }

    @Transactional
    public AddressEntity updateAddress(UUID addressId, AddressDto addressDto) {
        AddressEntity existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Адрес не найден"));

        // Безопасное обновление страны
        if (addressDto.getCountry() != null && addressDto.getCountry().getId() != null) {
            CountryEntity country = countryRepository.findById(addressDto.getCountry().getId())
                    .orElseThrow(() -> new RuntimeException("Страна не найдена"));
            existingAddress.setCountry(country);
        }

        Optional.ofNullable(addressDto.getAddress()).ifPresent(existingAddress::setAddress);
        Optional.ofNullable(addressDto.getPostalCode()).ifPresent(existingAddress::setZipCode);
        Optional.ofNullable(addressDto.getCity()).ifPresent(existingAddress::setCity);
        Optional.ofNullable(addressDto.getState()).ifPresent(existingAddress::setState);

        existingAddress.setUpdated(LocalDateTime.now());

        return addressRepository.save(existingAddress);
    }

    @Transactional
    public void archiveAddress(UUID addressId) {
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Адрес не найден"));

        address.setArchived(LocalDateTime.now());
        addressRepository.save(address);
    }
}