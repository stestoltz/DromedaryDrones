package javaClasses;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

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
	public Results[] runSimulation(Class<? extends PackingAlgorithm>[] packingAlgorithms, RoutingAlgorithm routingAlgorithm) {
		
		// create simulationResults[], one entry for each packing algorithm
		Results[] simulationResults = new Results[packingAlgorithms.length];
		
		// for each packing algorithm
		for (int p = 0; p < packingAlgorithms.length; p++) {
			
			//Class<? extends PackingAlgorithm> packingType = packingAlgorithms[p];
		
			// create results[] for this shift's results
			Results[] shiftResults = new Results[location.getShiftDetails().getNumberOfShifts()];
			
			// for each shift
			for (int i = 0; i < shiftResults.length; i++) {
		
				// generate all orders for this shift
				Queue<Order> orders = generateOrders();
				
				// MUST BE A SHALLOW COPY so that the delivered times are changed
				Queue<Order> packingAlgorithmsOrders = new LinkedList<Order>(orders);
				
				// instantiate a packing algorithm of the type in the array passed from the UI
				//PackingAlgorithm pa = (PackingAlgorithm) packingType.getConstructors()[0].newInstance(
				//		packingAlgorithmsOrders, location.getDrone());
				
				PackingAlgorithm pa;
				if (i == 0) {
					pa = new FIFOPacking(packingAlgorithmsOrders, location.getDrone());
				} else {
					pa = new KnapsackPacking(packingAlgorithmsOrders, location.getDrone());
				}
	
				/*
				 * send orders queue to xml file
				 * 
				 * read orders queue from xml file
				 * 
				 */
		
				// process the orders into drone trips
				
				
				//DroneTrip[] trips = processOrders(orders, routingAlgorithm);
		
				// start at time of first order
				double time = pa.nextOrderTime();
				
				List<DroneTrip> trips = new ArrayList<>();
				
				// DOES NOT FIX NO ORDERS AT CURRENT TIME
				
				// for each drone trip
				while (pa.hasNextOrder()) {
					
					// wait until we have an order
					if (time < pa.nextOrderTime()) {
						time = pa.nextOrderTime();
					}
					
					// ASSUMPTION: IF WE HAVE AN ORDER, SHIP IT
					
					// find a drone's worth of orders available now
					List<Order> nextTripOrders = pa.nextOrder(time);
					
					// route the drone for this trip
					DroneTrip trip = routingAlgorithm.createTrip(nextTripOrders, location.getHome());
					
					// add time of drone trip
					//  this updates the delivered times of all of the orders in the trip
					time += processTrip(trip, time);
					trips.add(trip);
					
					// add turn around time
					time += location.getDrone().getTurnAroundTimeSeconds();
				}
				
				// generate results for this shift and them to shift results[]
				shiftResults[i] = generateResults(trips);
	
			}
				
			// all shifts are done - this packing algorithm's simulation is done
		
			// aggregate all shift's results into one results object and add it to simulationResults
			simulationResults[p] = generateResults(shiftResults);
		
		}
			
		return simulationResults;
	}

	/**
	 * Generates a queue of orders for one shift
	 * @param packingAlgorithm the packing algorithm to use
	 * @return the queue
	 */
	//NEEDS TO BE FIXED: to put orders in the hour randomly, not evenly spaced out
	public Queue<Order> generateOrders() {
		Queue<Order> q = new LinkedList<Order>();
		double timeStamp, timeIncrease;
		//generate orders for each hour of the shift
		for (int i=0; i<location.getShiftDetails().getHoursInShift(); i++) {
			int numOrders = location.getShiftDetails().getOrdersPerHour()[i];
			timeStamp = i*3600;
			timeIncrease = 3600/numOrders;
			
			//generate the number of orders for that order
			for (int j=0; j<numOrders; j++) {
				//calculate the timestamp for the order
				timeStamp += timeIncrease;
			
				//add the order to the queue
				q.add(generateOrder(timeStamp));
			}
		}
		return q;
	}

	/**
	 * Given queue of orders, write to XML file
	 * @return file path to XML file
	 */
	public String generateXML(Queue<Order> orders) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    //DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    //Document doc = docBuilder.newDocument();
	    //Element rootElement = doc.createElement("Orders");
	    
	    
		
		//
		return "";
	}

	/**
	 * Given XML file, read to queue of orders
	 * @param filePath path to XML file
	 * @return queue of orders
	 */
	public Queue<Order> readXML(String filePath) {
		//variable dictionary:
		Queue<Order> orders = new LinkedList<>();//create the queue of orders to be returned
		//variables for storing all data going into the orders queue
		String name = "";
		Meal meal4Order = null;
		String meal = "";
		double orderedTime = 0;
		int xPoint = 0;
		int yPoint = 0;
		DeliveryPoint deliveryPoint = null;		
		
		//setup for reading xml file
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = null;
		
		try {	//try to open the file if its there
			streamReader = factory.createXMLStreamReader(new FileReader(filePath));
		} 
		//catch errors if they occur and inform user
		catch (FileNotFoundException e) {
			System.out.println("XML File could not be found");
			e.printStackTrace();
		} 
		catch (XMLStreamException e) {
			System.out.println("An XML stream error occured");
			e.printStackTrace();
		}

		//try to parse through the xml for each of its values
		try {
			while (streamReader.hasNext()) {	//iterate through each xml element
				int event = streamReader.next();
				if(event == XMLStreamConstants.START_ELEMENT) {
					if(streamReader.getLocalName().equals("Order")) {	//make sure its correct element
						//get all the values in the element
						
						//x and y coordinates:
						streamReader.nextTag();
						xPoint = Integer.parseInt(streamReader.getElementText());
						streamReader.nextTag();
						yPoint = Integer.parseInt(streamReader.getElementText());

						//name:
						streamReader.nextTag();
						name = streamReader.getElementText();

						//meal:
						streamReader.nextTag();
						meal =streamReader.getElementText();

						//timestamp: 
						streamReader.nextTag();
						orderedTime = Double.parseDouble(streamReader.getElementText());
					}
					
			/*____here down cannot be tested since SimulationDetails isnt complete____*/
					//get deliveryPoint object for order
					for(DeliveryPoint d: 
						location.getDeliveryPoints()) {
						if(d.getX() == xPoint && d.getY() == yPoint) {
							deliveryPoint = d;
						}
					}
					//name is already good to go

					//get meal object
					for(Meal locElement: 
						location.getMeals()) {
						if(locElement.stringEquals(meal)) {
							meal4Order = locElement;
						}
					}
					//create order
					Order order = new Order(name, meal4Order, orderedTime,
							deliveryPoint);

					//add order to queue
					orders.add(order);
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Error converting XML data to appropriate variable types");
			e.printStackTrace();
		} catch (XMLStreamException e) {
			System.out.println("An XML stream error occured");
			e.printStackTrace();
		}
		return orders;
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
				time += calcTime(home, stops[i].getDeliveryPoint());
				
			// last stop back to home
			} else if (i == stops.length) {
				time += calcTime(stops[i - 1].getDeliveryPoint(), home);
			
			// normal stop
			} else {
				time += calcTime(stops[i - 1].getDeliveryPoint(), stops[i].getDeliveryPoint());
			}
			
			
			// deliver order if not on trip back home
			if (i < stops.length) {
				
				stops[i].setDeliveredTime(time);
				
				// add delivery time
				time += location.getDrone().getDeliveryTime();
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
	private double calcTime(DeliveryPoint origin, DeliveryPoint destination) {
		double distance = distance(origin, destination);
		
		// D = RT, so T = D / R
		return distance / location.getDrone().getAverageCruisingSpeedFeetPerSecond();
	}
	
	private double distance(DeliveryPoint a, DeliveryPoint b) {
		return Math.sqrt(((a.getX() - b.getX()) * (a.getX() - b.getX())) + 
				((a.getY() - b.getY()) * (a.getY() - b.getY())));
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
