package github.com.brunomeloesilva.ecommerce;

import java.math.BigDecimal;

public class PedidoCompra {
	private final String userId;
	private final String orderId;
	private final BigDecimal value;
	
	public PedidoCompra(String userId, String orderId, BigDecimal value) {
		this.userId = userId;
		this.orderId = orderId;
		this.value = value;
	}

	public String getUserId() {
		return userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public BigDecimal getValue() {
		return value;
	}
}
