package com.example.person.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPerson() {
        return personRepository.findAll();
    }

    public void addNewPerson(Person person) {
        Optional<Person> personOptional = personRepository
                .findPersonByEmail(person.getEmail());
        if (personOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        personRepository.save(person);
    }

    public void deletePerson(Long personId) {
        boolean exists = personRepository.existsById(personId);
        if (!exists) {
            throw new IllegalStateException(
                    "person with id "+ personId + "does not exists");
        }
        personRepository.deleteById(personId);

    }

    @Transactional
    public void updatePerson(Long personId,
                             String name,
                             Integer age,
                             String career,
                             String hairColor,
                             String email) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new IllegalStateException("person with id "+ personId + "does not exists"));

        if(name != null && name.length() > 0) {
            person.setName(name);
        }

        if(age != null && age > 0) {
            person.setAge(age);
        }
        if(career != null && career.length() > 0) {
            person.setCareer(career);
        }
        if(hairColor != null && hairColor.length() > 0) {
            person.setHairColor(hairColor);
        }
        if(email != null && email.length() > 0) {
            person.setEmail(email);
        }


    }

}
