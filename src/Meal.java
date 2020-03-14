import java.util.HashMap;

public class Meal {
	
	HashMap<FoodItem, Integer> meal;
	double percentage;
	
	/**
	 * constructor
	 * @param meal
	 * @param percentage
	 */
	public Meal(HashMap<FoodItem, Integer> meal, double percentage) {
		this.meal = meal;
		this.percentage = percentage;
	}

	public HashMap<FoodItem, Integer> getMeal() {
		return meal;
	}

	public void setMeal(HashMap<FoodItem, Integer> meal) {
		this.meal = meal;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
	/**
	 * adds a food to the hashmap of foods
	 * @param f - takes in a FoodItem to add to the map
	 */
	public void addFood(FoodItem f) {
		
	}
	
	/**
	 * deletes a food from the hashmap
	 * @param f - takes in the FoodItem to delete
	 */
	public void deleteFood(FoodItem f) {
		
	}
	
	/**
	 * edits the number of a FoodItem in a given meal
	 * @param f - the FoodItem who's quantity is being changed
	 * @param amount - the new quantity
	 */
	public void editQuantity(FoodItem f, int amount) {
		
	}
	
	/**
	 * gets the weight of the entire meal
	 * @return - returns the weight
	 */
	public double getMealWeight() {
		
	}
	
	/**
	 * gets the prep time of the meal
	 * @return - returns the prep time
	 */
	public double getMealPrepTime() {
		
	}
	
	/**
	 * returns a readable string giving the FoodItems, 
	 * their quantities and their percentages
	 */
	public String toString() {
		
	}
}
