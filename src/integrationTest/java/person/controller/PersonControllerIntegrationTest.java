package person.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import person.person.Person;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import person.person.PersonService;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PersonControllerIntegrationTest {

    @Mock
    private Person person, person1, person2;
    private List<Person> personList;

    @LocalServerPort
    private int port;

    @PostConstruct
    void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("Victoria");
        person.setAge(24);
        person.setCareer("student");
        person.setHairColor("brown");
        person.setEmail("vicprandi@gmail.com");

        person1 = new Person("Joao", 21, "engineer", "brown", "joao@gmail.com");
        person2 = new Person("Maria", 22, "student", "brown", "maria@gmail.com");

        personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);

        RestAssured.baseURI = "http://localhost:" + port + "/";
    }

    @MockBean
    private PersonService personService;

    @Test
    public void anyMethodWhenPathIsIncorrectShouldReturnStatus404() {
        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","admin")
                .contentType(ContentType.JSON)
                .when()
                .get(RestAssured.baseURI + "api/v1/persontest")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void getAllPersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {
        Mockito.when(personService.getPerson()).thenReturn(personList);

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","admin")
                .contentType(ContentType.JSON)
                .when()
                .get(RestAssured.baseURI + "api/v1/person/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void getAllStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {
        Mockito.when(personService.getPerson()).thenReturn(personList);

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","admin")
                .contentType(ContentType.JSON)
                .when()
                .get(RestAssured.baseURI + "api/v1/person/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void addNewPersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() throws JSONException {
        JSONObject requestParams = new JSONObject();

        requestParams.put("id", person.getId());
        requestParams.put("name", person.getName());
        requestParams.put("age", person.getAge());
        requestParams.put("career", person.getCareer());
        requestParams.put("hairColor", person.getHairColor());
        requestParams.put("email", person.getEmail());

        Mockito.when(personService.addNewPerson(person)).thenReturn(person);

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","admin")
                .header("Content-Type","application/json")
                .body(requestParams.toString())
                .when()
                .post("api/v1/person/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void addNewPersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus401() throws JSONException {
        JSONObject requestParams = new JSONObject();

        requestParams.put("id", person.getId());
        requestParams.put("name", person.getName());
        requestParams.put("age", person.getAge());
        requestParams.put("career", person.getCareer());
        requestParams.put("hairColor", person.getHairColor());
        requestParams.put("email", person.getEmail());

        Mockito.when(personService.addNewPerson(person)).thenReturn(person);

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","erradissimo")
                .header("Content-Type","application/json")
                .body(requestParams.toString())
                .when()
                .post("api/v1/person/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(401, response.statusCode());
    }


    @Test
    public void deletePersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {
        Mockito.when(personService.deletePerson(person.getId())).thenReturn(person.getId());

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","admin")
                .header("Content-Type","application/json")
                .when()
                .delete("api/v1/person/" + person.getId())
                .then()
                .extract()
                .response();

        System.out.println(response.getBody().asString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void deletePersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus401() {
        Mockito.when(personService.deletePerson(person.getId())).thenReturn(person.getId());

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","errouasenha")
                .header("Content-Type","application/json")
                .when()
                .delete("api/v1/person/" + person.getId())
                .then()
                .extract()
                .response();

        System.out.println(response.getBody().asString());
        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    public void updatePersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {
        Mockito.when(personService.updatePerson(person.getId(), person.getName(), person.getEmail())).thenReturn(personList);

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","admin")
                .header("Content-Type","application/json")
                .when()
                .put("api/v1/person/" + person.getId() + "?name=" + person.getName() + "?email=" + person.getEmail())
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void updatePersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus401() {
        Mockito.when(personService.updatePerson(person.getId(), person.getName(), person.getEmail())).thenReturn(personList);

        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin","errou")
                .header("Content-Type","application/json")
                .when()
                .put("api/v1/person/" + person.getId() + "?name=" + person.getName() + "?email=" + person.getEmail())
                .then()
                .extract()
                .response();

        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    public void getPersonWhenUsernameAndPasswordAreIncorrectShouldReturnStatus401() {
        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("none", "admin")
                .contentType(ContentType.JSON)
                .when()
                .get(RestAssured.baseURI + "api/v1/person")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    public void getPersonWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {
        List<Person> person = new ArrayList<>();
        person.add(new Person("victoria", 24, "student", "brown", "vicprandi@gmail.com"));
        Mockito.when(personService.getPerson()).thenReturn(person);

        Response response =
                RestAssured.given()
                        .port(port)
                        .auth()
                        .basic("admin", "admin")
                        .contentType(ContentType.JSON)
                        .when()
                        .get(RestAssured.baseURI + "api/v1/person")
                        .then()
                        .extract()
                        .response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void getPersonByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatus405() {
//ARRANGE
        Person person = new Person("victoria", 24, "student", "brown", "vicprandi@gmail.com");
        Mockito.when(personService.getPersonById(1L)).thenReturn(Optional.of(person));
//ACT
        System.out.println(port);
        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin", "admin")
                .contentType(ContentType.JSON)
                .when()
                .get(RestAssured.baseURI + "api/v1/person/" + person.getId())
                .then()
                .extract()
                .response();
//ASSERT
        Assertions.assertEquals(405, response.statusCode());
    }

    @Test
    public void verifyIfPathIsIncorrectShouldReturn401() {
        Response response =
                RestAssured.given()
                        .port(port)
                        .auth()
                        .basic("admin", "none")
                        .contentType(ContentType.JSON)
                        .when()
                        .get(RestAssured.baseURI + "api/v1/person")
                        .then()
                        .extract()
                        .response();

        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    public void getRequest() {
        Response response = RestAssured.given()
                .port(port)
                .auth()
                .basic("admin", "admin")
                .contentType(ContentType.JSON)
                .when()
                .get(RestAssured.baseURI + "api/v1/person")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }


}