package javaClasses;
import java.util.ArrayList;
import java.util.List;

public class BacktrackingSearch implements RoutingAlgorithm {
	
	double bestDistance;
	List<Order> bestRoute;

	/**
	 * given an unordered list of orders, order them and make a drone trip
	 * 		in this case, use backtracking to efficiently do TSP
	 * @param orders
	 * @return drone trip containing orders, sequenced to be efficient
	 */
	@Override
	public DroneTrip createTrip(List<Order> orders, DeliveryPoint home) {

		bestDistance = Double.MAX_VALUE;
		
		backtrack(orders, new ArrayList<Order>(), home, 0, home);
		
		return new DroneTrip(bestRoute.toArray(new Order[bestRoute.size()]));
	}
	
	public void backtrack(List<Order> ordersLeft, List<Order> route, DeliveryPoint current, double distance, DeliveryPoint home) {
		
		// prune if we've already exceeded our target
		if (distance > bestDistance) {
			return;
		}
		
		if (ordersLeft.isEmpty()) {
			
			// done with orders - go back home
			updateBestRoute(distance + distance(current, home), route);
			
		} else {
		
			// try a route starting with each order
			for (int i = 0; i < ordersLeft.size(); i++) {
				
				// adjust lists based on using this order next
				Order currentOrder = ordersLeft.remove(i);
				route.add(currentOrder);
				
				// distance if we deliver this order next
				double currentDistance = distance + distance(current, currentOrder.getDeliveryPoint());
				
				// backtrack for this order
				backtrack(ordersLeft, route, currentOrder.getDeliveryPoint(), currentDistance, home);
				
				// reset lists
				route.remove(currentOrder);
				ordersLeft.add(i, currentOrder);
			}
		}
	}
	
	public void updateBestRoute(double distance, List<Order> route) {
		
		if (distance < bestDistance) {
			bestDistance = distance;
			bestRoute = new ArrayList<>(route);
		}
		
	}

}
