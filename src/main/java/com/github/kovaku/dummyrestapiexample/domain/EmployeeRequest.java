package org.github.kovaku.dummyrestapiexample.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequest {
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_age")
    private int age;
    @JsonProperty("employee_salary")
    private long salary;
    @JsonProperty("profile_image")
    private String profileImage;
}
