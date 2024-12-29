package seguros.ti.gestionar.semilla.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="msg")
public class PropertiesMsg {

	private String http200;
	private String http400;
	private String http401;
	private String http403;
	private String http404;
	private String http500;
	private String cliente_swagger_post_value;
	private String logger_info_iniciando;
	private String logger_info_finalizando;
	
		 
}