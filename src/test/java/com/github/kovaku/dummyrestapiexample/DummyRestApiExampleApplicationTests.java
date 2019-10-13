package com.github.kovaku.dummyrestapiexample;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.github.kovaku.dummyrestapiexample.domain.Employee;
import com.github.kovaku.dummyrestapiexample.domain.EmployeeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = {"classpath:it-properties.properties"})
public class DummyRestApiExampleApplicationTests {

    private static final String API_HOST = "http://localhost:";
    private static final String API_BASE_PATH = "/api/v1";
    private static final String API_GET_ALL_PATH = "/employees";
    private static final String API_GET_EMPLOYEE_PATH = "/employee/{employeeId}";
    private static final String API_CREATE_EMPLOYEE_PATH = "/create";
    private static final String API_UPDATE_EMPLOYEE_PATH = "/update/{employeeId}";
    private static final String API_DELETE_EMPLOYEE_PATH = "/delete/{employeeId}";
    private static final String EMPLOYEE_ID_PARAM = "employeeId";

    @LocalServerPort
    private int port;

    @Test
    public void getAllTheEmployees() {
        getCommonRequestSpecifications()
            .when()
            .get(API_GET_ALL_PATH)
            .then()
            .log()
            .all()
            .assertThat()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("get-all-employees-response-schema.json"));
    }

    @Test
    public void getSpecificEmployee() {
        EmployeeRequest expectedEmployee = new EmployeeRequest("John Doe", 18, 999, "");

        Employee actualEmployee = getCommonRequestSpecifications()
            .when()
            .pathParam(EMPLOYEE_ID_PARAM, 1)
            .get(API_GET_EMPLOYEE_PATH)
            .then()
            .log()
            .all()
            .assertThat()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("get-employee-response-schema.json"))
            .extract()
            .body()
            .as(Employee.class);

        assertThat(actualEmployee.getId(), equalTo("1"));
        assertThat(actualEmployee.getName(), equalTo(expectedEmployee.getName()));
        assertThat(actualEmployee.getAge(), equalTo(expectedEmployee.getAge()));
        assertThat(actualEmployee.getSalary(), equalTo(expectedEmployee.getSalary()));
        assertThat(actualEmployee.getProfileImage(), equalTo(expectedEmployee.getProfileImage()));
    }

    @Test
    public void createUpdateDeleteFlow() {
        //Create
        EmployeeRequest createEmployeeRequest = new EmployeeRequest("Test Account", 33, 1000, "");

        Employee createEmployeeResponse = given(getCommonRequestSpecifications())
            .body(createEmployeeRequest)
            .when()
            .post(API_CREATE_EMPLOYEE_PATH)
            .then()
            .log()
            .all()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(Employee.class);

        //Update
        String employeeId = createEmployeeResponse.getId();

        EmployeeRequest updateEmployeeRequest = new EmployeeRequest(createEmployeeResponse.getName(), 34, 1001, "");
        Employee updateEmployeeResponse = given(getCommonRequestSpecifications())
            .body(updateEmployeeRequest)
            .when()
            .pathParam(EMPLOYEE_ID_PARAM, employeeId)
            .put(API_UPDATE_EMPLOYEE_PATH)
            .then()
            .log()
            .all()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(Employee.class);

        //Delete
        getCommonRequestSpecifications()
            .when()
            .pathParam(EMPLOYEE_ID_PARAM, employeeId)
            .delete(API_DELETE_EMPLOYEE_PATH)
            .then()
            .log()
            .all()
            .assertThat()
            .statusCode(200);
    }

    public RequestSpecification getCommonRequestSpecifications() {
        return given()
            .baseUri(API_HOST + String.valueOf(port))
            .basePath(API_BASE_PATH)
            .log()
            .all()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON);
    }
}
