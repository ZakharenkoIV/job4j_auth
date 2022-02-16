package ru.job4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @JoinColumn(name = "first_name")
    private String firstName;

    @NotNull
    @JoinColumn(name = "last_name")
    private String lastName;

    @NotNull
    @Column(unique = true)
    private int tin;

    @NotNull
    @JoinColumn(name = "date_of_employment")
    private Timestamp dateOfEmployment;

    @JsonIgnoreProperties("employee")
    @OneToMany(mappedBy = "employee", orphanRemoval = true)
    private List<Person> accounts;

    public static Employee of(
            int id, String firstName, String lastName, int tin, Timestamp dateOfEmployment) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setTin(tin);
        employee.setDateOfEmployment(dateOfEmployment);
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTin() {
        return tin;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }

    public Timestamp getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Timestamp dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public List<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Person> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Person account) {
        this.accounts.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id
                && tin == employee.tin
                && Objects.equals(dateOfEmployment, employee.dateOfEmployment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tin, dateOfEmployment);
    }
}
