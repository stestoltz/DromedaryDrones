import java.util.HashMap;

public class Location {
	
	HashMap<DeliveryPoint, Boolean> DeliveryPoints;
	String name;
	
	/**
	 * constructor
	 * @param deliveryPoints
	 * @param name
	 */
	public Location(HashMap<DeliveryPoint, Boolean> deliveryPoints, String name) {
		DeliveryPoints = deliveryPoints;
		this.name = name;
	}

	public HashMap<DeliveryPoint, Boolean> getDeliveryPoints() {
		return DeliveryPoints;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * method adds a point to the hashmap
	 * assume that points added are automatically turned on
	 * @param dp DeliveryPoint that you want to add
	 */
	public void addPoint(DeliveryPoint dp) {
		
	}
	
	/**
	 * switches boolean value of point within the hashmap
	 * @param dp delivery point to toggle
	 */
	public void togglePoint(DeliveryPoint dp) {
		
	}
	
	/**
	 * changes any updated values in the selected point
	 * @param dp delivery point to edit
	 */
	public void editPoint(DeliveryPoint dp) {
		
	}
	
	/**
	 * deletes a delivery point from the hashmap
	 * @param dp delivery point to delete
	 */
	public void deletePoint(DeliveryPoint dp) {
		
	}
}
