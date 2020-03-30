package javaClasses;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KnapsackPacking extends PackingAlgorithm {

	private List<Order> skippedOrders;
	//note that items in skippedOrders are taken out of shiftOrders list
	
	/**
	 * constructor
	 * @param meals
	 * @param drone
	 * @param skippedMeals
	 */
	public KnapsackPacking(Queue<Order> orders, Drone drone) {
		super(orders, drone);
		this.skippedOrders = new ArrayList<>();
	}
	
	@Override
	public List<Order> nextOrder(double time) {
		//the array that holds the orders that are next
		List<Order> sendOut = new LinkedList<>();
		//the list of orders that are ready and could go out
		ArrayList<Order> readyOrders = readyOrders(time);
		
		//base case - no skipped meals and no orders ready yet
		if (skippedOrders.isEmpty() && readyOrders.isEmpty()) {
			return null;
		}
		
		//check if there are skipped meals to consider first
		if (!skippedOrders.isEmpty()) {
			
		} 
		//if there are no skipped orders then we can pack from the ready order list
		else {
			
		}
		
		return sendOut;
	}
	
	/**
	 * method that returns a list of orders that are in, prepped, and ready to be
	 * 	sent at a certain time
	 * @param time - time to check for
	 * @return
	 */
	private ArrayList<Order> readyOrders(double time) {
		ArrayList<Order> ready = new ArrayList<Order>();
		
		//see which orders are ready and add them to the list
		for (Order o : shiftOrders) {
			if (o.getReadyTime() <= time) {
				ready.add(o);
			}
		}
		
		return ready;
	}
	
	/**
	 * method to find the indices of orders that fill the knapsack the most
	 * @param orders - list of orders to pick from
	 * @param currentWeight - the amount the knapsack is already filled
	 * @param maxWeight - the max amount the knapsack can hold
	 * @param index - the index of the next order to look at
	 * @param ordersUsed - List object to store all of the orders we pick
	 * @return the list object of orders that fill the knapsack the most
	 */
	private List<Integer> orderIndices(List<Order> orders, double currentWeight, double maxWeight, int index, List<Integer> ordersUsed, boolean indexAdded) {
		//base case - the knapsack is perfectly full
		if (currentWeight == maxWeight) {
			return ordersUsed;
		}
		
		//if we have reached the end of our list of orders
		if (index == orders.size()) {
			return ordersUsed;
		}
		
		//otherwise look at the next order
		double nextWeight = currentWeight + orders.get(index).getMeal().getMealWeight();
		
		//if the order does not overfill the knapsack and has not been added, then add it to the list
		if (nextWeight <= maxWeight && !indexAdded) {
			ordersUsed.add(index);
			return orderIndices(orders, nextWeight, maxWeight, index, ordersUsed, true);
		}
		//if the order has been added in a previous check or would overflow the knapsack, then move to the next item
		else {
			return orderIndices(orders, currentWeight, maxWeight, index + 1, ordersUsed, false);
		}
		
	}

}
