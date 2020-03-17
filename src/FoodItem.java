
public class FoodItem {
	
	private double weight;
	private String name;
	private double prepTime;
	
	// constructor
	
	
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
	
	

}
