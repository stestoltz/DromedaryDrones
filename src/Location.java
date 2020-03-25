import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Location {
	
	// hashmap of delivery points, each with a boolean value for active/not active
	private HashMap<DeliveryPoint, Boolean> deliveryPoints;
	private DeliveryPoint home;
	private String name;
	
	// constructor for already-created delivery points from data file
	
	/**
	 * constructor
	 * @param name
	 */
	public Location(String name, String homeName) {
		deliveryPoints = new HashMap<>();
		this.home = new DeliveryPoint(homeName, 0, 0);
		this.name = name;
	}

	/**
	 * @return list of all active delivery points
	 */
	public List<DeliveryPoint> getDeliveryPoints() {
		// need to return a deep copy
		
		List<DeliveryPoint> activePoints = new ArrayList<>();
		
		for (DeliveryPoint dp : deliveryPoints.keySet()) {
			
			// if active
			if (deliveryPoints.get(dp)) {
				
				// add copy to list
				activePoints.add(new DeliveryPoint(dp));
			}
		}
		
		return activePoints;
	}

	public String getName() {
		return name;
	}
	
	public DeliveryPoint getHome() {
		return new DeliveryPoint(home);
	}
	
	/**
	 * method adds a point to the hashmap
	 * assume that points added are automatically turned on
	 * @param dp DeliveryPoint that you want to add
	 */
	public void addPoint(DeliveryPoint dp) {
		deliveryPoints.put(dp, true);
	}
	
	/**
	 * switches boolean value of point within the hashmap
	 * @param dp delivery point to toggle
	 */
	public void togglePoint(DeliveryPoint dp) {
		Boolean current = deliveryPoints.get(dp);
		
		// if delivery point is already in the hashmap, toggle its value
		if (current != null) {
			deliveryPoints.put(dp, !current);
		
		// if delivery point is not already in the hashmap, add it and activate it
		} else {	
			deliveryPoints.put(dp, true);
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * changes any updated values in the selected point
	 * @param dp delivery point to edit
	 */
	/*public void editPoint(DeliveryPoint dp) {
		// they can use the delivery point's getters and setters
	}*/
	
	/**
	 * deletes a delivery point from the hashmap
	 * @param dp delivery point to delete
	 */
	public void deletePoint(DeliveryPoint dp) {
		deliveryPoints.remove(dp);
		
		//Boolean removed = deliveryPoints.remove(dp);
		
		/*if (removed == null) {
			// did not exist
		}*/
	}
}
