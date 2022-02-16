package ru.job4j.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private static final String PERSONS_API = "http://localhost:8080/persons/";

    private final RestTemplate rest;

    private final EmployeeRepository employees;

    public EmployeeService(EmployeeRepository employees, RestTemplate rest) {
        this.employees = employees;
        this.rest = rest;
    }

    public List<Employee> findAll() {
        return this.employees.find4All();
    }

    public Person savePerson(Person person) {
        return rest.postForObject(PERSONS_API, person, Person.class);
    }

    public void deletePerson(int id) {
        rest.delete(PERSONS_API.concat("{id}"), id);
    }
}
