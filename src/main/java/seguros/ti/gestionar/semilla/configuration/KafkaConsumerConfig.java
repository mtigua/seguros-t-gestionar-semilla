package seguros.ti.gestionar.semilla.configuration;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {
 
	@Autowired
	PropertiesKafka propertiesKafka;
	
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.CLIENT_ID_CONFIG, propertiesKafka.getKAFKA_CLIENT_ID());
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesKafka.getBOOTSTRAP_SERVERS_KAFKA());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, propertiesKafka.getKAFKA_AUTO_OFFSET_RESET());
        configProps.put(propertiesKafka.getKAFKA_SECURITY_PROTOCOL_KEY(), propertiesKafka.getKAFKA_SECURITY_PROTOCOL_VALUE());
        configProps.put(propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PATH_KEY(), propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PATH_VALUE());
        configProps.put(propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PALABRAPASE_KEY(), propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PALABRAPASE_VALUE());

        return configProps;
    }
 
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
      return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, Object> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());

      return factory;
    }

    @Bean
    public KafkaConsumer consumer() {
      return new KafkaConsumer();
    }
}
