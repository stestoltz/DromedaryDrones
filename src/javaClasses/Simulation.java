package javaClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;

public class Simulation {

	private Location location;
	
	private ArrayList<String> firstNames = new ArrayList<String>();
	private ArrayList<String> lastNames = new ArrayList<String>();

	/**
	 * Constructor for simulation
	 * Copy the details passed in to member details variable
	 * @param details the options for this simulation
	 */
	public Simulation(Location location) {
		this.location = location;
		
		firstNames.add("Alexander");
		firstNames.add("Drew");
		firstNames.add("Mark");
		firstNames.add("Alice");
		firstNames.add("Julie");
		firstNames.add("Andrew");
		firstNames.add("Grace");
		
		lastNames.add("Smith");
		lastNames.add("Johnson");
		lastNames.add("Williams");
		lastNames.add("Jones");
		lastNames.add("Brown");
		lastNames.add("Hall");
		lastNames.add("Young");
	}

	/**
	 * Run a simulation and return one results object for each packing algorithm
	 * @param packingAlgorithms which packing algorithms to use
	 * 		Will run one simulation for each packing algorithm
	 * @param routingAlgorithm which routing algorithm to use
	 * @return array of results objects, one per packing algorithm
	 */
	public Results[] runSimulation(RoutingAlgorithm routingAlgorithm) {
	
		Results[] simulationResults = new Results[2];
		
		// generate all orders for this simulation
		
		int numShifts = location.getShiftDetails().getNumberOfShifts();
		
		List<Queue<Order>> allOrders = new ArrayList<Queue<Order>>();
		
		for (int i = 0; i < numShifts; i++) {
			allOrders.add(generateOrders());
		}
		
		double maxDistanceBetweenPoints = location.maxDistanceBetweenPoints();
		
		// for each packing algorithm
		for (int p = 0; p < 2; p++) {
		
			// create results[] for each shift's results
			Results[] shiftResults = new Results[location.getShiftDetails().getNumberOfShifts()];
			
			// for each shift
			for (int i = 0; i < shiftResults.length; i++) {
				
				// MUST BE A SHALLOW COPY so that the delivered times are changed
				Queue<Order> packingAlgorithmsOrders = new LinkedList<Order>(allOrders.get(i));
				
				PackingAlgorithm packingAlgorithm;
				if (p == 0) {
					packingAlgorithm = new FIFOPacking(packingAlgorithmsOrders, location.getDrone(), location.getHome());
				} else {
					packingAlgorithm = new KnapsackPacking(packingAlgorithmsOrders, location.getDrone(), location.getHome());
				}
		
				// process the orders into drone trips
		
				// start at time of first order
				double simulationTime = packingAlgorithm.nextOrderTime();
				
				List<DroneTrip> trips = new ArrayList<>();
				
				//keep track of the next time that each drone will be available
				List<Double> nextAvailableTime = new ArrayList<>();
				
				for (int droneNumber = 0; droneNumber < location.getNumberOfDrones(); droneNumber++) {
					nextAvailableTime.add(simulationTime);
				}
				
				// until all of the orders have been shipped
				while (packingAlgorithm.hasNextOrder()) {
					
					// wait until we have an order
					if (simulationTime < packingAlgorithm.nextOrderTime()) {
						simulationTime = packingAlgorithm.nextOrderTime();
					}
					
					// ASSUMPTION: IF WE HAVE AN ORDER, SHIP IT
					//use all of the drones that we have
					for (int droneNumber = 0; droneNumber < location.getNumberOfDrones(); droneNumber++) {
						double droneTime = nextAvailableTime.get(droneNumber);
						
						//make sure that there are orders left in the shift
						if (packingAlgorithm.hasNextOrder()) {
							//make sure those orders have times before the simulation time and
							//that the drone is available
							if (packingAlgorithm.nextOrderTime() <= simulationTime && 
									nextAvailableTime.get(droneNumber) <= simulationTime) {
								
								// find a drone's worth of orders available now
								List<Order> nextTripOrders = packingAlgorithm.nextOrder(simulationTime);
								
								// route the drone for this trip
								DroneTrip trip = routingAlgorithm.createTrip(nextTripOrders, location.getHome());
								
								//assign times for the drone trip
								droneTime += processTrip(trip, simulationTime);
								
								//add the trip to the list of trips
								trips.add(trip);
								
								//add turn around time
								droneTime += location.getDrone().getTurnAroundTimeSeconds();
								
								nextAvailableTime.set(droneNumber, droneTime);
							}
						}
					}

					//this updates the overall simulation time to the next time that a drone is available
						//after sending out all of the ready orders
					Collections.sort(nextAvailableTime);
					simulationTime = nextAvailableTime.get(0);
				}
				
				// generate results for this shift and then to shift results[]
				shiftResults[i] = generateResults(trips);
			}
			
			// all shifts are done - this packing algorithm's simulation is done
			
			// aggregate all shift's results into one results object and add it to simulationResults
			simulationResults[p] = generateResults(shiftResults);
		
		}
		
		System.out.println(simulationResults);
		return simulationResults;
	}

