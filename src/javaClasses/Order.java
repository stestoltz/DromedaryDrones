package javaClasses;

public class Order implements Comparable<Order> {
	
	private String name;
	private Meal meal;
	private double orderedTime;
	private double readyTime;
	private double deliveredTime;
	private DeliveryPoint deliveryPoint;
	
	/**
	 * constructor
	 * @param name
	 * @param meal
	 * @param orderedTime
	 * @param deliveredTime
	 * @param deliveryPoint
	 */
	public Order(String name, Meal meal, double orderedTime, DeliveryPoint deliveryPoint) {
		this.name = name;
		this.meal = meal;
		this.orderedTime = orderedTime;
		this.readyTime = orderedTime + meal.getMealPrepTime();
		//this.deliveredTime; // we do not set delivered time when we init it
		this.deliveryPoint = deliveryPoint;
	}
	
	/**
	 * copy constructor
	 */
	public Order(Order o) {
		this.name = o.name;
		this.meal = new Meal(o.meal);
		this.orderedTime = o.orderedTime;
		this.deliveryPoint = new DeliveryPoint(o.deliveryPoint);
		this.readyTime = o.readyTime;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Meal getMeal() {
		return meal;
	}
	
	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	
	public double getOrderedTime() {
		return orderedTime;
	}
	
	// once ordered, we won't need to change the ordered time
	/*public void setOrderedTime(double orderedTime) {
		this.orderedTime = orderedTime;
	}*/
	
	public double getDeliveredTime() {
		return deliveredTime;
	}
	
	public void setDeliveredTime(double deliveredTime) {
		this.deliveredTime = deliveredTime;
	}
	
	public double getReadyTime() {
		return readyTime;
	}
	
	/**
	 * @return how long this order took to deliver
	 */
	public double getDuration() {
		return orderedTime - deliveredTime;
	}
	
	
	public DeliveryPoint getDeliveryPoint() {
		return deliveryPoint;
	}
	
	public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	
	//sorts orders from heaviest to lightest
	@Override
    public int compareTo(Order o) {
        if(this.meal.getMealWeight() > o.meal.getMealWeight()) {
            return -1;
        } else if (this.meal.getMealWeight() < o.meal.getMealWeight()) {
            return 1;
        } else {
            return 0;
        }
    }

	
}
