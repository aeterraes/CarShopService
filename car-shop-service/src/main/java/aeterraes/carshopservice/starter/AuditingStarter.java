package aeterraes.carshopservice.starter;

import aeterraes.carshopservice.configuration.aop.AuditingAspectConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(AuditingAspectConfiguration.class)
public class AuditingStarter {
}
