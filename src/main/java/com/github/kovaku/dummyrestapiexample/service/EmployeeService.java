package org.github.kovaku.dummyrestapiexample.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.github.kovaku.dummyrestapiexample.domain.Employee;
import org.github.kovaku.dummyrestapiexample.domain.EmployeeRequest;
import org.github.kovaku.dummyrestapiexample.persistence.EmployeeRepository;
import org.github.kovaku.dummyrestapiexample.persistence.EmployeeSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeSequenceGenerator employeeSequenceGenerator;

    @PostConstruct
    private void setup() {
        addEmployee(new Employee(null, "John Doe", 18, 999, ""));
        addEmployee(new Employee(null, "Jane Doe", 19, 1000, ""));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee employee) {
        employee.setId(employeeSequenceGenerator.generateNextId());
        employeeRepository.insert(employee);
        return employee;
    }

    public Optional<Employee> updateEmployee(String id, Employee updatedEmployee) {
        if(employeeRepository.existsById(id)) {
            updatedEmployee.setId(id);
            employeeRepository.save(updatedEmployee);
            return getEmployeeById(id);
        } else {
            return Optional.empty();
        }
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}
