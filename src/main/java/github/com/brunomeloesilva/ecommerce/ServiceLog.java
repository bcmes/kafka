package github.com.brunomeloesilva.ecommerce;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ServiceLog {
	public static void main(String[] args) throws InterruptedException {
		
		var service = new ServiceLog();
		try(var serviceKafka = new ConsumidorKafka<String>(ServiceLog.class.getName()
													, Pattern.compile("ECOMMERCE.*")
													, service::parse
													, String.class
													, Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
			serviceKafka.run();
		}		
	}
	
	private void parse(ConsumerRecord<String, String> record) {
		System.out.println("\n--------------------- INICIO DO PROCESSAMENTO DA MENSAGEM ---------------------");
		System.out.println("TOPICO => " + record.topic());
		var mensagem =  String.format("Log: Key = %s, Value = %s, Partition = %s, Offset = %s"
								, record.key(), record.value(), record.partition(), record.offset());
		System.out.println(mensagem);
		System.out.println("--------------------- FIM DO PROCESSAMENTO DA MENSAGEM ---------------------\n");
	}
}