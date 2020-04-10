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
	/**
	 * copy constructor
	 * @param meal
	 * @param percentage
	 */
	public Meal(Meal m) {
		HashMap<FoodItem, Integer> mCopy = new HashMap<FoodItem, Integer>();
		for (HashMap.Entry<FoodItem,Integer> element : m.getMeal().entrySet()) { 
			FoodItem key =  element.getKey(); //get food item
            int value = element.getValue();	//get number of foods
            
            mCopy.put(key, value);	//add food to meal
        }
		this.meal = mCopy;
		this.percentage = m.percentage;
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
		for (HashMap.Entry<FoodItem,Integer> element : meal.entrySet()) { 
			//gets individual food's weight
			ret +=  (element.getKey()).getWeight();
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
		for (HashMap.Entry<FoodItem,Integer> element : meal.entrySet()) { 
			//gets individual food's prep times
			double foodPrepTime = (element.getKey()).getPrepTime();
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
		for (HashMap.Entry<FoodItem,Integer> element : meal.entrySet()) { 
            FoodItem key =  element.getKey(); //get food item
            int value = element.getValue();	//get number of foods
  
            ret += key.getName() + "("+ value +") "; 
        }
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
