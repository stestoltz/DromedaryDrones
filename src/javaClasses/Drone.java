package javaClasses;

public class Drone {
	
	private double cargoWeight; /* default: 12 lbs */
	private double averageCruisingSpeed; /* default: 20 miles per hour */
	private double maxFlightTime; /* default: 20 minutes */
	private double turnAroundTime; /* default: 3 minutes */
	private double deliveryTime; /* default: 30 seconds */
	
	/**
	 * constructor
	 * @param cargoWeight
	 * @param averageCruisingSpeed
	 * @param maxFlightTime
	 * @param turnAroundTime
	 * @param defaultDeliveryTime
	 */
	public Drone(double cargoWeight, double averageCruisingSpeed, double maxFlightTime, double turnAroundTime,
			double defaultDeliveryTime) {
		this.cargoWeight = cargoWeight;
		this.averageCruisingSpeed = averageCruisingSpeed;
		this.maxFlightTime = maxFlightTime;
		this.turnAroundTime = turnAroundTime;
		this.deliveryTime = defaultDeliveryTime;
	}
	
	/**
	 * default constructor
	 */
	public Drone() {
		this.cargoWeight = 12;
		this.averageCruisingSpeed = 20;
		this.maxFlightTime = 20;
		this.turnAroundTime = 3;
		this.deliveryTime = 30;
	}
	
	/**
	 * @return cargo weight in lbs
	 */
	public double getCargoWeight() {
		return cargoWeight;
	}
	
	/**
	 * @param cargoWeight cargo weight in lbs
	 */
	public void setCargoWeight(double cargoWeight) {
		this.cargoWeight = cargoWeight;
	}
	
	/**
	 * @return average cruising speed in miles per hour
	 */
	public double getAverageCruisingSpeed() {
		return averageCruisingSpeed;
	}
	
	/**
	 * @return average cruising speed in feet per second
	 */
	public double getAverageCruisingSpeedFeetPerSecond() {
		double convertedCruisingSpeed = averageCruisingSpeed;
		
		convertedCruisingSpeed *= 5280; // miles to feet
		convertedCruisingSpeed /= 3600; // hours to seconds
		
		return convertedCruisingSpeed;
	}
	
	/**
	 * @param averageCruisingSpeed average cruising speed in miles per hour
	 */
	public void setAverageCruisingSpeed(double averageCruisingSpeed) {
		this.averageCruisingSpeed = averageCruisingSpeed;
	}
	
	/**
	 * @return max flight time in minutes
	 */
	public double getMaxFlightTime() {
		return maxFlightTime;
	}
	
	/**
	 * @param maxFlightTime max flight time in minutes
	 */
	public void setMaxFlightTime(double maxFlightTime) {
		this.maxFlightTime = maxFlightTime;
	}
	
	/**
	 * @return turn around time in minutes
	 */
	public double getTurnAroundTime() {
		return turnAroundTime;
	}
	
	/**
	 * @return turn around time in seconds
	 */
	public double getTurnAroundTimeSeconds() {
		return turnAroundTime * 60;
	}
	
	/**
	 * @param turnAroundTime turn around time in minutes
	 */
	public void setTurnAroundTime(double turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}
	
	/**
	 * @return delivery time in seconds
	 */
	public double getDeliveryTime() {
		return deliveryTime;
	}
	
	/**
	 * @param defaultDeliveryTime delivery time in seconds
	 */
	public void setDeliveryTime(double defaultDeliveryTime) {
		this.deliveryTime = defaultDeliveryTime;
	}
	
	
}
