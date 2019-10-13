package org.github.kovaku.dummyrestapiexample.persistence;

import org.github.kovaku.dummyrestapiexample.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
