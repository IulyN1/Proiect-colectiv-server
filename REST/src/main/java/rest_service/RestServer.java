package rest_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.util.Collections;
import java.util.Properties;

/**
 * Rest server start class
 */
@ComponentScan(basePackages = {"repository","rest_service", "emailService"})
@SpringBootApplication
public class RestServer {
    /**
     * Server port to run on
     */
    @Value("{server.port}")
    private static int serverPort;

    /**
     * Bean parameter for repository construction
     * @return Properties
     */
    @Bean(name="jdbcProps")
    public static Properties createJdbcValues() {
        Properties jdbcProps = new Properties();
        try {
            jdbcProps.load(new ClassPathResource("repo.properties").getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jdbcProps;
    }

    /**
     * Starts the server
     * @param args doesn't accept any
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port",String.valueOf(serverPort)));
        app.run(args);
    }
}
