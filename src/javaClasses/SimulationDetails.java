package javaClasses;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationDetails {
		
	private static ArrayList<Meal> meals;
	private static Location location;
	private ShiftDetails shift;
	private Drone drone;
	
	/**
	 * constructor
	 * @param meals
	 * @param location
	 * @param shift
	 * @param drone
	 */
	public SimulationDetails(ArrayList<Meal> meals, Location location, ShiftDetails shift, Drone drone) {
		this.meals = meals;
		this.location = location;
		this.shift = shift;
		this.drone = drone;
	}
	
	public static ArrayList<Meal> getMeals() {
		return meals;
	}
	
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}
	
	public static Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public ShiftDetails getShift() {
		return shift;
	}
	
	public void setShift(ShiftDetails shift) {
		this.shift = shift;
	}
	
	public Drone getDrone() {
		return drone;
	}
	
	public void setDrone(Drone drone) {
		this.drone = drone;
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
	 * returns a random delivery point that is toggled on
	 * @return a delivery point
	 */
	public DeliveryPoint getRandomPoint() {
		// get list of delivery points
		List<DeliveryPoint> points = location.getDeliveryPoints();
		
		// get random index
		Random rand = new Random();
		int index = rand.nextInt(points.size());
		
		return points.get(index);
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
}
