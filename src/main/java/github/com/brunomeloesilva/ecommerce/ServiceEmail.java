package github.com.brunomeloesilva.ecommerce;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ServiceEmail {
	public static void main(String[] args) throws InterruptedException {
		var kafkaConsumer = new KafkaConsumer<String, String>(properties());
		kafkaConsumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL"));
		
		while(true) {	
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
			
			if(!records.isEmpty()) {
				System.out.println("Encontrei registros. Quantidade = " + records.count());

				for (ConsumerRecord<String, String> record : records) {
					System.out.println("\n--------------------- INICIO DO PROCESSAMENTO DA MENSAGEM ---------------------");
					var mensagem =  String.format("Enviando Email: Key = %s, Value = %s, Partition = %s, Offset = %s"
											, record.key(), record.value(), record.partition(), record.offset());
					System.out.println(mensagem);
					Thread.sleep(1000); // Só para simular a demora de um processo.
					System.out.println("--------------------- FIM DO PROCESSAMENTO DA MENSAGEM ---------------------\n");
				}
			}
		}
		
	}

	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ServiceEmail.class.getName());
		return properties;
	}
}
