package individuals.personservice.mapper;

import individuals.common.dto.AddressDto;
import individuals.common.dto.CountryDto;
import individuals.common.dto.UserDto;
import individuals.personservice.entity.Address;
import individuals.personservice.entity.Country;
import individuals.personservice.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T17:40:13+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.firstName( user.getFirstName() );
        userDto.lastName( user.getLastName() );
        userDto.email( user.getEmail() );
        userDto.address( addressToAddressDto( user.getAddress() ) );
        userDto.created( user.getCreated() );
        userDto.updated( user.getUpdated() );
        userDto.filled( user.getFilled() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setEmail( userDto.getEmail() );
        user.setCreated( userDto.getCreated() );
        user.setUpdated( userDto.getUpdated() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setFilled( userDto.getFilled() );
        user.setAddress( addressDtoToAddress( userDto.getAddress() ) );

        return user;
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

    protected AddressDto addressToAddressDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDto.AddressDtoBuilder addressDto = AddressDto.builder();

        addressDto.id( address.getId() );
        addressDto.created( address.getCreated() );
        addressDto.updated( address.getUpdated() );
        addressDto.country( countryToCountryDto( address.getCountry() ) );
        addressDto.address( address.getAddress() );
        addressDto.zipCode( address.getZipCode() );
        addressDto.archived( address.getArchived() );
        addressDto.city( address.getCity() );
        addressDto.state( address.getState() );

        return addressDto.build();
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

    protected Address addressDtoToAddress(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        Address address = new Address();

        address.setId( addressDto.getId() );
        address.setCreated( addressDto.getCreated() );
        address.setUpdated( addressDto.getUpdated() );
        address.setCountry( countryDtoToCountry( addressDto.getCountry() ) );
        address.setAddress( addressDto.getAddress() );
        address.setZipCode( addressDto.getZipCode() );
        address.setArchived( addressDto.getArchived() );
        address.setCity( addressDto.getCity() );
        address.setState( addressDto.getState() );

        return address;
    }
}
