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
		
		//https://nathanrooy.github.io/posts/2016-09-07/haversine-with-python/
		
		int radiusOfEarthInMeters = 6371000;
		double feetPerMeter = 3.28084;
		
		double myLatInRad = Math.toRadians(this.lat);
		double bLatInRad = Math.toRadians(b.lat);
		
		double changeInLatitude = Math.toRadians(b.lat - this.lat);
		double changeInLongitude = Math.toRadians(b.lng - this.lng);
		
		double haversine_A = Math.pow(Math.sin(changeInLatitude / 2.0), 2) + 
				Math.cos(myLatInRad) * Math.cos(bLatInRad) *
				Math.pow(Math.sin(changeInLongitude / 2.0), 2.0);
		
		double haversine_C = 2.0 * Math.atan2(Math.sqrt(haversine_A), Math.sqrt(1 - haversine_A));
		
		double inMeters = radiusOfEarthInMeters * haversine_C;
		
		double inFeet = inMeters * feetPerMeter;
		
		return inFeet;
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
