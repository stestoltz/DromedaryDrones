package javaClasses;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class PackingAlgorithm {
	
	protected Queue<Order> shiftOrders;
	protected Drone drone;
	
	private DeliveryPoint home;
	
	private final double MAX_WEIGHT;
	
	private final double FILL_TO = 0.95;
	
	/**
	 * parent constructor for PackingAlgorithms
	 * @param orders
	 * @param drone
	 * @param home
	 */
	public PackingAlgorithm(Queue<Order> orders, Drone drone, DeliveryPoint home) {
		this.shiftOrders = new LinkedList<>(orders);
		this.drone = drone;
		
		this.MAX_WEIGHT = Math.min(drone.getCargoWeight(), drone.getUserSpecifiedWeight());
		
		this.home = home;
	}
	
	
	/**
	 * next order builds the next order
	 * @param the time at which the drone wants to leave - only look at orders from before this time
	 * @return the next order
	 */
	public abstract List<Order> nextOrder(double time);
	
	
	public abstract boolean hasNextOrder();
	
	public abstract double nextOrderTime();
	
	/**
	 * see if a weight will fit in the drone, with a bit of wiggle room
	 * @param weight
	 * @return
	 */
	public boolean fitsInDrone(double weight) {
		return weight <= MAX_WEIGHT * FILL_TO;
	}
	
	/**
	 * see if a trip will fit in the drone
	 * @param trip
	 * @return
	 */
	public boolean fitsInDrone(List<Order> trip) {
		double weight = 0.0;
		
		for (Order o : trip) {
			weight += o.getMeal().getMealWeight();
		}
		
		return fitsInDrone(weight);
	}
	
	/**
	 * Given a list of orders, returns true if the new order does not fill the drone
	 * @param trip
	 * @return
	 */
	public boolean canAddOrder(List<Order> currentOrders, Order next) {
		double currentWeight = 0.0;
		
		for (Order o : currentOrders) {
			currentWeight += o.getMeal().getMealWeight();
		}
		
		double droneWeight = currentWeight + next.getMeal().getMealWeight();
		
		int numStops = currentOrders.size() + 1;
		
		double maxDistanceForTrip = tripTime(currentOrders, next);
		
		boolean fitsInOneTrip = maxDistanceForTrip <= drone.getMaxFlightDistanceInFeet(numStops);
		
		return fitsInDrone(droneWeight) && fitsInOneTrip;
	}
	
	/**
	 * see if the current ordering of stops will allow me to deliver in enough time
	 * @param currentOrders 
	 * @param next
	 * @return
	 */
	public double tripTime(List<Order> currentTrip, Order next) {
		// use to order the list of trips to make it better at a low cost
		RoutingAlgorithm greedy = new GreedyAlgorithm();
		
		List<Order> copyTrip = new ArrayList<Order>(currentTrip);
		copyTrip.add(next);
		
		DroneTrip trip = greedy.createTrip(copyTrip, home);
		
		double time = tripTime(trip.getStops());
		
		return time;
	}
	
	
	/**
	 * find how long the trip will take (do not modify the orders)
	 * @param stops ordered array of stops
	 * @return time of trip
	 */
	public double tripTime(Order[] stops) {
		double time = 0.0;
			
		// each iteration calculates the time from the previous stop to the current one
		for (int i = 0; i <= stops.length; i++) {
			
			// calculate time to get to point
			
			// home to first stop
			if (i == 0) {
				time += Simulation.calcTime(home, stops[i].getDeliveryPoint(), drone.getAverageCruisingSpeedFeetPerSecond());
				
			// last stop back to home
			} else if (i == stops.length) {
				time += Simulation.calcTime(stops[i - 1].getDeliveryPoint(), home, drone.getAverageCruisingSpeedFeetPerSecond());
			
			// normal stop
			} else {
				time += Simulation.calcTime(stops[i - 1].getDeliveryPoint(), stops[i].getDeliveryPoint(), drone.getAverageCruisingSpeedFeetPerSecond());
			}
			
			
			// deliver order if not on trip back home
			if (i < stops.length) {
				
				// add delivery time
				time += drone.getDeliveryTime();
			}
		}
		
		return time;
	}

}
