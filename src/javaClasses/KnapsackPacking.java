package javaClasses;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KnapsackPacking extends PackingAlgorithm {

	private Queue<Order> skippedOrders;
	
	/**
	 * constructor
	 * @param meals
	 * @param drone
	 * @param skippedMeals
	 */
	public KnapsackPacking(Queue<Order> orders, Drone drone) {
		super(orders, drone);
		this.skippedOrders = new LinkedList<>();
	}
	
	@Override
	public List<Order> nextOrder(double time) {
		// TODO Auto-generated method stub
		return null;
	}

}
