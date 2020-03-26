import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Simulation {
	
	private SimulationDetails details;
	
	private ArrayList<String> firstNames = new ArrayList<String>();
	private ArrayList<String> lastNames = new ArrayList<String>();
	
	/**
	 * Constructor for simulation
	 * Copy the details passed in to member details variable
	 * @param details the options for this simulation
	 */
	public Simulation(SimulationDetails details) {
		this.details = details;
		
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
	public Results[] runSimulation(PackingAlgorithm[] packingAlgorithms, RoutingAlgorithm routingAlgorithm) {
		
		// create simulationResults[], one entry for each packing algorithm
		
		// for each packing algorithm
		
			// create results[] for this shift's results
			
			// for each shift
		
				// create orders queue
		
				// for each hour
					
					// add orders to queue
	
				/*
				 * send orders queue to xml file
				 * 
				 * read orders queue from xml file
				 * 
				 */
		
				// process the orders into drone trips
		
				// for each drone trip
					
					// process the trip
				
				// generate results for this shift
		
				// add results to shift results[]
	
			// all shifts are done - this packing algorithm's simulation is done
		
			// aggregate all shift's results into one results object
		
			// add to simulationResults
		
		// return simulationResults
				
		
		return null;
	}
	
	/**
	 * Generates a queue of orders for one shift
	 * @param packingAlgorithm the packing algorithm to use
	 * @return the queue
	 */
	public Queue<Order> generateOrders(PackingAlgorithm packingAlgorithm) {
		return null;
	}
	
	/**
	 * Given queue of orders, write to XML file
	 * @return file path to XML file
	 */
	public String generateXML(Queue<Order> orders) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    Document doc = docBuilder.newDocument();
	    Element rootElement = doc.createElement("Orders");
	    
	    
		
		//
		return "";
	}
	
	/**
	 * Given XML file, read to queue of orders
	 * @param filePath path to XML file
	 * @return queue of orders
	 */
	public Queue<Order> readXML(String filePath) {
		return null;
	}
	
	/**
	 * Given queue of orders, build drone trips from the orders
	 * Use processTrip to figure out how long each trip took
	 * @param orders
	 * @param routingAlgorithm routing algorithm used to generate drone trips
	 * @return the sequence of trips the drone will take
	 */
	public DroneTrip[] processOrders(Queue<Order> orders, RoutingAlgorithm routingAlgorithm) {
		return null;
	}
	
	/**
	 * Figure out how long the trip takes
	 * Updates the delivered time of each order object in the drone trip
	 * @param trip the trip to process
	 * @return the time the trip takes
	 */
	public double processTrip(DroneTrip trip) {
		return 0.0;
	}
	
	/**
	 * Given one shift's trip sequence, aggregate the results for that shift
	 * @param trips
	 * @return results for an individual shift
	 */
	public Results generateResults(DroneTrip[] trips) {
		Results shiftResults = new Results();
		
		List<Double> times = new ArrayList<>();
		
		// for each order in each trip, add the time that order took
		for (DroneTrip trip : trips) {
			for (Order order : trip.getStops()) {
				times.add(order.getDeliveredTime() - order.getOrderedTime());
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
	 * delivery time is originally set as 0
	 * @param timestamp of the order
	 * @return a randomly generated order
	 */
	public Order generateOrder(double timestamp) {
		Random rand = new Random();
		String name;
		Meal m;
		DeliveryPoint dp;
		
		//pull a random name
		int first = rand.nextInt(firstNames.size());
		int last = rand.nextInt(lastNames.size());
		name = firstNames.get(first) + " " + lastNames.get(last);
		
		//get a random meal
		int meal = rand.nextInt(details.getMeals().size());
		m = details.getMeals().get(meal);
		
		//get a random delivery point
		int num = rand.nextInt(details.getLocation().getDeliveryPoints().size());
		dp = details.getLocation().getDeliveryPoints().get(num);
		
		Order o = new Order(name, m, timestamp, 0.0, dp);
		
		return o;
		
	}

}
