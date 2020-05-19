package seguros.ti.gestionar.semilla.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="kafka")
public class PropertiesKafka {

	private String KAFKA_SSL_TRUSTSTORE_PATH_KEY;
	private String KAFKA_SSL_TRUSTSTORE_PALABRAPASE_KEY;
	private String KAFKA_SSL_TRUSTSTORE_PATH_VALUE;
	private String KAFKA_SSL_TRUSTSTORE_PALABRAPASE_VALUE;
	private String KAFKA_SECURITY_PROTOCOL_KEY;
	private String KAFKA_SECURITY_PROTOCOL_VALUE;
	private String BOOTSTRAP_SERVERS_KAFKA;
	private String KAFKA_TOPIC;
	private String KAFKA_GROUP_ID;
	private String KAFKA_CLIENT_ID;
	private String KAFKA_CONSUMER_MAX_POLL_RECORDS;
	private String KAFKA_PRODUCER_ACKS;
	private String KAFKA_PRODUCER_BATCH_SIZE; 
	private String KAFKA_PRODUCER_LINGER_MS;
	private String KAFKA_AUTO_OFFSET_RESET;
	private String KAFKA_BUFFER_MEMORY;
	
		 
}