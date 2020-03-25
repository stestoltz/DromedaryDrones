package javaClasses;
import java.util.Queue;

public abstract class PackingAlgorithm {
	
	private Queue<Meal> shiftMeals;
	private Drone drone;
	
	/**
	 * next order builds the next order
	 * @return the next order
	 */
	public abstract Order nextOrder();
}
