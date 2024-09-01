package aeterraes.carshopservice.configuration.aop;

import aeterraes.carshopservice.configuration.aop.aspects.AuditingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuditingAspectConfiguration {

    @Bean
    public AuditingAspect auditingAspect() {
        return new AuditingAspect();
    }
}