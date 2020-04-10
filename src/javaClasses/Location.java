package javaClasses;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Location implements Serializable {
	
	// hashmap of delivery points, each with a boolean value for active/not active
	private Map<DeliveryPoint, Boolean> deliveryPoints;
	private DeliveryPoint home;
	private String name;
	
	private List<Meal> meals;	//list of all available meals (for this location)
	private Drone drone;
	private List<FoodItem> foods;		//list of all foods  (for this location)
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
		
		// add real delivery points
		addPoint(new DeliveryPoint("PEW", 115, -510));
		addPoint(new DeliveryPoint("HAL", 40, -70));
		addPoint(new DeliveryPoint("Soccer Field", 0, 1100));
		addPoint(new DeliveryPoint("PLC", -160, 440));
		addPoint(new DeliveryPoint("MEP", -225, 550));
		addPoint(new DeliveryPoint("STEM", -300, 180));
		addPoint(new DeliveryPoint("Harker", -315, 390));
		addPoint(new DeliveryPoint("Hoyt", -350, -70));
		addPoint(new DeliveryPoint("Hicks", -360, -440));
		addPoint(new DeliveryPoint("TLC", -420, -400));
		addPoint(new DeliveryPoint("Baseball Field", -475, -170));
		addPoint(new DeliveryPoint("Library", -475, -170));
		addPoint(new DeliveryPoint("Rockwell", -480, 290));
		addPoint(new DeliveryPoint("Map", -560, 860));
		addPoint(new DeliveryPoint("Rathburn", -675, 985));
		addPoint(new DeliveryPoint("Hopeman", -715, -225));
		addPoint(new DeliveryPoint("Lincoln", -780, -70));
		addPoint(new DeliveryPoint("Ketler Recreation", -800, 155));
		addPoint(new DeliveryPoint("Chapel", -860, 680));
		addPoint(new DeliveryPoint("Softball Field", -930, 1420));
		addPoint(new DeliveryPoint("Zerbe", -940, -135));
		addPoint(new DeliveryPoint("Crawford", -975, 485));
		addPoint(new DeliveryPoint("Memorial", -1160, 100));
		addPoint(new DeliveryPoint("President's House", -1280, -30));
		addPoint(new DeliveryPoint("Field House", -1625, 1000));
		addPoint(new DeliveryPoint("Tennis Courts", -1880, 1210));
		addPoint(new DeliveryPoint("Football Field", -2065, 885));
		addPoint(new DeliveryPoint("Colonial Apartments", -2330, 770));
		addPoint(new DeliveryPoint("Carnegie Hall", -2455, 835));

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
		meals.add(new Meal(typical, 55));
		
		HashMap<FoodItem, Integer> meal2 = new HashMap<>();
		meal2.put(burger, 2);
		meal2.put(fries, 1);
		meal2.put(drink, 1);
		meals.add(new Meal(meal2, 10));
		
		HashMap<FoodItem, Integer> meal3 = new HashMap<>();
		meal3.put(burger, 1);
		meal3.put(fries, 1);
		meals.add(new Meal(meal3, 20));
		
		HashMap<FoodItem, Integer> meal4 = new HashMap<>();
		meal4.put(burger, 2);
		meal4.put(fries, 1);
		meals.add(new Meal(meal4, 15));

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
	
	public Map<DeliveryPoint, Boolean> getDeliveryPointsMap() {
		HashMap<DeliveryPoint, Boolean> pointsCopy = new HashMap<>();
		
		for (DeliveryPoint dp : deliveryPoints.keySet()) {
			
			pointsCopy.put(new DeliveryPoint(dp), Boolean.valueOf(deliveryPoints.get(dp)));
			
		}
		
		return pointsCopy;
	}
	
	public void setDeliveryPoints(Map<DeliveryPoint, Boolean> points) {
		this.deliveryPoints = points;
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
	
	public List<Meal> getMeals() {
		List<Meal> mealsCopy = new ArrayList<Meal>();
		
		for (Meal m : meals) {
			mealsCopy.add(new Meal(m));
		}
		
		return mealsCopy;
	}
	
	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}
	
	public Drone getDrone() {
		return drone;
	}
	
	public void setDrone(Drone drone) {
		this.drone = drone;
	}
	
	public List<FoodItem> getFoods(){
		List<FoodItem> foodsCopy = new ArrayList<FoodItem>();
		
		for (FoodItem f : foods) {
			foodsCopy.add(new FoodItem(f));
		}
		return foodsCopy;
	}
	
	public void setFoods(List<FoodItem> foods){
		this.foods = foods;
	}
	
	public ShiftDetails getShiftDetails() {
		return new ShiftDetails(shiftDetails);
	}
	
	public void setShiftDetails(ShiftDetails shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
	
	/**
	 * add a food to the arraylist
	 * @param food to be added
	 * @return - a boolean to alert form if it was added or not
	 */
	/*public boolean addFood(FoodItem f) {
		for(FoodItem food : foods) {
			if(f.toString().equals(food.toString())) {
				System.out.println("There is already a food with that name");
				return false;
			}
		}
		foods.add(f);
		return true;
	}*/
	
	/**
	 * delete a food from the arraylist
	 * @param food to be deleted
	 */
	/*public void deleteFood(FoodItem f) {
		//if I am checking a food to be equal to another does this work? 
		//or would they be different references
		for(int i = 0; i<foods.size(); i++) {
			if (foods.get(i)==f) {
				foods.remove(i);
			}
		}
	}*/
	
	
	/**
	 * add a meal to the arraylist
	 * @param m meal to be added
	 * @return - a boolean to tell the fx form not to display the meal
	 */
	/*public boolean addMeal(Meal m) {
		for(Meal meal : meals) {
			if(m.toString().equals(meal.toString())) {
				System.out.println("This is already a meal");
				return false;
			}
		}
		meals.add(m);
		return true;
	}*/
	
	/**
	 * delete a meal from the arraylist
	 * @param m meal to delete
	 */
	/*public void deleteMeal(Meal m) {
		//if I am checking a meal to be equal to another does this work? 
		//or would they be different references
		for(int i = 0; i<meals.size(); i++) {
			if (meals.get(i)==m) {
				meals.remove(i);
			}
		}
	}*/
	
	/**
	 * returns a random meal from the arraylist based off percentages
	 * @return the random meal
	 */
	public Meal getRandomMeal() {
		double lowerBound = 0;
		double upperBound = 0;
		double indexDecimal;
		
		//gets random integer from 1-100
		Random rand = new Random();
		int index = rand.nextInt(100);
		//converts index to a double (between 0 and 1)
		indexDecimal = index/100;
		
		//goes through list of meals
		for(Meal m : meals) {
			//sets upper bound val to the lowerbound + the meals percent chance
			upperBound = lowerBound + m.getPercentage();
			//checks if the random double was in the range for this meal
			if(indexDecimal>=lowerBound && indexDecimal<upperBound) {
				return m;
			}
			//sets lowerBound to upperBound for next iteration through loop
			lowerBound = upperBound;
		}
		return null;	//returns null if no meal is found
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
		ArrayList<DeliveryPoint> availablePoints = new ArrayList<>();
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
