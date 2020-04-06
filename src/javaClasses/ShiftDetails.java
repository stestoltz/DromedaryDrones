package javaClasses;

public class ShiftDetails {
	
	private int numberOfShifts;
	private int hoursInShift;
	private int ordersPerHour[]; //size of hoursInShift
	
	public ShiftDetails() {
		this.numberOfShifts = 50;
		this.hoursInShift = 4;
		this.ordersPerHour = new int[4];
		this.ordersPerHour[0] = 15;
		this.ordersPerHour[1] = 17;
		this.ordersPerHour[2] = 22;
		this.ordersPerHour[3] = 15;
		//this.ordersPerHour = new int[] {200, 200, 200, 200};
	}
	
	/**
	 * constructor
	 * @param numberOfShifts
	 * @param hoursInShift
	 * @param ordersPerHour
	 */
	public ShiftDetails(int numberOfShifts, int hoursInShift, int[] ordersPerHour) {
		this.numberOfShifts = numberOfShifts;
		this.hoursInShift = hoursInShift;
		this.ordersPerHour = ordersPerHour;
	}


	public int getNumberOfShifts() {
		return numberOfShifts;
	}


	public void setNumberOfShifts(int numberOfShifts) {
		this.numberOfShifts = numberOfShifts;
	}


	public int getHoursInShift() {
		return hoursInShift;
	}


	public void setHoursInShift(int hoursInShift) {
		this.hoursInShift = hoursInShift;
	}


	public int[] getOrdersPerHour() {
		return ordersPerHour;
	}


	public void setOrdersPerHour(int[] ordersPerHour) {
		this.ordersPerHour = ordersPerHour;
	}

	/**
	 * allow the user to change a number of orders in an hour in the shift
	 * @param hour - the hour that is being changed
	 * @param orders - the number of orders in that hour
	 */
	public void editOrderPerHour(int hour, int orders) {
		
	}
}
