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
		// then run until the next order overfills the drone
		// returns the orders that were removed from the queue
		while(hasNextOrder() && (shiftOrders.peek().getMeal().getMealWeight() + weight) <= drone.getCargoWeight()) {
			Order justRemoved = shiftOrders.remove();
			orders.add(justRemoved);
		}
		return orders;
	}

}
