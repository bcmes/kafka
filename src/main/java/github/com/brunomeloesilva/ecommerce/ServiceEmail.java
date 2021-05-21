package github.com.brunomeloesilva.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ServiceEmail {
	public static void main(String[] args) {
		
		var service = new ServiceEmail();
		
		try(var serviceKafka = new ConsumidorKafka(ServiceEmail.class.getName()
											, "ECOMMERCE_SEND_EMAIL"
											, service::parse))
		{
			serviceKafka.run();
		}
		
	}
	
	private void parse(ConsumerRecord<String, String> record) {
		System.out.println("\n--------------------- INICIO DO PROCESSAMENTO DA MENSAGEM ---------------------");
		var mensagem =  String.format("Enviando Email: Key = %s, Value = %s, Partition = %s, Offset = %s"
								, record.key(), record.value(), record.partition(), record.offset());
		System.out.println(mensagem);
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); } // Só para simular a demora de um processo.
		System.out.println("--------------------- FIM DO PROCESSAMENTO DA MENSAGEM ---------------------\n");
	}
}