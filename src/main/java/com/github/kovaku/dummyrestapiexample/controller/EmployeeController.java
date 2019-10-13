package com.github.kovaku.dummyrestapiexample.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kovaku.dummyrestapiexample.domain.Employee;
import com.github.kovaku.dummyrestapiexample.domain.EmployeeRequest;
import com.github.kovaku.dummyrestapiexample.service.EmployeeService;

@RestController
@RequestMapping("v1")
public class EmployeeController {
    private static final Map<String, String> NOT_FOUND_MESSAGE = Map.of("message", "Employee not found!");

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/employees", produces = "application/json")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/employee/{id}", produces = "application/json")
    public ResponseEntity<Object> getEmployeeById(@NotNull @PathVariable("id") String id) {
        Optional<Employee> response = employeeService.getEmployeeById(id);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/create", produces = "application/json")
    public Employee createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(convertToEmployee(employeeRequest));
    }

    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<Object> updateEmployee(
        @NotNull @PathVariable String id,
        @Valid @RequestBody EmployeeRequest employeeRequest) {
        Optional<Employee> response = employeeService.updateEmployee(id, convertToEmployee(employeeRequest));
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteEmployee(@NotNull @PathVariable String id) {
        Boolean isDeleted = employeeService.deleteEmployee(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    private Employee convertToEmployee(EmployeeRequest e) {
        return new Employee(null, e.getName(), e.getAge(), e.getSalary(), e.getProfileImage());
    }
}
