package seguros.ti.gestionar.semilla.configuration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class.getSimpleName());
	
	private static final String KAFKA_GROPUP_ID = "semilla_consumer_group";
	private static final String KAFKA_TOPIC_TO_CONSUME = "topic-semilla";
	private static final String CONSUMED_MESSAGE = "Consumed message: ";
	private static final String FROM_TOPIC = "From topic: ";
	private static final String PARTITION = "Partition: ";

	@KafkaListener(topics = KAFKA_TOPIC_TO_CONSUME, groupId = KAFKA_GROPUP_ID)
	public void consume(ConsumerRecord<?, ?> message){
		logger.info(CONSUMED_MESSAGE + message.value() + " " + FROM_TOPIC + message.topic() + " " + PARTITION + message.partition());
	}
}
