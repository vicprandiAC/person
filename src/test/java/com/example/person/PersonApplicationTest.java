package com.example.person;

import com.example.person.person.Person;
import com.example.person.person.PersonService;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.response.Response;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
// Cria uma porta random pra não conflitar com o servidor.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersonApplicationTests {

    private static String requestBody = "{\n" +
            "  \"name\": \"Victoria\",\n" +
            "  \"age\": \"24\",\n" +
            "  \"career\": \"intern\",\n" +
            "  \"hairColor\": \"brown\",\n" +
            "  \"email\": \"vicprandi@gmail.com\" \n}";


    // Define a variável da porta random feita ali em cima em um int pra usar nos testes.
    @LocalServerPort
    private int port;

    // Define a URI Base
    @PostConstruct
    public void init() {
        baseURI = "http://localhost:" + port + "/";
    }

    // Moca o serviço
    @MockBean
    private PersonService personService;

    @Test
    public void getPersonWhenUsernameAndPasswordAreIncorrectShouldReturnStatus401() {
        System.out.println(port);
        Response response = given()
                       .port(port)
                       .auth()
                       .basic("none", "admin")
                       .contentType("application/json")
                       .when()
                       .get(baseURI + "api/person")
                       .then()
                       .extract()
                       .response();

        Assert.assertEquals(401, response.statusCode());
    }

    @Test
    public void getBooksWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {
        List<Person> person = new ArrayList<Person>();
        person.add(new Person("victoria", 24, "student", "brown", "vicprandi@gmail.com"));
        Mockito.when(personService.getPerson()).thenReturn(person);

        Response response =
                given()
                        .port(port)
                        .auth()
                        .basic("admin", "admin")
                        .contentType(ContentType.JSON)
                        .when()
                        .get(baseURI + "api/person")
                        .then()
                        .extract()
                        .response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(1L, response.jsonPath().getLong("id[0]"));
    }

    @Test
    public void getPersonByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatus200() {
//ARRANGE
        Person person = new Person("victoria", 24, "student", "brown", "vicprandi@gmail.com");
        Mockito.when(personService.getPersonById(1L)).thenReturn(Optional.of(person));
//ACT
        System.out.println(port);
        Response response = (Response) given()
                .port(port)
                .auth()
                .basic("admin", "admin")
                .contentType("application/json")
                .when()
                .get(baseURI + "api/person/" + person.getId())
                .then()
                .extract()
                .response();
//ASSERT
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void verifyIfPathIsIncorrectShouldReturn404() {
        Response response =
                (Response)
                        given()
                                .port(port)
                                .auth()
                                .basic("admin", "admin")
                                .when()
                                .get(baseURI + "api/person")
                                .then()
                                .extract()
                                .response();

        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void getRequest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + "api/person")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void postRequest() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(baseURI + "api/person")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("Victoria", response.jsonPath().getString("name"));
        Assertions.assertEquals("24", response.jsonPath().getString("age"));
        Assertions.assertEquals("intern", response.jsonPath().getString("career"));
        Assertions.assertEquals("brown", response.jsonPath().getString("hairColor"));
        Assertions.assertEquals("vicprandi@gmail.com", response.jsonPath().getString("email"));
    }

}