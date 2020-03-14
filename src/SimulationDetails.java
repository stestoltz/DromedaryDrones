import java.util.ArrayList;

public class SimulationDetails {
		
	ArrayList<Meal> meals;
	Location location;
	ShiftDetails shift;
	Drone drone;
	
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
	public ArrayList<Meal> getMeals() {
		return meals;
	}
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}
	public Location getLocation() {
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
		
	}
	
	/**
	 * delete a meal from the arraylist
	 * @param m meal to delete
	 */
	public void deleteMeal(Meal m) {
	
	}
	
	/**
	 * returns a random meal from the arraylist
	 * @return the random meal
	 */
	public Meal getRandomMeal() {
		
	}
	
	/**
	 * returns a random delivery point that is toggled on
	 * @return a delivery point
	 */
	public DeliveryPoint getRandomPoint() {
		
	}
	
	/**
	 * returns a specific list
	 * @param n the index of the meal to get
	 * @return a meal from the arraylist
	 */
	public Meal getMeal(int n) { 
		
	}
}
