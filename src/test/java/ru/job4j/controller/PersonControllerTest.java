package ru.job4j.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Job4jAuthApplication;
import ru.job4j.domain.Person;
import ru.job4j.service.PersonService;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jAuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService persons;

    @Test
    public void whenGetFindAllPersonsThenReturnAllPersonsList() throws Exception {
        List<Person> allPersons = List.of(
                Person.of(1, "parsentev", "123", 1),
                Person.of(2, "ban", "123", 2),
                Person.of(3, "ivan", "123", 3)
        );
        Mockito.when(persons.findAll()).thenReturn(allPersons);
        StringJoiner foundAllPersons = new StringJoiner(",", "[", "]");
        foundAllPersons.add("{\"id\":1,\"login\":\"parsentev\",\"password\":\"123\"}");
        foundAllPersons.add("{\"id\":2,\"login\":\"ban\",\"password\":\"123\"}");
        foundAllPersons.add("{\"id\":3,\"login\":\"ivan\",\"password\":\"123\"}");
        this.mockMvc.perform(get("/persons/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(foundAllPersons.toString()));
    }

    @Test
    public void whenGetPersonByIdThenReturnPerson() throws Exception {
        Mockito.when(persons.findById(2)).thenReturn(
                Optional.of(Person.of(2, "ban", "123", 2)));
        String foundPerson = "{\"id\":2,\"login\":\"ban\",\"password\":\"123\"}";
        this.mockMvc.perform(get("/persons/{id}", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(foundPerson));
    }

    @Test
    public void whenSavePersonThenReturnThisPerson() throws Exception {
        Person person = Person.of(2, "ban", "123", 2);
        Mockito.when(persons.save(any(Person.class))).thenReturn(person);
        String savedPerson = "{\"id\":2,\"login\":\"ban\",\"password\":\"123\"}";
        this.mockMvc.perform(post("/persons/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(savedPerson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(savedPerson));
    }

    @Test
    public void whenPutPersonThenReturnStatusOk() throws Exception {
        Person person = Person.of(2, "tom", "123", 2);
        Mockito.when(persons.save(any(Person.class))).thenReturn(person);
        String savedPerson = "{\"id\":2,\"login\":\"tom\",\"password\":\"123\"}";
        this.mockMvc.perform(put("/persons/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(savedPerson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void whenDeletePersonThenReturnStatusOk() throws Exception {
        doNothing().when(persons).delete(2);
        this.mockMvc.perform(delete("/persons/{id}", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
