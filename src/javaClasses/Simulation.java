package javaClasses;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Simulation {

	private SimulationDetails details;

	/**
	 * Constructor for simulation
	 * Copy the details passed in to member details variable
	 * @param details the options for this simulation
	 */
	public Simulation(SimulationDetails details) {
		this.details = details;
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
		double deliveredTime = 0;
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
						SimulationDetails.getLocation().getDeliveryPoints()) {
						if(d.getX() == xPoint && d.getY() == yPoint) {
							deliveryPoint = d;
						}
					}
					//name is already good to go

					//get meal object
					for(Meal locElement: 
						SimulationDetails.getMeals()) {
						if(locElement.stringEquals(meal)) {
							meal4Order = locElement;
						}
					}
					//create order
					Order order = new Order(name, meal4Order, orderedTime, deliveredTime,
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
	 * @param the starting time of the trip
	 * @return the time the trip takes
	 */
	public double processTrip(DroneTrip trip, double startTime) {
		
		double time = startTime;
		
		// get from location class?
		DeliveryPoint home = details.getLocation().getHome();
		
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
				time += details.getDrone().getDefaultDeliveryTime();
			}
		}
		
		return time;
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
		return distance / details.getDrone().getAverageCruisingSpeedFeetPerSecond();
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
	public Results generateResults(DroneTrip[] trips) {
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
		return null;
	}

}
