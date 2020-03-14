import java.util.Queue;

public abstract class PackingAlgorithm {
	
	Queue<Meal> shiftMeals;
	Drone drone;
	
	/**
	 * next order builds the next order
	 * @return the next order
	 */
	public abstract Order nextOrder();
}
