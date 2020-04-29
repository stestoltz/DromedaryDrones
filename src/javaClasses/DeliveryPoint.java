package javaClasses;

import java.io.Serializable;

public class DeliveryPoint implements Serializable {

	private static final long serialVersionUID = 13456L;
	
	private String name;
	private double lat;
	private double lng;
	
	/**
	 * constructor
	 * @param x
	 * @param y
	 * @param name
	 */
	public DeliveryPoint(String name, double lat, double lng) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * Calculates the distance between two points
	 * @param a the first point
	 * @param b the second point
	 * @return the distance between the two points
	 */
	public double distanceInFeet(DeliveryPoint b) {
		double latDist = getLatitudeDistanceInFeet(b);
		double longDist = getLongitudeDistanceInFeet(b);
		
		return Math.sqrt(latDist * latDist + longDist * longDist);
	}
	
	/**
	 * copy constructor
	 * @param other
	 */
	public DeliveryPoint(DeliveryPoint other) {
		this.lat = other.lat;
		this.lng = other.lng;
		this.name = other.name;
	}
	
	public double getLatitudeDistanceInFeet(DeliveryPoint b) {
		return 0;
	}
	
	public double getLongitudeDistanceInFeet(DeliveryPoint b) {
		return 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
