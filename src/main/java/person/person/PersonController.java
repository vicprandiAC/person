package person.person;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/person")
@Slf4j
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPerson() {
        log.info("getPerson endpoint was called");
        return personService.getPerson();
    }

    @PostMapping
    public void registerNewPerson(@RequestBody Person person) {
        log.info("registerNewPerson endpoint was called");
        personService.addNewPerson(person);
    }

    @DeleteMapping(path = "{personId}")
    public void deletePerson(
            @PathVariable("personId") Long personId) {
        log.info("deletePerson endpoint was called");
        personService.deletePerson(personId);
    }

    @PutMapping(path = "{personId}")
    public void updatePerson (@PathVariable("personId") long personId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        log.info("updatePerson endpoint was called");
        personService.updatePerson(personId, name,  email);
    }
}
