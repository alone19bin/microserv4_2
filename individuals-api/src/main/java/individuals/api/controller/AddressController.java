package individuals.api.controller;

import individuals.api.entity.AddressEntity;
import individuals.api.service.AddressService;
import individuals.common.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;


            //Создание нового адреса
    @PostMapping
    public ResponseEntity<AddressEntity> createAddress(@RequestBody AddressDto addressDto) {
        AddressEntity createdAddress = addressService.createAddress(addressDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    // Обновление адреса
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressEntity> updateAddress(
        @PathVariable UUID addressId, 
        @RequestBody AddressDto addressDto
    ) {
        AddressEntity updatedAddress = addressService.updateAddress(addressId, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }



    //архивация адреса
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> archiveAddress(@PathVariable UUID addressId) {
        addressService.archiveAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}