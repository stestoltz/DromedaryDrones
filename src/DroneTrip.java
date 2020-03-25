import java.util.List;

public class DroneTrip {

	private Order[] stops;
	
	/**
	 * constructor
	 * @param stops the ordered array of stops created by RoutingAlgorithm
	 */
	public DroneTrip(Order[] stops) {
		this.stops = stops.clone();
	}
	
	/**
	 * (Shallow) copy and return the stops array
	 * We need to reference the same Order objects, so that changes to 
	 * 		deliveredTime update correctly
	 * @return shallow copy of stops[]
	 */
	public Order[] getStops() {
		return stops.clone();
	}
	
	
}
