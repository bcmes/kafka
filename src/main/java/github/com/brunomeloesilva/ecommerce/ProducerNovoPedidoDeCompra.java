package github.com.brunomeloesilva.ecommerce;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerNovoPedidoDeCompra {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		var kafkaProducer = new KafkaProducer<String, String>(properties());
		String key = "IdPedido01,IdUsuario01,ValorCompra120.00";
		String value = key;
		var producerRecord = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", key, value);
		//kafkaProducer.send(producerRecord); //Modo Assincrono
		//kafkaProducer.send(producerRecord).get(); //Modo Sincrono
		//Modo Sincrono, analisando o retorno do envio.
		Callback callback = (data, exception) -> {
			if(exception != null) {
				exception.printStackTrace();
				return;
			}
			System.out.println("Sucesso enviado tópico: " + data.topic() +":::partition "+ data.partition() + "/offset " + data.offset() +"/timestamp "+ data.timestamp());
		};
		
		kafkaProducer.send(producerRecord, callback).get(); 
		//Enviando outro registro...
		String keyEmail = "Obrigado por seu pedido. Obrigado por seu pedido.";
		String valueEmail = keyEmail;
		var producerEmailRecord = new ProducerRecord<String, String>("ECOMMERCE_SEND_EMAIL", keyEmail, valueEmail);
		kafkaProducer.send(producerEmailRecord, callback).get(); 
	}

	
	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}

}
