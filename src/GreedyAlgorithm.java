import java.util.Arrays;
import java.util.List;

public class GreedyAlgorithm implements RoutingAlgorithm{

	@Override
	public DroneTrip createTrip(List<Order> orders) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Sorts the array and then calculates the shortest distance between the different points
	 * @param ary the array of x and y coordinates
	 * @param n the number of times the loop is run calculating the distance between the points
	 * @return the sum of the distance of one axis
	 */
	public int greedyAlgorithm(int ary[], int n) {
		Arrays.sort(ary);
		
		int result = 0, sum = 0;
		for(int i = 0; i < n; i++) {
			result += (ary[i] * i - sum);
			sum += ary[i];
		}
		return result;
	}

}
