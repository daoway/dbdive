package com.blogspot.ostas.apps.dbdive.jpa;

import com.blogspot.ostas.apps.dbdive.OracleXeContainertTests;
import com.blogspot.ostas.apps.dbdive.jpa.domain.AppUser;
import com.blogspot.ostas.apps.dbdive.jpa.repository.AppUserRepository;
import com.blogspot.ostas.apps.dbdive.jpa.service.CustomerService;
import lombok.SneakyThrows;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.io.FileOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=update")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppUserTests implements OracleXeContainertTests {

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private DataSource dataSource;


	@SneakyThrows
	@Test
	void save(){
		var customer = new AppUser();
		customer.setLogin("random1");
		customerService.saveUser(customer);
		var fromDb = appUserRepository.findById(customer.getId()).get();
		assertThat(fromDb).isEqualTo(customer);
	}

	@AfterAll
	@SneakyThrows
	public void afterAll(){
		var dbConnection = dataSource.getConnection();
		var connection = new DatabaseConnection(dbConnection, dbConnection.getSchema());//for oracle
		var beforeDataSet = connection.createDataSet();
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
		FlatXmlDataSet.write(beforeDataSet, new FileOutputStream("schema-ums.xml"));
	}

}
