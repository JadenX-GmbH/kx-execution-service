package com.jadenx.kxexecutionservice.config;

import com.jadenx.kxexecutionservice.KxExecutionServiceApplication;
import com.jadenx.kxexecutionservice.repos.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.MariaDBContainer;

import java.nio.charset.Charset;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
// CHECKSTYLE IGNORE check FOR NEXT 5 LINES
@SpringBootTest(
    classes = {KxExecutionServiceApplication.class, TestSecurityConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
@Sql("/data/clearAll.sql")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    private static final MariaDBContainer mariaDBContainer;

    static {
        mariaDBContainer = (MariaDBContainer) (new MariaDBContainer("mariadb")
            .withUsername("testcontainers")
            .withPassword("Testcontain3rs!")
            .withReuse(true));
        mariaDBContainer.start();
    }

    @Autowired
    public TestRestTemplate restTemplate;

    @Autowired
    public ExecutionResultRepository executionResultRepository;

    @Autowired
    public ExplorationResultRepository explorationResultRepository;

    @Autowired
    public ExplorationJobRepository explorationJobRepository;

    @Autowired
    public ProgramRepository programRepository;

    @Autowired
    public ExecutionJobRepository executionJobRepository;

    @Autowired
    public GigRepository gigRepository;

    @Autowired
    public DatasetRepository datasetRepository;

    @Autowired
    public OrderRepository orderRepository;

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDBContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mariaDBContainer::getPassword);
        registry.add("spring.datasource.username", mariaDBContainer::getUsername);
    }

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), Charset.forName("UTF-8"));
    }

    public HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
