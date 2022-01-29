package com.blogspot.ostas.apps.dbdive;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface OracleXeContainertTests {

    @Container
    OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe");

    @DynamicPropertySource
    static void prepareContainers(DynamicPropertyRegistry registry) {
        oracle.start();
        registry.add("spring.datasource.url", oracle::getJdbcUrl);
        registry.add("spring.datasource.driver-class-name", oracle::getDriverClassName);
        registry.add("spring.datasource.username", oracle::getUsername);
        registry.add("spring.datasource.password", oracle::getPassword);
    }

}
