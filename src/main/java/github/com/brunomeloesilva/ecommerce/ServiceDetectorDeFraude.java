package github.com.brunomeloesilva.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ServiceDetectorDeFraude {
	public static void main(String[] args) {
		var service = new ServiceDetectorDeFraude();
		try( var serviceKafka = new ConsumidorKafka(ServiceDetectorDeFraude.class.getName()
											, "ECOMMERCE_NEW_ORDER"
											, service::parse)) 
		{
			serviceKafka.run();
		}
	}

	private void parse(ConsumerRecord<String, String> record) {
			System.out.println("\n--------------------- INICIO DO PROCESSAMENTO DA MENSAGEM ---------------------");
			var mensagem = String.format("Novo Pedido: Key = %s, Value = %s, Partition = %s, Offset = %s", record.key(),
					record.value(), record.partition(), record.offset());
			System.out.println(mensagem);
			try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); } // Só para simular a demora de um processo.
			System.out.println("--------------------- FIM DO PROCESSAMENTO DA MENSAGEM ---------------------\n");
	}
}