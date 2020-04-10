package javaClasses;

import java.util.List;
import java.util.ArrayList;

public class GreedyAlgorithm implements RoutingAlgorithm{
	
	@Override
	/**
	 * Method calculates a greedy approach to creating a DroneTrip
	 * @param List<Order> orders list of orders
	 * @param home the location's home delivery point
	 * @return a new DroneTrip with a sequence of Orders 
	 */
	public DroneTrip createTrip(List<Order> orders, DeliveryPoint home) {
		List<Order> tripList = new ArrayList<>();
		DeliveryPoint previous = home;
		// runs until the order list is empty
		while(!orders.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			Order closest = null;
			// looks at each order still left in the list and finds the closest order
			for(Order o : orders) {
				double distance = distance(o.getDeliveryPoint(), previous);
				// updates the value of closest to o and the minDistance to distance
				if(distance < minDistance) {
					closest = o;
					minDistance = distance;
				}
			}
			tripList.add(closest);
			orders.remove(closest);
			previous = closest.getDeliveryPoint();
		}
		return new DroneTrip(tripList.toArray(new Order[tripList.size()]));
	}

}
