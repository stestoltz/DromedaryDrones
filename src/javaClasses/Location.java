package javaClasses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Location {
	
	// hashmap of delivery points, each with a boolean value for active/not active
	private HashMap<DeliveryPoint, Boolean> deliveryPoints;
	private DeliveryPoint home;
	private String name;
	
	private ArrayList<Meal> meals;	//list of all available meals (for this location)
	private Drone drone;
	private ArrayList<FoodItem> foods;		//list of all foods  (for this location)
	private ShiftDetails shiftDetails;
	
	
	/**
	 * constructor for a brand new location
	 * @param name - name of the location
	 * @param homeName - name of the starting point/ origin
	 */
	public Location(String name, String homeName) {
		deliveryPoints = new HashMap<>();
		this.home = new DeliveryPoint(homeName, 0, 0);
		this.name = name;
		
		// add fake points
		addPoint(new DeliveryPoint("HAL", 25, 0));
		addPoint(new DeliveryPoint("Hoyt", -25, -30));
		addPoint(new DeliveryPoint("PLC", -25, 30));
		addPoint(new DeliveryPoint("STEM", -40, 0));
		addPoint(new DeliveryPoint("Rockwell", -50, 0));
		addPoint(new DeliveryPoint("Crawford", -75, -30));

		this.foods = new ArrayList<FoodItem>();	
		// add default foods
		FoodItem burger = new FoodItem("Burger", 6.0 / 16.0, 0);
		FoodItem fries = new FoodItem("Fries", 4.0 / 16.0, 0);
		FoodItem drink = new FoodItem("Drink", 14.0 / 16.0, 0);
		
		foods.add(burger);
		foods.add(fries);
		foods.add(drink);

		this.meals = new ArrayList<Meal>();
		// add default meals
		HashMap<FoodItem, Integer> typical = new HashMap<>();
		typical.put(burger, 1);
		typical.put(fries, 1);
		typical.put(drink, 1);
		meals.add(new Meal(typical, 100));

		// create default drone and default shift details
		this.drone = new Drone();
		this.shiftDetails = new ShiftDetails();
	}
	
	/**
	 * constructor for a previously existing location (loaded from saved file?)
	 * @param name - name of the location
	 * @param homeName - name of the starting point/ origin
	 * @param deliveryPoints - list of all delivery points
	 * @param meals - list of meals 
	 * @param drone - drone options
	 * @param foods - list of all available foods
	 */
	public Location(String name, String homeName, HashMap<DeliveryPoint, Boolean> deliveryPoints,
			ArrayList<Meal> meals,Drone drone, ArrayList<FoodItem> foods, ShiftDetails shiftDetails) {
		
		this.deliveryPoints = deliveryPoints;
		this.home = new DeliveryPoint(homeName, 0, 0);
		this.name = name;
		this.meals = meals;
		this.drone = drone;
		this.foods = foods;		
		this.shiftDetails = shiftDetails;
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
	
	
	/*-------------------- End DeliveryPoint/ basic Location stuff ------------------------*/
	
	public ArrayList<Meal> getMeals() {
		return meals;
	}
	
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}
	
	public Drone getDrone() {
		return drone;
	}
	
	public void setDrone(Drone drone) {
		this.drone = drone;
	}
	
	public ArrayList<FoodItem> getFoods(){
		return foods;
	}
	
	public void setFoods(ArrayList<FoodItem> foods){
		this.foods = foods;
	}
	
	public ShiftDetails getShiftDetails() {
		return this.shiftDetails;
	}
	
	public void setShiftDetails(ShiftDetails shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
	
	/**
	 * add a food to the arraylist
	 * @param food to be added
	 */
	public void addFood(FoodItem f) {
		foods.add(f);
	}
	
	/**
	 * delete a food from the arraylist
	 * @param food to be deleted
	 */
	public void deleteFood(FoodItem f) {
		//if I am checking a food to be equal to another does this work? 
		//or would they be different references
		for(int i = 0; i<foods.size(); i++) {
			if (foods.get(i)==f) {
				foods.remove(i);
			}
		}
	}
	
	
	/**
	 * add a meal to the arraylist
	 * @param m meal to be added
	 */
	public void addMeal(Meal m) {
		meals.add(m);
	}
	
	/**
	 * delete a meal from the arraylist
	 * @param m meal to delete
	 */
	public void deleteMeal(Meal m) {
		//if I am checking a meal to be equal to another does this work? 
		//or would they be different references
		for(int i = 0; i<meals.size(); i++) {
			if (meals.get(i)==m) {
				meals.remove(i);
			}
		}
	}
	
	/**
	 * returns a random meal from the arraylist
	 * @return the random meal
	 */
	public Meal getRandomMeal() {
		Random rand = new Random();
		int index = rand.nextInt(meals.size());
		return meals.get(index);
	}
	
	/**
	 * returns a specific meal from the list
	 * @param n the index of the meal to get
	 * @return a meal from the arraylist
	 */
	public Meal getMeal(int n) { 
		//returns the specified meal
		if(n>=0 && n<meals.size())
			return meals.get(n);
		
		//if the meal was out of bounds inform and return the 0th one
		System.out.println("Index for Meal was Out of Bounds");
		return meals.get(0);	
	}
	
	/**
	 * returns a random delivery point that is toggled on
	 * @return a delivery point
	 */
	public DeliveryPoint getRandomPoint() {
		//converts hashmap keys to a list (for random generation)
		ArrayList<DeliveryPoint> availablePoints = null;
		for (HashMap.Entry<DeliveryPoint,Boolean> element : deliveryPoints.entrySet()) { 
			//if the deliverypoint is turned on add it to the list
			if(element.getValue()) {
				availablePoints.add(element.getKey()); 
			}
		}
		// get random index
		Random rand = new Random();
		int index = rand.nextInt(availablePoints.size());
		
		return availablePoints.get(index);
	}
}
