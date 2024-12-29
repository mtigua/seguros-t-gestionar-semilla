package seguros.ti.gestionar.semilla.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;



@Configuration
public class KafkaProducerConfig {

	@Autowired
	PropertiesKafka propertiesKafka;
	
	@Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesKafka.getBOOTSTRAP_SERVERS_KAFKA());
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, propertiesKafka.getKAFKA_CLIENT_ID());        
        configProps.put(ProducerConfig.ACKS_CONFIG, propertiesKafka.getKAFKA_PRODUCER_ACKS());
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, propertiesKafka.getKAFKA_PRODUCER_BATCH_SIZE());
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, propertiesKafka.getKAFKA_BUFFER_MEMORY()); 
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, propertiesKafka.getKAFKA_PRODUCER_LINGER_MS());
        configProps.put(propertiesKafka.getKAFKA_SECURITY_PROTOCOL_KEY(), propertiesKafka.getKAFKA_SECURITY_PROTOCOL_VALUE());
        configProps.put(propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PATH_KEY(), propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PATH_VALUE());
        configProps.put(propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PALABRAPASE_KEY(), propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PALABRAPASE_VALUE());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);

        return configProps;
    }
 
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
	   return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
	   return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public KafkaProducer producer() {
	   return new KafkaProducer();
	}
}
