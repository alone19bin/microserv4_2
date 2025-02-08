package individuals.personservice.mapper;

import individuals.common.dto.AddressDto;
import individuals.common.dto.CountryDto;
import individuals.personservice.entity.Address;
import individuals.personservice.entity.Country;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T17:21:24+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressDto toDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDto.AddressDtoBuilder addressDto = AddressDto.builder();

        addressDto.country( countryToCountryDto( address.getCountry() ) );
        addressDto.id( address.getId() );
        addressDto.created( address.getCreated() );
        addressDto.updated( address.getUpdated() );
        addressDto.address( address.getAddress() );
        addressDto.zipCode( address.getZipCode() );
        addressDto.archived( address.getArchived() );
        addressDto.city( address.getCity() );
        addressDto.state( address.getState() );

        return addressDto.build();
    }

    @Override
    public Address toEntity(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        Address address = new Address();

        address.setCountry( countryDtoToCountry( addressDto.getCountry() ) );
        address.setId( addressDto.getId() );
        address.setCreated( addressDto.getCreated() );
        address.setUpdated( addressDto.getUpdated() );
        address.setAddress( addressDto.getAddress() );
        address.setZipCode( addressDto.getZipCode() );
        address.setArchived( addressDto.getArchived() );
        address.setCity( addressDto.getCity() );
        address.setState( addressDto.getState() );

        return address;
    }

    protected CountryDto countryToCountryDto(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDto.CountryDtoBuilder countryDto = CountryDto.builder();

        countryDto.id( country.getId() );
        countryDto.name( country.getName() );

        return countryDto.build();
    }

    protected Country countryDtoToCountry(CountryDto countryDto) {
        if ( countryDto == null ) {
            return null;
        }

        Country country = new Country();

        country.setId( countryDto.getId() );
        country.setName( countryDto.getName() );

        return country;
    }
}
