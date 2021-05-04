package person.person;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPerson() {
        log.info("Returning getPerson");
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(long personId) {
        Optional<Person> existsById = personRepository.findById(personId);
        if(existsById.isEmpty()) {
            throw new IllegalStateException("Person with ID "+ personId + "does not exists");
        }
        log.info("Returning getPersonById");
        return personRepository.findById(personId);
    }


    public Person addNewPerson (Person person) {
        Optional<Person> existsByEmail = personRepository.findPersonByEmail(person.getEmail());
        if (existsByEmail.isPresent()) {
            throw new IllegalStateException("Email: " + person.getEmail() + " taken");
        }
        log.info("Saving a new person");
        personRepository.save(person);
        log.info("Returning: Person " + person.getName() + " added!");
        return person;
    }

    public Long deletePerson(Long personId) {
        boolean exists = personRepository.existsById(personId);
        if (!exists) {
            throw new IllegalStateException(
                    "person with id "+ personId + "does not exists");
        }
        log.info("Deleting person!!");
        personRepository.deleteById(personId);
        return personId;
    }

    public Optional<Person> getPersonById(Long personId) {
        boolean exists = personRepository.existsById(personId);
        if(!exists) {
            throw new IllegalStateException("book with id " + personId + " does not exists");
        }
        return personRepository.findById(personId);
    }

    @Transactional
    public List<Person> updatePerson(Long personId, String name, String email) {

        Person person = personRepository.findById(personId).orElseThrow(
                () -> new IllegalStateException("Person With Id " + personId + " does not exists")
        );

        if (name != null && name.length() > 0) {
            person.setName(name);
        }

        if (email != null && email.length() > 0) {
            Optional<Person> existsByEmail = personRepository.findPersonByEmail(email);
            if (existsByEmail.isPresent()) {
                throw new IllegalStateException("Email: " + email + " taken");
            } else {
                log.info("Updating person email");
                person.setEmail(email);
            }
        }
        log.info("Returning: Person with id " + personId + "updated!");
        return List.of(person);
    }
}
