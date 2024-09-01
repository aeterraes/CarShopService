package aeterraes.carshopservice.starter;

import aeterraes.carshopservice.configuration.aop.LoggingAspectConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(LoggingAspectConfiguration.class)
public class LoggingStarter {
}
