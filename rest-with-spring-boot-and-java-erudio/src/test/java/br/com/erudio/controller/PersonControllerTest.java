package br.com.erudio.controller;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {
    @Autowired
    private PersonRepository personRepository;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.dll-auto", () -> "create");
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        personRepository.deleteAll();
    }

    @Test
    void connectionEstablished() {
        Assertions.assertThat(postgres.isCreated()).isTrue();
        Assertions.assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    @Transactional
    void createPerson() {
        Person person = new Person();
        person.setFirstName("João");
        person.setLastName("Bosco");
        person.setAddress("Interlagos");
        person.setGender("Masculino");

        given()
                .contentType("application/json")
                .body(person)
                .when()
                .post("api/v1/person")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void getAll() {
        Person personOne = new Person();
        personOne.setFirstName("Milena");
        personOne.setLastName("Carvalho");
        personOne.setAddress("Bosque dos Eucaliptos");
        personOne.setGender("Feminino");
        Person personTwo = new Person();
        personTwo.setFirstName("Yuri");
        personTwo.setLastName("Claro");
        personTwo.setAddress("Jardim Oriente");
        personTwo.setGender("Masculino");
        List<Person> personList = List.of(personOne, personTwo);
        personRepository.saveAll(personList);

        given()
                .contentType("application/json")
                .when()
                .get("api/v1/person")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", hasSize(2));
    }

    @Test
    void getById() {
        Person person = new Person();
        person.setFirstName("Milena");
        person.setLastName("Carvalho");
        person.setAddress("Bosque dos Eucaliptos");
        person.setGender("Feminino");
        Person savedPerson = personRepository.save(person);
        String personId = savedPerson.getId().toString();

        given()
                .contentType("application/json")
                .when()
                .get("api/v1/person/{id}", personId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(personId))
                .body("firstName", equalTo(person.getFirstName()))
                .body("lastName", equalTo(person.getLastName()))
                .body("address", equalTo(person.getAddress()))
                .body("gender", equalTo(person.getGender()));
    }

    @Test
    void updatePerson() {
        Person person = new Person();
        person.setFirstName("Milena");
        person.setLastName("Carvalho");
        person.setAddress("Bosque dos Eucaliptos");
        person.setGender("Feminino");

        String personIdAsString = given()
                .contentType("application/json")
                .body(person)
                .when()
                .post("api/v1/person")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        UUID personId = UUID.fromString(personIdAsString);

        Person updatedPerson = new Person();
        updatedPerson.setId(personId);
        updatedPerson.setFirstName("Milena");
        updatedPerson.setLastName("Claro");
        updatedPerson.setAddress("Jardim Oriente");
        updatedPerson.setGender("Feminino");

        given()
                .contentType("application/json")
                .body(updatedPerson)
                .when()
                .put("api/v1/person/" + updatedPerson.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo(updatedPerson.getFirstName()))
                .body("lastName", equalTo(updatedPerson.getLastName()))
                .body("address", equalTo(updatedPerson.getAddress()))
                .body("gender", equalTo(updatedPerson.getGender()));
    }

    @Test
    void deletePerson() {
        Person person = new Person();
        person.setFirstName("Milena");
        person.setLastName("Carvalho");
        person.setAddress("Bosque dos Eucaliptos");
        person.setGender("Feminino");
        Person savedPerson = personRepository.save(person);
        String personId = savedPerson.getId().toString();

        given()
                .contentType("application/json")
                .when()
                .delete("api/v1/person/{id}", personId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}