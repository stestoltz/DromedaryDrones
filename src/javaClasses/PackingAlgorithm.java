package javaClasses;
import java.util.List;
import java.util.Queue;

public abstract class PackingAlgorithm {
	
	private Queue<Order> shiftOrders;
	private Drone drone;
	
	/**
	 * next order builds the next order
	 * @param the time at which the drone wants to leave - only look at orders from before this time
	 * @return the next order
	 */
	public abstract List<Order> nextOrder(double time);
	
	public boolean hasNextOrder() {
		return !shiftOrders.isEmpty();
	}
	
	public double nextOrderTime() {
		return shiftOrders.peek().getOrderedTime();
	}
}
