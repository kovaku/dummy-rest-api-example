package org.github.kovaku.dummyrestapiexample.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_age")
    private int age;
    @JsonProperty("employee_salary")
    private long salary;
    @JsonProperty("profile_image")
    private String profileImage;

    public Employee(String name, int age, long salary, String profileImage) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.profileImage = profileImage;
    }

    public Employee(String id, String name, int age, long salary, String profileImage) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.profileImage = profileImage;
    }
}
