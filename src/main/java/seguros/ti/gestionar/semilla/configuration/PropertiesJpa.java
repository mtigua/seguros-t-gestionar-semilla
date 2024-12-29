package seguros.ti.gestionar.semilla.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="jpa")
public class PropertiesJpa {


	private String spring_jpa_properties_hibernate_jdbc_lob_non_contextual_creation;
	private String spring_jpa_hibernate_ddl_auto;
	private String HIBERNATE_DDL_AUTO_KEY;
	private String HIBERNATE_DIALECT_KEY;
	private String SPRING_JPA_HIBERNATE_DIALECT_SQLSERVER_KEY;
	private String SPRING_JPA_HIBERNATE_DIALECT_H2_KEY;
}