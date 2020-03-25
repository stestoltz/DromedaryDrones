package javaClasses;

public class DeliveryPoint {

	private String name;
	private int x;
	private int y;
	
	/**
	 * constructor
	 * @param x
	 * @param y
	 * @param name
	 */
	public DeliveryPoint(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * copy constructor
	 * @param other
	 */
	public DeliveryPoint(DeliveryPoint other) {
		this.x = other.x;
		this.y = other.y;
		this.name = other.name;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
