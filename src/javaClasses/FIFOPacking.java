package javaClasses;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FIFOPacking extends PackingAlgorithm {
	
	/**
	 * constructor
	 * @param orders
	 * @param drone
	 */
	public FIFOPacking(Queue<Order> orders, Drone drone) {
		super(orders, drone);
	}
	
	@Override
	public List<Order> nextOrder(double time) {
		List<Order> orders = new ArrayList<Order>();
		double weight = 0.0;
		
		// if the queue has another order and if the new order will not overfill the drone
		//		and if the next order was ordered before now
		// then run until the next order overfills the drone or was ordered later than now
		// returns the orders that were removed from the queue
		while(hasNextOrder() && 
				(shiftOrders.peek().getMeal().getMealWeight() + weight) <= drone.getCargoWeight() &&
				shiftOrders.peek().getOrderedTime() <= time
				) {
			Order justRemoved = shiftOrders.remove();
			weight += justRemoved.getMeal().getMealWeight();
			orders.add(justRemoved);
		}
		return orders;
	}
	
	public boolean hasNextOrder() {
		return !shiftOrders.isEmpty();
	}
	
	public double nextOrderTime() {
		return shiftOrders.peek().getReadyTime();
	}
}
