package sensorsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sensorsmanager.bootstrap.BootstrapService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        BootstrapService.getInstance(); // Singleton, just need to call getInstance to populate database.
    }

}
