package javaClasses;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Location implements Serializable {
	
	private static final long serialVersionUID = 13456L;
	
	// hashmap of delivery points, each with a boolean value for active/not active
	private Map<DeliveryPoint, Boolean> deliveryPoints;
	private DeliveryPoint home;
	private String name;
	
	private List<Meal> meals;	//list of all available meals (for this location)
	private Drone drone;
	private List<FoodItem> foods;		//list of all foods  (for this location)
	private ShiftDetails shiftDetails;
	private int numberOfDrones;
	
	
	/**
	 * constructor for a brand new location - defaults to default provided by project description
	 * @param name - name of the location
	 * @param homeName - name of the starting point/ origin
	 */
	public Location(String name, String homeName) {
		deliveryPoints = new HashMap<>();
		this.home = new DeliveryPoint(homeName, 41.154808488498865, -80.07775153085983);
		this.name = name;
		
		// add real delivery points
		addPoint(new DeliveryPoint("PEW", 41.152881903411874, -80.07758203606872));
		addPoint(new DeliveryPoint("HAL", 41.15467560850118, -80.07749045574086));
		addPoint(new DeliveryPoint("Soccer Field", 41.158550264091765, -80.07740477043758));
		addPoint(new DeliveryPoint("PLC", 41.1559095899193, -80.07835384017163));
		addPoint(new DeliveryPoint("MEP", 41.15666590257303, -80.07862836407985));
		addPoint(new DeliveryPoint("STEM", 41.15510275699408, -80.07911920832957));
		addPoint(new DeliveryPoint("Harker", 41.155963085811415, -80.07914616148645));
		addPoint(new DeliveryPoint("Hoyt", 41.1546035027463, -80.07901830412852));
		addPoint(new DeliveryPoint("Hicks", 41.153299367569964, -80.07874418401852));
		addPoint(new DeliveryPoint("TLC", 41.15375838554398, -80.07926083656726));
		addPoint(new DeliveryPoint("Baseball Field", 41.15839619045162, -80.07934393694319));
		addPoint(new DeliveryPoint("Library", 41.15431005663642, -80.07945147948362));
		addPoint(new DeliveryPoint("Rockwell", 41.15553724553622, -80.07945498721149));
		addPoint(new DeliveryPoint("Map", 41.15634090277309, -80.07992987184225));
		addPoint(new DeliveryPoint("Rathburn", 41.15738496547057, -80.08028928785025));
		addPoint(new DeliveryPoint("Hopeman", 41.15428201087024, -80.08066345600783));
		addPoint(new DeliveryPoint("Lincoln", 41.15485555861438, -80.08044619707762));
		addPoint(new DeliveryPoint("Ketler Recreation", 41.15524107111898, -80.08068493869966));
		addPoint(new DeliveryPoint("Chapel", 41.15668501610066, -80.0808744035746));
		addPoint(new DeliveryPoint("Softball Field", 41.15856934057094, -80.08119794326178));
		addPoint(new DeliveryPoint("Zerbe", 41.1542919610577, -80.08127341817142));
		addPoint(new DeliveryPoint("Crawford", 41.15582881514225, -80.08158187220813));
		addPoint(new DeliveryPoint("Memorial", 41.15514147765253, -80.08213022048878));
		addPoint(new DeliveryPoint("President's House", 41.15468383521992, -80.08242774396987));
		addPoint(new DeliveryPoint("Field House", 41.15781060717677, -80.08391673836604));
		addPoint(new DeliveryPoint("Tennis Courts", 41.15805345127432, -80.08502627945477));
		addPoint(new DeliveryPoint("Football Field", 41.15688053608965, -80.08466665710328));
		addPoint(new DeliveryPoint("Colonial Apartments", 41.15695121305507, -80.08621938896987));
		addPoint(new DeliveryPoint("Carnegie Hall", 41.157314636651144, -80.08678326120625));
		
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
		
		//default is to have one drone
		numberOfDrones = 1;
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
	public Location(
			String name, 
			String homeName, 
			HashMap<DeliveryPoint, 
			Boolean> deliveryPoints,
			ArrayList<Meal> meals,
			Drone drone, 
			ArrayList<FoodItem> foods, 
			ShiftDetails shiftDetails, 
			int numberOfDrones) {
		
		this.deliveryPoints = deliveryPoints;
		this.home = new DeliveryPoint(homeName, 0, 0);
		this.name = name;
		this.meals = meals;
		this.drone = drone;
		this.foods = foods;		
		this.shiftDetails = shiftDetails;
		this.numberOfDrones = numberOfDrones;
	}
	
	public Location(Location loc) {
		this.deliveryPoints = loc.deliveryPoints;
		this.home = loc.home;
		this.name = loc.name;
		this.meals = loc.meals;
		this.drone = loc.drone;
		this.foods = loc.foods;		
		this.shiftDetails = loc.shiftDetails;
		this.numberOfDrones = loc.numberOfDrones;
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
	
	/**
	 * @return a copy of the delivery point map
	 */
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
	
	public void setHome(DeliveryPoint home) {
		this.home = home;
	}
	
	public int getNumberOfDrones() {
		return numberOfDrones;
	}
	
	public void setNumberOfDrones(int num) {
		this.numberOfDrones = num;
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
	}
	
	
	/*-------------------- End DeliveryPoint/ basic Location stuff ------------------------*/
	
	/**
	 * 
	 * @returns - the list of meals available 
	 * for this location's simulation (as a copy)
	 */
	public List<Meal> getMeals() {
		//creates a copy
		List<Meal> mealsCopy = new ArrayList<Meal>();
		
		//copies the meals list
		for (Meal m : meals) {
			mealsCopy.add(new Meal(m));
		}
		
		return mealsCopy;	//returns the copy
	}
	
	/**
	 * 
	 * @param meals - sets the this.meals list to the given meals list
	 */
	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}
	
	/**
	 * 
	 * @returns - this's drone object
	 */
	public Drone getDrone() {
		return drone;
	}
	
	/**
	 * 
	 * @param drone - sets the drone object of this to the given drone
	 */
	public void setDrone(Drone drone) {
		this.drone = drone;
	}
	
	/**
	 * 
	 * @returns - the list of foods available (for this location's simulation) - as a copy
	 */
	public List<FoodItem> getFoods(){
		//makes a list for a copy
		List<FoodItem> foodsCopy = new ArrayList<FoodItem>();
		
		//copies the foods from the list
		for (FoodItem f : foods) {
			foodsCopy.add(new FoodItem(f));
		}
		return foodsCopy;	//returns the copy
	}
	
	/**
	 * 
	 * @param foods - sets the list of foods in this to the given food list
	 */
	public void setFoods(List<FoodItem> foods){
		this.foods = foods;
	}
	
	/**
	 * 
	 * @returns - the shift details object (as a copy)
	 */
	public ShiftDetails getShiftDetails() {
		return new ShiftDetails(shiftDetails);
	}
	
	/**
	 * 
	 * @param shiftDetails - sets the shift details object of this to the given shift details object
	 */
	public void setShiftDetails(ShiftDetails shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
	
	
	/**
	 * returns a random meal from the arraylist based off percentages
	 * @return the random meal
	 */
	public Meal getRandomMeal() {
		double lowerBound = 0;
		double upperBound = 0;
		
		//gets random integer from 1-100
		Random rand = new Random();
		int index = rand.nextInt(100);
		//converts index to a double (between 0 and 1)
		
		//goes through list of meals
		for(Meal m : meals) {
			//sets upper bound val to the lowerbound + the meals percent chance
			upperBound = lowerBound + m.getPercentage();
			//checks if the random double was in the range for this meal
			if(index>=lowerBound && index<upperBound) {
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

	public void setName(String name) {
		this.name = name;
	}
}
