package javaClasses;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Results {

	private List<Double> times;
	
	/**
	 * Initializes the times list
	 */
	public Results() {
		times = new ArrayList<>();
	}
	
	/**
	 * @return a copy of the internal times list
	 */
	public List<Double> getTimes() {
		return new ArrayList<Double>(times);
	}
	
	/**
	 * @return a delimited string of the times
	 */
	public String getTimesString(String delim) {
		List<String> timesAsStrings = new ArrayList<>();
		
		for (double t : times) {
			timesAsStrings.add(Double.toString(t));
		}
		
		return String.join(delim, timesAsStrings);
	}
	
	/**
	 * Add a time to the times list
	 * @param time
	 */
	public void addTime(double time) {
		times.add(time);
	}
	
	/**
	 * Add a collection of times to the times list
	 * @param newTimes
	 */
	public void addTimes(Collection<Double> times) {
		this.times.addAll(times);
	}
	
	/**
	 * Calculates and returns the worst (largest) time in the list
	 * @return worst time in the list
	 * @throws Exception if list is empty
	 */
	public double worstTime() throws Exception {
		if (times.size() == 0) {
			throw new Exception("Attempted to max empty results");
		}
		
		return Collections.max(times);
	}
	
	/**
	 * Calculates and returns the average time in the list
	 * @return average time in the list
	 * @throws Exception if list is empty
	 */
	public double averageTime() throws Exception {
		if (times.size() == 0) {
			throw new Exception("Attempted to average empty results");
		}
		
		double sum = 0;
		
		for (double time : times) {
			sum += time;
		}
		
		return sum / times.size();
	}
	
}
