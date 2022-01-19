package dev.fullstackcode.tc.docker.it;


import dev.fullstackcode.tc.docker.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentControllerIT extends BaseIT {

//    @Container
//    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:13.2")
//			                                                                            .withDatabaseName("eis");

    @Test
    @Sql({ "/import.sql" })
    public void testGetDepartmentById() {

        ResponseEntity<Department> response = testRestTemplate.getForEntity( "/department/{id}",Department.class,100);
        Department dept =  response.getBody();

        assertEquals(100,dept.getId());
        assertEquals("HR", dept.getName());

    }

//    @DynamicPropertySource
//    public static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url",postgresDB::getJdbcUrl);
//		registry.add("spring.datasource.username", postgresDB::getUsername);
//		registry.add("spring.datasource.password", postgresDB::getPassword);
//
//    }


}