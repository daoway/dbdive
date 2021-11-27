package com.blogspot.ostas.apps.dbdive;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface MySqlContainerTests {

	String DATABASE_NAME = "classicmodels";

	@Container
	MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest").withDatabaseName(DATABASE_NAME).withUsername("root")
			.withPassword("test").withReuse(true);

	@DynamicPropertySource
	static void prepareContainers(DynamicPropertyRegistry registry) {
		mysql.start();
		registry.add("spring.datasource.url", mysql::getJdbcUrl);
		registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
		registry.add("spring.datasource.username", mysql::getUsername);
		registry.add("spring.datasource.password", mysql::getPassword);
	}

}
