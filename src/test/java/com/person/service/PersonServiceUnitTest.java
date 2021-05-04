package com.person.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import person.person.Person;
import person.person.PersonRepository;
import person.person.PersonService;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServiceUnitTest {

    @Mock
    private PersonRepository personRepository;
    private AutoCloseable autoCloseable;
    private PersonService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PersonService(personRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllPerson() {
        // when
        underTest.getPerson();

        // then
        verify(personRepository).findAll();
    }


    @Test
    void canGetAllStudents() {
        //when
        underTest.getPerson();

        //then
        Mockito.verify(personRepository).findAll();
    }

    @Test
    void canAddNewPerson() {
        Person person = new Person(
                "Victoria",
                24,
                "student",
                "brown",
                "vicprandi@gmail.com");

        underTest.addNewPerson(person);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        Mockito.verify(personRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        AssertionsForClassTypes.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canUpdatePerson(){
        Person person = new Person(
                "Victoria",
                24,
                "Student",
                "Brown",
                "vicprandi@gmail.com"
        );

        personRepository.save(person);

        String email = "victoriazinha@gmail.com";
        String name = "Victoria";


        BDDMockito.given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        underTest.updatePerson(person.getId(), name, email);

        AssertionsForClassTypes.assertThat(person.getName()).isEqualTo(name);
        AssertionsForClassTypes.assertThat(person.getEmail()).isEqualTo(email);


    }

    @Test
    void canDeleteStudentById() {
        //given
        Person person = new Person(
                "Victoria",
                24,
                "Student",
                "Brown",
                "vicprandi@gmail.com"
        );
        personRepository.save(person);

        //when
        BDDMockito.given(personRepository.existsById(person.getId())).willReturn(true);
        underTest.deletePerson(person.getId());

        //then
        Mockito.verify(personRepository).deleteById(person.getId());
    }


}
