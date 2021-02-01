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
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;


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
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, propertiesKafka.getKAFKA_AUTO_OFFSET_RESET());
        configProps.put(propertiesKafka.getKAFKA_SECURITY_PROTOCOL_KEY(), propertiesKafka.getKAFKA_SECURITY_PROTOCOL_VALUE());
        configProps.put(propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PATH_KEY(), propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PATH_VALUE());
        configProps.put(propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PALABRAPASE_KEY(), propertiesKafka.getKAFKA_SSL_TRUSTSTORE_PALABRAPASE_VALUE());
        //Error Handling
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
        configProps.put(ErrorHandlingDeserializer2.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configProps.put(ErrorHandlingDeserializer2.VALUE_DESERIALIZER_CLASS,JsonDeserializer.class.getName());
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return configProps;
    }
 
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        ErrorHandlingDeserializer2<String> keyErrorHandlingDeserializer = new ErrorHandlingDeserializer2<>(new StringDeserializer());
        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        ErrorHandlingDeserializer2<Object> valueErrorHandlingDeserializer  = new ErrorHandlingDeserializer2<>(deserializer);
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),keyErrorHandlingDeserializer, valueErrorHandlingDeserializer);
    }



    
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, Object> factory =  new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      factory.setErrorHandler(new KafkaErrorHandler());
      return factory;
    }

    @Bean
    public KafkaConsumer consumer() {
      return new KafkaConsumer();
    }
}
