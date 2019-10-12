package org.github.kovaku.dummyrestapiexample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.github.kovaku.dummyrestapiexample.domain.Employee;
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
    public Set<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/employee/{id}", produces = "application/json")
    public Employee getEmployeeById(@PathVariable("id") String id) {
        return employeeService.getEmployeeById(id).orElse(null);
    }

    @PostMapping(path = "/create", produces = "application/json")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Optional<Employee> response = employeeService.addEmployee(employee);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        Optional<Employee> response = employeeService.updateEmployee(id, employee);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Map<String, Map<String, String>>> updateEmployee(@PathVariable String id) {
        if (employeeService.deleteEmployee(id)) {
            Map<String, Map<String, String>> response = new HashMap<>();
            Map<String, String> message = new HashMap<>();
            message.put("text", "successfully! deleted Records");
            response.put("success", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
