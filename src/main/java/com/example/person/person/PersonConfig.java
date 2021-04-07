package com.example.person.person;

import com.example.person.person.PersonRepository;
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

            repository.saveAll(
                    List.of(victoria
                            )
            );
        };
    }
}