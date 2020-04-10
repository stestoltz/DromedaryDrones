package javaClasses;
import java.util.HashMap;

public class Meal {
	
	//variables
	private HashMap<FoodItem, Integer> meal;	//map of foods corresponding to the number of them in the meal
	private double percentage;					//likelyhood of meal being ordered
	
	/**
	 * constructor used to make a meal when given a map (of foods) and a percentage
	 * @param meal
	 * @param percentage
	 */
	public Meal(HashMap<FoodItem, Integer> meal, double percentage) {
		this.meal = meal;	//sets the global hashmap to the map given
		this.percentage = percentage;	//sets the percentage to the given percentage
	}
	
	/**
	 * copy constructor
	 * @param m - takes in a Meal object m
	 */
	public Meal(Meal m) {
		//makes a copy of m's hashmap
		HashMap<FoodItem, Integer> mCopy = new HashMap<FoodItem, Integer>();
		for (HashMap.Entry<FoodItem,Integer> element : m.getMeal().entrySet()) { 
			FoodItem key =  element.getKey(); //get food item
            int value = element.getValue();	//get number of foods
            
            mCopy.put(key, value);	//add food to meal
        }
		//sets the global hashmap to be the copied hashmap
		this.meal = mCopy;
		this.percentage = m.percentage;	//sets the percentage to the copied meal's percent
	}

	/**
	 * method that returns the hashmap of the meal (the foods and their count)
	 * @returns - hashmap of foods/integers
	 */
	public HashMap<FoodItem, Integer> getMeal() {
		return meal;
	}

	/**
	 * sets the hashmap to the new hashmap given
	 * @param meal - takes in a hashmap to become the new global hashmap
	 */
	public void setMeal(HashMap<FoodItem, Integer> meal) {
		this.meal = meal;
	}

	/**
	 * gets the percentage for this meal
	 * @return - percentage as a double
	 */
	public double getPercentage() {
		return percentage;
	}

	/**
	 * sets the percentage to a new double value
	 * @param percentage - new double value
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
	/**
	 * adds a foodItem to the hashmap of foods with 'count' number of them in the meal
	 * @param f - the foodItem being added
	 * @param count - the number of the given foodItem in the meal
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
		double ret = 0;	//sums all of the foods' weight
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
	 * returns a readable string giving the FoodItems and their quantities
	 */
	public String toString() {
		String ret = "";
		//loops through the map for each food
		for (HashMap.Entry<FoodItem,Integer> element : meal.entrySet()) { 
            FoodItem key =  element.getKey(); //get food item
            int value = element.getValue();	//get number of foods
  
            ret += key.getName() + "("+ value +") "; 
        }
		return ret;
	}
	
	/**
	 * returns true if the readable string of one meal is equal to another
	 */
	public boolean stringEquals(String meal2) {
		if(meal.toString().equals(meal2)) {
			return true;
		}
		return false;
	}
}
