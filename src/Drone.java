
public class Drone {
	
	private double cargoWeight;
	private double averageCruisingSpeed;
	private double maxFlightTime;
	private double turnAroundTime;
	private double defaultDeliveryTime;
	
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
		this.defaultDeliveryTime = defaultDeliveryTime;
	}
	public double getCargoWeight() {
		return cargoWeight;
	}
	public void setCargoWeight(double cargoWeight) {
		this.cargoWeight = cargoWeight;
	}
	public double getAverageCruisingSpeed() {
		return averageCruisingSpeed;
	}
	public void setAverageCruisingSpeed(double averageCruisingSpeed) {
		this.averageCruisingSpeed = averageCruisingSpeed;
	}
	public double getMaxFlightTime() {
		return maxFlightTime;
	}
	public void setMaxFlightTime(double maxFlightTime) {
		this.maxFlightTime = maxFlightTime;
	}
	public double getTurnAroundTime() {
		return turnAroundTime;
	}
	public void setTurnAroundTime(double turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}
	public double getDefaultDeliveryTime() {
		return defaultDeliveryTime;
	}
	public void setDefaultDeliveryTime(double defaultDeliveryTime) {
		this.defaultDeliveryTime = defaultDeliveryTime;
	}
	
	
}
