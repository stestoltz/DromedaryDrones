package javaClasses;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class PackingAlgorithm {
	
	protected Queue<Order> shiftOrders;
	protected Drone drone;
	
	public PackingAlgorithm(Queue<Order> orders, Drone drone) {
		this.shiftOrders = new LinkedList<>(orders);
		this.drone = drone;
	}
	
	
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
		return shiftOrders.peek().getReadyTime();
	}
}
