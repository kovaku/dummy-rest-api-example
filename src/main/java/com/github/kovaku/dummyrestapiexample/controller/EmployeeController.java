package org.github.kovaku.dummyrestapiexample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.github.kovaku.dummyrestapiexample.domain.Employee;
import org.github.kovaku.dummyrestapiexample.domain.EmployeeRequest;
import org.github.kovaku.dummyrestapiexample.service.EmployeeService;
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

@RestController
@RequestMapping("v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/employees", produces = "application/json")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/employee/{id}", produces = "application/json")
    public Employee getEmployeeById(@PathVariable("id") String id) {
        return employeeService.getEmployeeById(id).orElse(null);
    }

    @PostMapping(path = "/create", produces = "application/json")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(convertToEmployee(employeeRequest));
    }

    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody EmployeeRequest employeeRequest) {
        Optional<Employee> response = employeeService.updateEmployee(id, convertToEmployee(employeeRequest));
        if(response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public void updateEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
    }

    private Employee convertToEmployee(EmployeeRequest e) {
        return new Employee(null, e.getName(), e.getAge(), e.getSalary(), e.getProfileImage());
    }
}
