package individuals.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"individuals.api", "individuals.common.dto"})
@EntityScan(basePackages = "individuals.api.entity")
@EnableJpaRepositories(basePackages = "individuals.api.repository")
public class IndividualsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndividualsApiApplication.class, args);
    }
}