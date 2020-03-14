
public class ShiftDetails {
	
	int numberOfShifts;
	int hoursInShift;
	int ordersPerHour[]; //size of hoursInShift
	
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
