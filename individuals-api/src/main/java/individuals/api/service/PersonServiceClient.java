package individuals.api.service;


import individuals.api.entity.UserEntity;
import individuals.api.mapper.UserMapper;
import individuals.common.dto.PersonDto;
import individuals.common.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PersonServiceClient {
    private final RestTemplate restTemplate;
    private final UserMapper userMapper;

    @Value("${person.service.url}")
    private String personServiceUrl;

    public String saveIndividual(UserEntity user) {
        UserDto userDto = userMapper.toDto(user);
        return restTemplate.postForObject(
                personServiceUrl + "/api/person/save",
                userDto,
                String.class
        );
    }
}


