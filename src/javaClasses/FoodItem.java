package javaClasses;

public class FoodItem {
	
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
		this.name = name;
		this.weight = weight;
		this.prepTime = prepTime;
	}
	
	/**
	 * copy constructor
	 * @param f
	 */
	public FoodItem(FoodItem f) {
		this.name = f.name;
		this.weight = f.weight;
		this.prepTime = f.prepTime;
		
	}
	
	
	/**
	 * constructor
	 * @param weight
	 * @param name
	 * @param prepTime
	 */
	public FoodItem(double weight, String name, double prepTime) {
		this.weight = weight;
		this.name = name;
		this.prepTime = prepTime;
	}
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrepTime() {
		return prepTime;
	}
	
	public void setPrepTime(double prepTime) {
		this.prepTime = prepTime;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
