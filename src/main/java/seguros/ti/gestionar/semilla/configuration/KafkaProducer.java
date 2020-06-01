package seguros.ti.gestionar.semilla.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import seguros.ti.gestionar.semilla.dbh2.dto.KafkaMsgDto;


public class KafkaProducer {	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class.getSimpleName());
	
	private static final String MESSAGE_SENT = "Message sent: ";
	private static final String TARGET_TOPIC = "Target topic: ";
	private static final String TOPIC_PARTITION = "Topic partition: ";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	public void sendMessage(String topic, KafkaMsgDto message){
		
		ListenableFuture<SendResult<String, Object>> result = this.kafkaTemplate.send(topic, message.getKey(), message.getBody());
		result.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

		    @Override
		    public void onSuccess(SendResult<String, Object> result) {		    	
		    	logger.info(MESSAGE_SENT + message + " " + TARGET_TOPIC + result.getRecordMetadata().topic() + " " + TOPIC_PARTITION +  result.getRecordMetadata().partition());				
		    }

		    @Override
		    public void onFailure(Throwable ex) {		    	
		    	logger.error(ex.getMessage());	
		    }

		});
    }
}
