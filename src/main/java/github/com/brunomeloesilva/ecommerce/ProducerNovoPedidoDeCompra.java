package github.com.brunomeloesilva.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ProducerNovoPedidoDeCompra {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		try( var produtor = new ProdutorKafka() ) {
			//Gerar uma massa de pedidos
			for (int i = 0; i < 10; i++) {
				
				String key = UUID.randomUUID().toString(); //Para que sempre caia em partições diferentes (Antes devo definir mais de uma partição as configurações das minhas TAGs no Kafka).
				String value = "IdPedido01,IdUsuario01,ValorCompra120.00";
				produtor.send("ECOMMERCE_NEW_ORDER", key, value);
				
				value = "Obrigado pelo seu pedido.";
				produtor.send("ECOMMERCE_SEND_EMAIL", key, value);
			}
		}//Se der sucesso ou lançar uma exception, o IO será fechado.
	}
}
