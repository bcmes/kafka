package github.com.brunomeloesilva.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ProducerNovoPedidoDeCompra {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		try( var produtorPedidoCompra = new ProdutorKafka<PedidoCompra>() ) {
			try( var produtorEmail = new ProdutorKafka<Email>() ) {
				//Gerar uma massa de pedidos
				for (int i = 0; i < 10; i++) {
					
					String userId = UUID.randomUUID().toString(); //Para que sempre caia em partições diferentes (Antes devo definir mais de uma partição as configurações das minhas TAGs no Kafka).
					String orderId = UUID.randomUUID().toString();
					double price = Math.random() * 5000 + 1;
					PedidoCompra pedidoCompra = new PedidoCompra(userId, orderId, new BigDecimal(price));
					produtorPedidoCompra.send("ECOMMERCE_NEW_ORDER", userId, pedidoCompra);
					
					Email email = new Email("Título do email", "Corpo do email.");
					produtorEmail.send("ECOMMERCE_SEND_EMAIL", userId, email);
				}
			}
		}
	}//Se der sucesso ou lançar uma exception, o IO será fechado.
}
