package com.example.person.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPerson() {
        return personService.getPerson();
    }

    @PostMapping
    public void registerNewPerson(@RequestBody Person person) {
        personService.addNewPerson(person);
    }

    @DeleteMapping(path = "{personId}")
    public void deletePerson(
            @PathVariable("personId") Long personId) {
        personService.deletePerson(personId);
    }

    @PutMapping(path = "{personId}")
    public void updatePerson (@PathVariable("personId") long personId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) Integer age,
                              @RequestParam(required = false) String career,
                              @RequestParam(required = false) String hairColor,
                              @RequestParam(required = false) String email) {
        personService.updatePerson(personId, name, age, career, hairColor, email);
    }
}
