package EcomE2EAPIAuth;

import java.util.List;

public class POJOCreateOrderRequest {

	private List<POJOOrdersRequest> orders;

	public List<POJOOrdersRequest> getOrders() {
		return orders;
	}

	public void setOrders(List<POJOOrdersRequest> orders) {
		this.orders = orders;
	}
	
}
