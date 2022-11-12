package rest_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.util.Collections;
import java.util.Properties;

@ComponentScan(basePackages = {"repository","rest_service"})
@SpringBootApplication
public class RestServer {
    @Value("{server.port}")
    private static int serverPort;


    @Bean(name="jdbcProps")
    public static Properties createJdbcValues() {
        Properties jdbcProps = new Properties();
        try {
            jdbcProps.load(new ClassPathResource("app.properties").getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jdbcProps;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port",String.valueOf(serverPort)));
        app.run(args);
    }
}
