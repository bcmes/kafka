package github.com.brunomeloesilva.ecommerce;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class NovoPedidoDeCompraMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		var kafkaProducer = new KafkaProducer<String, String>(properties());
		String key = "IdPedido01,IdUsuario01,ValorCompra120.00";
		String value = key;
		var producerRecord = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", key, value);
		//kafkaProducer.send(producerRecord); //Modo Assincrono
		//kafkaProducer.send(producerRecord).get(); //Modo Sincrono
		//Modo Sincrono, analisando o retorno do envio.
		kafkaProducer.send(producerRecord, (data, exception) -> {
			if(exception != null) {
				exception.printStackTrace();
				return;
			}
			System.out.println("Sucesso enviado tópico: " + data.topic() +":::partition "+ data.partition() + "/offset " + data.offset() +"/timestamp "+ data.timestamp());
		}).get(); 
	}

	
	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}

}
