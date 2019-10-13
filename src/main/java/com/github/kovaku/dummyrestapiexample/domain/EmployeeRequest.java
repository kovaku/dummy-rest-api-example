package com.github.kovaku.dummyrestapiexample.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    @JsonProperty("employee_name")
    private String name;
    @NotNull
    @Min(0)
    @Max(100)
    @JsonProperty("employee_age")
    private int age;
    @NotNull
    @Min(0)
    @JsonProperty("employee_salary")
    private long salary;
    @JsonProperty("profile_image")
    private String profileImage;
}