	/**
	 * Generates a queue of orders for one shift
	 * @param packingAlgorithm the packing algorithm to use
	 * @return the queue
	 */
	public Queue<Order> generateOrders() {
		Queue<Order> q = new LinkedList<Order>();
		double timeStart, timeIncrease;
		Random rand = new Random();
		//generate orders for each hour of the shift
		for (int i=0; i<location.getShiftDetails().getHoursInShift(); i++) {
			int numOrders = location.getShiftDetails().getOrdersPerHour().get(i);
			//the staring time at the beginning of the hour
			timeStart = i*3600;
			
			ArrayList<Double> times = new ArrayList<Double>();
			
			//generate the time stamps for the orders for the hour
			for (int j=0; j<numOrders; j++) {
				timeIncrease = rand.nextInt(3600);
				times.add(timeStart + timeIncrease);
			}
			
			//sort the times
			Collections.sort(times);
			
			//put all of the new orders with times in the full q
			for (double t : times) {
				q.add(generateOrder(t));
			}
		} //loop of hours in shift
		return q;
	}

	/**
	 * Figure out how long the trip takes
	 * Updates the delivered time of each order object in the drone trip
	 * @param trip the trip to process
	 * @param the starting time of the trip
	 * @return the amount of time the drone was away from home
	 */
	public double processTrip(DroneTrip trip, double startTime) {
		
		double time = startTime;
		
		// get from location class?
		DeliveryPoint home = location.getHome();
		
		Order[] stops = trip.getStops();
		
		// each iteration calculates the time from the previous stop to the current one
		for (int i = 0; i <= stops.length; i++) {
			
			// calculate time to get to point
			
			// home to first stop
			if (i == 0) {
				time += calcTime(home, stops[i].getDeliveryPoint(), location.getDrone().getAverageCruisingSpeedFeetPerSecond());
				
			// last stop back to home
			} else if (i == stops.length) {
				time += calcTime(stops[i - 1].getDeliveryPoint(), home, location.getDrone().getAverageCruisingSpeedFeetPerSecond());
			
			// normal stop
			} else {
				time += calcTime(stops[i - 1].getDeliveryPoint(), stops[i].getDeliveryPoint(), location.getDrone().getAverageCruisingSpeedFeetPerSecond());
			}
			
			
			// deliver order if not on trip back home
			if (i < stops.length) {
				
				// add delivery time
				time += location.getDrone().getDeliveryTime();
				
				stops[i].setDeliveredTime(time);
			}
		}
		
		// return the total duration of the trip
		return time - startTime;
	}
	
	/**
	 * Calculates the time it takes for the drone to travel from point a to point b
	 * @param origin
	 * @param destination
	 * @return
	 */
	public static double calcTime(DeliveryPoint origin, DeliveryPoint destination, double droneSpeedFpS) {
		double distance = origin.distanceInFeet(destination);
		
		// D = RT, so T = D / R
		return distance / droneSpeedFpS;
	}
	
	/**
	 * Given one shift's trip sequence, aggregate the results for that shift
	 * @param trips
	 * @return results for an individual shift
	 */
	public Results generateResults(List<DroneTrip> trips) {
		Results shiftResults = new Results();
		
		ArrayList<Double> times = new ArrayList<>();
		
		// for each order in each trip, add the time that order took
		for (DroneTrip trip : trips) {
			for (Order order : trip.getStops()) {
				times.add(order.getDuration());
			}
		}
		
		shiftResults.addTimes(times);
		
		return shiftResults;
	}

	/**
	 * Aggregates all shift's results into one results object
	 * @param allShiftsResults results for all shifts in simulation
	 * @return results for a whole simulation
	 */
	public Results generateResults(Results[] allShiftsResults) {
		Results simulationResults = new Results();
		
		// for each shift's results, add the times that it took
		for (Results shiftResults : allShiftsResults) {
			simulationResults.addTimes(shiftResults.getTimes());
		}
		
		return simulationResults;
	}

	/**
	 * Generates a random order, based on SimulationDetails object
	 * @param timestamp of the order
	 * @return a randomly generated order
	 */
	public Order generateOrder(double timestamp) {
		Random rand = new Random();
		String name;
		Meal m = location.getRandomMeal();
		DeliveryPoint dp = location.getRandomPoint();
		
		//pull a random name
		int first = rand.nextInt(firstNames.size());
		int last = rand.nextInt(lastNames.size());
		name = firstNames.get(first) + " " + lastNames.get(last);
		
		return new Order(name, m, timestamp, dp);
	}

}