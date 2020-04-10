package javaClasses;

import java.io.Serializable;

public class FoodItem implements Serializable { 

	private static final long serialVersionUID = 13456L;
	
	// variables
	private double weight;	//weight of food
	private String name;	//name of food
	private double prepTime;//preptime food takes
	
	/**
	 * Constructor 
	 * @param name - name of food
	 * @param weight - weight of food
	 * @param prepTime -preptime food takes
	 */
	public FoodItem(String name, double weight, double prepTime) {
		this.name = name;			//makes global name the name given
		this.weight = weight;		//makes global weight the weight given
		this.prepTime = prepTime;	// makes global preptime the preptime given
	}
	
	/**
	 * copy constructor
	 * @param f - food item that is put into this
	 */
	public FoodItem(FoodItem f) {
		this.name = f.name;
		this.weight = f.weight;
		this.prepTime = f.prepTime;
		
	}
	
	
	/**
	 * 
	 * @returns - the weight of the foodItem
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * 
	 * @param weight - sets this's weight to the weight given
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * 
	 * @returns - the name of the foodItem
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name - sets the name of this to the name given
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @returns - the preptime of this foodItem
	 */
	public double getPrepTime() {
		return prepTime;
	}
	
	/**
	 * 
	 * @param prepTime - sets the preptime of this to the preptime given
	 */
	public void setPrepTime(double prepTime) {
		this.prepTime = prepTime;
	}
	
	/**
	 * returns the foodItem as a string (the string being just the name)
	 */
	@Override
	public String toString() {
		return name;
	}
}
