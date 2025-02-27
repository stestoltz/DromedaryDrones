package javaClasses;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ShiftDetails implements Serializable {
	
	private static final long serialVersionUID = 13456L;
	
	private int numberOfShifts;
	private int hoursInShift;
	private List<Integer> ordersPerHour; //size of hoursInShift
	
	public ShiftDetails() {
		this.numberOfShifts = 50;
		this.hoursInShift = 4;
		this.ordersPerHour = new ArrayList<>();
		this.ordersPerHour.add(38);
		this.ordersPerHour.add(45);
		this.ordersPerHour.add(60);
		this.ordersPerHour.add(30);
	}
	
	/**
	 * constructor
	 * @param numberOfShifts
	 * @param hoursInShift
	 * @param ordersPerHour
	 */
	public ShiftDetails(int numberOfShifts, int hoursInShift, List<Integer> ordersPerHour) {
		this.numberOfShifts = numberOfShifts;
		this.hoursInShift = hoursInShift;
		this.ordersPerHour = ordersPerHour;
	}
	
	public ShiftDetails(ShiftDetails shiftDetails) {
		this.numberOfShifts = shiftDetails.numberOfShifts;
		this.hoursInShift = shiftDetails.hoursInShift;
		this.ordersPerHour = new ArrayList<>(shiftDetails.ordersPerHour);
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


	public List<Integer> getOrdersPerHour() {
		return ordersPerHour;
	}


	public void setOrdersPerHour(List<Integer> ordersPerHour) {
		this.ordersPerHour = ordersPerHour;
	}
}
