import java.util.List;

public interface RoutingAlgorithm {

	/**
	 * given an unordered list of orders, order them and make a drone trip
	 * @param orders
	 * @return drone trip containing orders, sequenced to be efficient
	 */
	public DroneTrip createTrip(List<Order> orders);
	
	/**
	 * Calculates the distance between two points
	 * @param a the first point
	 * @param b the second point
	 * @return the distance between the two points
	 */
	default double distance(DeliveryPoint a, DeliveryPoint b) {
		return 0.0;
	}
	
}
