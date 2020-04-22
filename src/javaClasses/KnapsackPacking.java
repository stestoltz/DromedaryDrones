package javaClasses;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KnapsackPacking extends PackingAlgorithm {
	
	private final double MAX_WEIGHT;
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
		MAX_WEIGHT = Math.min(drone.getCargoWeight(), drone.getUserSpecifiedWeight());
	}
	
	/**
	 * next order builds the next order
	 * @param the time at which the drone wants to leave - only look at orders from before this time
	 * @return the next order
	 */
	@Override
	public List<Order> nextOrder(double time) {
		//the array that holds the orders that are next
		List<Order> sendOut = new LinkedList<>();
		//the list of orders that are ready and could go out
		ArrayList<Order> readyOrders = readyOrders(time);
		//amount of weight packed into the drone currently
		double currentWeight = 0;
		
		//base case - no skipped meals and no orders ready yet
		if (skippedOrders.isEmpty() && readyOrders.isEmpty()) {
			return null;
		}
		
		//check if there are skipped meals to consider first
		if (!skippedOrders.isEmpty()) {
			//sort from biggest to smallest
			Collections.sort(skippedOrders);
			
			//fill the knapsack as much as possible
			Iterator<Order> itr = skippedOrders.iterator();
			while (itr.hasNext()) {
				Order o = itr.next();
				if (o.getMeal().getMealWeight() + currentWeight <= MAX_WEIGHT) {
					sendOut.add(o);
					currentWeight += o.getMeal().getMealWeight();
				}
			}
			
			skippedOrders.removeAll(sendOut);
			
			//if there is room left then check to fill with other orders
			if (currentWeight < MAX_WEIGHT && !readyOrders.isEmpty()) {
				Iterator<Order> itr2 = readyOrders.iterator();
				while (itr2.hasNext()) {
					Order o = itr2.next();
					if (o.getMeal().getMealWeight() + currentWeight <= MAX_WEIGHT) {
						sendOut.add(o);
						currentWeight += o.getMeal().getMealWeight();
						shiftOrders.remove(o);
					} 
					//if the item isn't packed then it moves to skipped orders
					else {
						skippedOrders.add(o);
						shiftOrders.remove(o);
					}
				}
			}
			
			return sendOut;
		} 
		//if there are no skipped orders then we can pack from the ready order list
		else {
			Iterator<Order> itr3 = readyOrders.iterator();
			while (itr3.hasNext()) {
				Order o = itr3.next();
				if (o.getMeal().getMealWeight() + currentWeight <= MAX_WEIGHT) {
					sendOut.add(o);
					currentWeight += o.getMeal().getMealWeight();
					shiftOrders.remove(o);
				} 
				//if the item isn't packed then it moves to skipped orders
				else {
					skippedOrders.add(o);
					shiftOrders.remove(o);
				}
			}
			
			return sendOut;
		}
	}
	
	/**
	 * method that returns a list of orders that are in, prepped, and ready to be
	 * 	sent at a certain time
	 * @param time - time to check for
	 * @return
	 */
	private ArrayList<Order> readyOrders(double time) {
		ArrayList<Order> ready = new ArrayList<Order>();
		
		for (Order o : shiftOrders) {
			if (o.getReadyTime() <= time) {
				ready.add(o);
			}
		}

		return ready;
	}
	
	public boolean hasNextOrder() {
		if (shiftOrders.isEmpty() && skippedOrders.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public double nextOrderTime() {
		//in this case it might just have skipped orders to send out
		if (shiftOrders.isEmpty()) {
			return skippedOrders.get(0).getReadyTime();
		}
		return shiftOrders.peek().getReadyTime();
	}

}
