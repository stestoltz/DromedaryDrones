package javaClasses;

import java.io.Serializable;

public class Drone implements Serializable {
	
	private static final long serialVersionUID = 13456L;
	
	private double cargoWeight; /* default: 12 lbs */
	private double averageCruisingSpeed; /* default: 25 miles per hour */
	private double maxFlightTime; /* default: 20 minutes */
	private double turnAroundTime; /* default: 2.5 minutes */
	private double deliveryTime; /* default: 30 seconds */
	private double userSpecifiedWeight; /*default: equal to cargoWeight */
	
	/**
	 * constructor
	 * @param cargoWeight
	 * @param averageCruisingSpeed
	 * @param maxFlightTime
	 * @param turnAroundTime
	 * @param defaultDeliveryTime
	 * @param userSpecifiedWeight
	 */
	public Drone(double cargoWeight, double averageCruisingSpeed, double maxFlightTime, double turnAroundTime,
			double defaultDeliveryTime, double userSpecifiedWeight) {
		this.cargoWeight = cargoWeight;
		this.averageCruisingSpeed = averageCruisingSpeed;
		this.maxFlightTime = maxFlightTime;
		this.turnAroundTime = turnAroundTime;
		this.deliveryTime = defaultDeliveryTime;
		this.userSpecifiedWeight = userSpecifiedWeight;
	}
	
	/**
	 * default constructor
	 */
	public Drone() {
		this.cargoWeight = 12;
		this.averageCruisingSpeed = 25;
		this.maxFlightTime = 20;
		this.turnAroundTime = 2.5;
		this.deliveryTime = 30;
		this.userSpecifiedWeight = cargoWeight;
	}
	
	/**
	 * copy constructor
	 * @param drone
	 */
	public Drone(Drone drone) {
		this.cargoWeight = drone.cargoWeight;
		this.averageCruisingSpeed = drone.averageCruisingSpeed;
		this.maxFlightTime = drone.maxFlightTime;
		this.turnAroundTime = drone.turnAroundTime;
		this.deliveryTime = drone.deliveryTime;
		this.userSpecifiedWeight = drone.userSpecifiedWeight;
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
	 * @return max flight time in seconds
	 */
	public double getMaxFlightTimeInSeconds() {
		return maxFlightTime * 60.0;
	}
	
	/**
	 * @return max flight time in feet - distance = rate * time
	 */
	public double getMaxFlightDistanceInFeet(int numStops) {
		// max flight time decreases for each stop made
		double maxFlightTimeAfterStops = getMaxFlightTimeInSeconds() - (numStops * getTurnAroundTimeSeconds());
		
		return getAverageCruisingSpeedFeetPerSecond() * maxFlightTimeAfterStops;
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
	
	public double getUserSpecifiedWeight() {
		return userSpecifiedWeight;
	}
	
	/**
	 * @param userSpecifiedWeight max weight for the drone as given by the user
	 */
	public void setUserSpecifiedWeight(double userSpecifiedWeight) {
		this.userSpecifiedWeight = userSpecifiedWeight;
	}
	
}
