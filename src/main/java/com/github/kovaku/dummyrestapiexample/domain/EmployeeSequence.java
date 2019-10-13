package com.github.kovaku.dummyrestapiexample.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("employee_sequence")
public class EmployeeSequence {
    @Id
    private String id;
    private long sequence;
}
