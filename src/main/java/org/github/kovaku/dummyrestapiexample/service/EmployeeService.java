package org.github.kovaku.dummyrestapiexample.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.github.kovaku.dummyrestapiexample.domain.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private Set<Employee> employees;

    @PostConstruct
    private void setup() {
        employees = new HashSet<>();
        employees.add(new Employee("89fb59be-19a9-4f73-b46d-297f14efdb94", "John Doe", 18, 999, ""));
        employees.add(new Employee("36a066d7-9275-433e-9c8e-2c771a8235ab", "Jane Doe", 19, 1000, ""));
    }

    public Set<Employee> getAllEmployees() {
        return employees;
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employees.stream()
            .filter(employee -> employee.getId().equals(id))
            .findFirst();
    }

    public Optional<Employee> addEmployee(Employee employee) {
        if(employees.add(employee)) {
            return Optional.of(employee);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Employee> updateEmployee(String id, Employee updatedEmployee) {
        updatedEmployee.setId(id);
        Optional<Employee> oldEmployee = employees
            .stream()
            .filter(employee -> employee.getId().equals(id))
            .findFirst();
        if (oldEmployee.isPresent()) {
            employees.remove(oldEmployee.get());
            employees.add(updatedEmployee);
            return Optional.of(updatedEmployee);
        } else {
            return Optional.empty();
        }
    }

    public Boolean deleteEmployee(String id) {
        Optional<Employee> employee = employees
            .stream()
            .filter(e -> e.getId().equals(id))
            .findFirst();
        if(employee.isPresent()) {
            return employees.remove(employee.get());
        } else {
            return Boolean.FALSE;
        }
    }
}
