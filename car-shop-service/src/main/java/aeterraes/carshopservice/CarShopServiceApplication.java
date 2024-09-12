package aeterraes.carshopservice;

import aeterraes.carshopservice.starter.AuditingStarter;
import aeterraes.carshopservice.starter.LoggingStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AuditingStarter.class, LoggingStarter.class})
public class CarShopServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarShopServiceApplication.class, args);
    }

}
