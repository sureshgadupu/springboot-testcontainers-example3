package dev.fullstackcode.tc.docker.it;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.logging.Level;
import java.util.logging.LogManager;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext
public  class BaseIT {

	static {
		// Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
		LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
	}
	
	@Autowired
	protected TestRestTemplate testRestTemplate ;

	public static PostgreSQLContainer<?> postgresDB;

	static {
	 postgresDB = new PostgreSQLContainer<>("postgres:13.2")
			.withDatabaseName("eis");

	 postgresDB.start();
	}

	@DynamicPropertySource
	public static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",postgresDB::getJdbcUrl);
		registry.add("spring.datasource.username", postgresDB::getUsername);
		registry.add("spring.datasource.password", postgresDB::getPassword);

	}


}