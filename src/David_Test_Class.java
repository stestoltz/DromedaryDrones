import java.util.HashMap;

public class David_Test_Class {

	public static void main(String[] args) {
		HashMap<FoodItem, Integer> mealList = new HashMap<>();
		FoodItem burger = new FoodItem("Burger", 6.0, 8.0);
		FoodItem fries = new FoodItem("Fries", 2.0, 4.0);
		FoodItem drink = new FoodItem("Drink", 8.0, 0.0);
		FoodItem queso = new FoodItem("Queso", 3.0, 2.0);
		
		mealList.put(burger, 2);
		mealList.put(fries, 3);
		mealList.put(drink, 3);
		double percentage = 22;
		
		Meal meal = new Meal(mealList, percentage);
		
		System.out.println("Food stuff: ");
		System.out.println(burger.getName());
		System.out.println(burger.getPrepTime());
		System.out.println(burger.getWeight());

		burger.setName("Quarter pounder");
		drink.setWeight(100);
		fries.setPrepTime(42);

		meal.addFood(queso, 1);

		System.out.println("Meal stuff: ");
		System.out.println(meal.toString());
		System.out.println(meal.getMealPrepTime());
		System.out.println(meal.getMealWeight());
		System.out.println(meal.getPercentage());
	}

}
