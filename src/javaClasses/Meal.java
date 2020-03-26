package javaClasses;
import java.util.HashMap;

public class Meal {
	
	private HashMap<FoodItem, Integer> meal;
	private double percentage;
	
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
	 * adds count number of foodItems to the hashmap of foods
	 * @param f
	 * @param count
	 */
	public void addFood(FoodItem f, int count) {
		meal.put(f, count);
	}
	
	/**
	 * deletes a food from the hashmap
	 * @param f - takes in the FoodItem to delete
	 */
	public void deleteFood(FoodItem f) {
		meal.remove(f);
	}
	
	/**
	 * edits the number of a FoodItem in a given meal
	 * @param f - the FoodItem who's quantity is being changed
	 * @param amount - the new quantity
	 */
	public void editQuantity(FoodItem f, int count) {
		meal.remove(f);
		meal.put(f, count);
	}
	
	/**
	 * gets the weight of the entire meal
	 * @return - returns the weight
	 */
	public double getMealWeight() {
		double ret = 0;	//addition of each food's weight
		for (HashMap.Entry element : meal.entrySet()) { 
			//gets individual food's weight
			ret +=  ((FoodItem) element.getKey()).getWeight();
		}
		return ret;
	}
	
	/**
	 * gets the prep time of the meal
	 * (this would be the longest prep time of all the foods)
	 * 
	 * @return - returns the prep time
	 */
	public double getMealPrepTime() {
		double ret = 0;	//longest prepTime
		for (HashMap.Entry element : meal.entrySet()) { 
			//gets individual food's prep times
			double foodPrepTime = ((FoodItem) element.getKey()).getPrepTime();
			if(foodPrepTime > ret) {	//gets max prep time
				ret =  foodPrepTime;
			}
		}
		return ret;
	}
	
	/**
	 * returns a readable string giving the FoodItems, 
	 * their quantities and their percentages
	 */
	public String toString() {
		String ret = "";
		for (HashMap.Entry element : meal.entrySet()) { 
            FoodItem key =  (FoodItem) element.getKey(); 
  
            // Add some bonus marks 
            // to all the students and print it 
            int value = (int)element.getValue();
  
            ret += key.getName() + "("+ value +") "; 
        }
		//ret += "\nOrder Chance: "+percentage + "%";
		return ret;
	}
	
	/**
	 * returns a readable string giving the FoodItems, 
	 * their quantities and their percentages
	 */
	public boolean stringEquals(String meal2) {
		if(meal.toString().equals(meal2)) {
			return true;
		}
		return false;
	}
}
