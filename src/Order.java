
public class Order {
	
	String name;
	Meal meal;
	double orderedTime;
	double deliveredTime;
	DeliveryPoint deliveryPoint;
	
	/**
	 * constructor
	 * @param name
	 * @param meal
	 * @param orderedTime
	 * @param deliveredTime
	 * @param deliveryPoint
	 */
	public Order(String name, Meal meal, double orderedTime, double deliveredTime,
			DeliveryPoint deliveryPoint) {
		
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
	public void setOrderedTime(double orderedTime) {
		this.orderedTime = orderedTime;
	}
	public double getDeliveredTime() {
		return deliveredTime;
	}
	public void setDeliveredTime(double deliveredTime) {
		this.deliveredTime = deliveredTime;
	}
	public DeliveryPoint getDeliveryPoint() {
		return deliveryPoint;
	}
	public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	
	
}
