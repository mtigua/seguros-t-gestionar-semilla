package seguros.ti.gestionar.semilla.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Component
@Data
@ConfigurationProperties(prefix="sql.query")
public class PropertiesSql {
	private String find_clients_insurances_by_id_and_rate_filepath;
	private String find_clients_single_filepath;
	private String find_clients_insurances_by_insurance_id_filepath;
	
	private String find_persons_single_filepath;
		 
}