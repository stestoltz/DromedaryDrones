package javaClasses;
import java.util.List;

public interface RoutingAlgorithm {

	/**
	 * given an unordered list of orders, order them and make a drone trip
	 * DOES NOT INCLUDE HOME AT EITHER END
	 * @param orders
	 * @return drone trip containing orders, sequenced to be efficient
	 */
	public DroneTrip createTrip(List<Order> orders, DeliveryPoint home);
	
}
