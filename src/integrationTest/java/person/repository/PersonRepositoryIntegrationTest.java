package person.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import person.person.Person;
import person.person.PersonRepository;
import org.junit.jupiter.api.BeforeEach;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository underTest;

    @BeforeEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void CheckIfPersonEmailExists() {
        Person person = new Person(
                "Victoria",
                24,
                "student",
                "brown",
                "vicprandi@gmail.com");

        underTest.save(person);

        //when
        Optional<Person> expected = underTest.findPersonByEmail(person.getEmail());

        //then
        assertThat(expected.isPresent()).isTrue();
    }

    @Test
    void ItShouldCheckIfPersonDoesNotExistByEmail() {
        Person person = new Person(
                "Victoria",
                24,
                "student",
                "brown",
                "vicprandi@gmail.com");

        underTest.save(person);

        Optional<Person> personByEmail = underTest.findPersonByEmail("testeemail@gmail.com");

        assertThat(personByEmail.isPresent()).isFalse();

        }

    @Test
    void checkIfPersonIdDoesNotExists() {
        //given
        Person person = new Person(
                "Victoria",
                24,
                "student",
                "brown",
                "vicprandi@gmail.com");
        underTest.save(person);

        //when
        boolean expected = underTest.existsById(2l);

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void checkIfPersonIdExists() {
        //given
        Person person = new Person(
                "Victoria",
                24,
                "student",
                "brown",
                "vicprandi@gmail.com");
        underTest.save(person);

        //when
        boolean expected = underTest.existsById(person.getId());

        //then
        assertThat(expected).isTrue();
    }

}


