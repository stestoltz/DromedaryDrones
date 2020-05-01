package javaClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FIFOPacking extends PackingAlgorithm {
	
	private final double MAX_WEIGHT;
	
	/**
	 * constructor
	 * @param orders
	 * @param drone
	 */
	public FIFOPacking(Queue<Order> orders, Drone drone) {
		super(orders, drone);
		MAX_WEIGHT = Math.min(drone.getCargoWeight(), drone.getUserSpecifiedWeight());
	}
	
	/**
	 * next order builds the next order - pulls from queue until drone full or time passed
	 * @param the time at which the drone wants to leave - only look at orders from before this time
	 * @return the next order
	 */
	@Override
	public List<Order> nextOrder(double time) {
		List<Order> orders = new ArrayList<Order>();
		double weight = 0.0;
		
		// if the queue has another order and if the new order will not overfill the drone
		//		and if the next order was ordered before now
		// then run until the next order overfills the drone or was ordered later than now
		// returns the orders that were removed from the queue
		while(hasNextOrder() && 
				(shiftOrders.peek().getMeal().getMealWeight() + weight) <= MAX_WEIGHT &&
				shiftOrders.peek().getOrderedTime() <= time
				) {
			Order justRemoved = shiftOrders.remove();
			weight += justRemoved.getMeal().getMealWeight();
			orders.add(justRemoved);
		}
		return orders;
	}
	
	@Override
	public boolean hasNextOrder() {
		return !shiftOrders.isEmpty();
	}
	
	@Override
	public double nextOrderTime() {
		return shiftOrders.peek().getReadyTime();
	}
}
