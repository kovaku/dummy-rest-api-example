package com.github.kovaku.dummyrestapiexample.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document("employee")
public class Employee {
    @Id
    private String id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_age")
    private int age;
    @JsonProperty("employee_salary")
    private long salary;
    @JsonProperty("profile_image")
    private String profileImage;
}
