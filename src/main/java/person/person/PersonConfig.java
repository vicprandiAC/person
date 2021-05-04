package person.person;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class PersonConfig {

    @Bean
    CommandLineRunner commandLineRunner(PersonRepository repository) {
        return args -> {
            Person victoria = new Person(
                    "Victoria",
                    24,
                    "Software Engineer",
                    "Brown",
                    "vicprandi@gmail.com"
            );

            Person rafael = new Person(
                    "Rafael",
                    32,
                    "Software Engineer Senior",
                    "Brown",
                    "rprandi@gmail.com"
            );

            repository.saveAll(
                    List.of(victoria,
                            rafael
                            )
            );
        };
    }
}