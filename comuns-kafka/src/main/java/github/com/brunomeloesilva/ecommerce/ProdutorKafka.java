package github.com.brunomeloesilva.ecommerce;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProdutorKafka<T> implements Closeable{
	
	private final KafkaProducer<String, T> producer;
	
	public ProdutorKafka() {
		this.producer = new KafkaProducer<>(properties());
	}
	
	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
		return properties;
	}

	public void send(String topic, String key, T value) throws InterruptedException, ExecutionException {
		var producerRecord = new ProducerRecord<String, T>(topic, key, value);

		Callback callback = (data, exception) -> {
			if(exception != null) {
				exception.printStackTrace();
				return;
			}
			System.out.println("Sucesso enviado tópico: " + data.topic() +":::partition "+ data.partition() + "/offset " + data.offset() +"/timestamp "+ data.timestamp());
		};
		
		producer.send(producerRecord, callback).get();  
	}

	@Override
	public void close() {
		producer.close();
	}
}
