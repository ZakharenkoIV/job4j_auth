package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employees;

    public EmployeeController(EmployeeService employees) {
        this.employees = employees;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return this.employees.findAll();
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(
                this.employees.savePerson(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/persons")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.employees.savePerson(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.employees.deletePerson(id);
        return ResponseEntity.ok().build();
    }
}
