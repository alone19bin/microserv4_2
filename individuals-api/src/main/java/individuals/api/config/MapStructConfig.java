package individuals.api.config;

import individuals.personservice.mapper.IndividualMapper;
import individuals.personservice.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {
    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public IndividualMapper individualMapper() {
        return Mappers.getMapper(IndividualMapper.class);
    }
}