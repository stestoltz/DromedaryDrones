package javaFX_Forms;
import java.util.ArrayList;
import java.util.HashMap;

import javaClasses.FoodItem;
import javaClasses.Location;
import javaClasses.Meal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button; 

public class MealForm extends Application
{
	// Declaring the TextArea for Logging
	TextArea logging;
	ArrayList<String> foods = new ArrayList<>();
	Location location = new Location("test", "home");

	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		/****************************set up meal list***************************/
		//create food label
		Label mealLabel = new Label("Meals:");

		FoodItem burger = new FoodItem("Burger", 2, 2);
		location.addFood(burger);
		FoodItem fries = new FoodItem("Fries", .5, 1);
		location.addFood(fries);
		FoodItem drink = new FoodItem("Drink", 2, 0);
		location.addFood(drink);
		FoodItem pie = new FoodItem("Pie", 2.5, 8);
		location.addFood(pie);

		HashMap<FoodItem, Integer> meal1List = new HashMap<>();
		meal1List.put(burger, 1);
		meal1List.put(fries, 1);
		meal1List.put(drink, 1);
		Meal meal1 = new Meal(meal1List, .15);

		HashMap<FoodItem, Integer> meal2List = new HashMap<>();
		meal2List.put(burger, 2);
		meal2List.put(fries, 2);
		meal2List.put(drink, 2);
		Meal meal2 = new Meal(meal2List, .35);

		HashMap<FoodItem, Integer> meal3List = new HashMap<>();
		meal3List.put(burger, 2);
		meal3List.put(fries, 3);
		meal3List.put(drink, 4);
		meal3List.put(pie, 1);
		Meal meal3 = new Meal(meal3List, .25);

		HashMap<FoodItem, Integer> meal4List = new HashMap<>();
		meal4List.put(fries, 18);
		Meal meal4 = new Meal(meal4List, .25);

		location.addMeal(meal1);
		location.addMeal(meal2);
		location.addMeal(meal3);
		location.addMeal(meal4);

		
		ArrayList<HBox> mealElements = new ArrayList<>();
		for(int i=0; i<location.getMeals().size(); i++) {
			TextField inputVal = new TextField();	//creates the textField
			
			HBox hbox = new HBox();
			//gets the food item as text
			Text temp = new Text(location.getMeals().get(i).toString());
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			mealElements.add(hbox);	//adds the hbox to the arraylist
			hbox.setPrefWidth(20);
		}

		ObservableList<HBox> mealList = FXCollections.<HBox>observableArrayList(mealElements);

		// Create the ListView for the seasons
		ListView<HBox> mealView = new ListView<>(mealList);
		// Set the Orientation of the ListView
		mealView.setOrientation(Orientation.VERTICAL);
		// Set the Size of the ListView
		mealView.setPrefSize(120, 100);

		// Create the Season VBox
		VBox mealSelection = new VBox();
		// Set Spacing to 10 pixels
		mealSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		mealSelection.getChildren().addAll(mealLabel,mealView);
		mealSelection.setPrefWidth(300);	//prevents a smooshed display
		/****************************finished food list***************************/

		/****************************set up edit list buttons***************************/
		// create a button 
		Button edit = new Button("Edit"); 
		Button delete = new Button("Delete");

		edit.setOnAction(event->
		System.out.println("edit: " + mealView.getSelectionModel().getSelectedItem()));
		delete.setOnAction(event->
		System.out.println("delete: " + mealView.getSelectionModel().getSelectedItem()));
		//button section on gui
		VBox buttons = new VBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(edit,delete);	//adds buttons to vbox
		buttons.setPadding(new Insets(50, 10, 0, 0));	//above,right,below,left
		buttons.setPrefWidth(150);	//prevents buttons from showing up as "..."
		/***************************finished list buttons**************************/

		/****************************set up new order list***************************/
		//create food label
		Label newMealLabel = new Label("New Meal");		

		//creates arraylist to store all hboxes going into the listview
		ArrayList<HBox> foodElements = new ArrayList<>();
		for(int i=0; i<location.getFoods().size(); i++) {
			TextField inputVal = new TextField();	//creates the textField
			
			HBox hbox = new HBox();
			//gets the food item as text
			Text temp = new Text(location.getFoods().get(i).toString());
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			foodElements.add(hbox);	//adds the hbox to the arraylist
			hbox.setPrefWidth(20);
		}

		//makes the arraylist of hboxes an observable list
		ObservableList<HBox> foods = FXCollections.<HBox>observableArrayList(foodElements);
		//makes the observable list a listview to be displayed
		ListView<HBox> foodView = new ListView<>(foods);
		//makes the list be displayed vertically
		foodView.setOrientation(Orientation.VERTICAL);
		// Set the Size of the ListView
		foodView.setPrefSize(120, 100);

		// Create the Season VBox
		VBox foodSelection = new VBox();
		// Set Spacing to 10 pixels
		foodSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		foodSelection.getChildren().addAll(newMealLabel,foodView);
		foodSelection.setPrefWidth(200);	//prevents a smooshed display
		/***************************finished new order list**************************/

		/****************************set up add area***************************/


		Button addMeal = new Button("Add New Meal");

		//calls function to check if adding stuff is valid
		//need to check for duplicates
		//need to refresh the list
		addMeal.setOnAction(event->
		addingEvent(foodView,mealView));

		delete.setOnAction((event) -> {
			HBox selectedBox = (HBox) mealView.getSelectionModel().getSelectedItem();
			
			Text temp = (Text)(selectedBox.getChildren().get(0));
			String meal = temp.getText();

			Meal selectedMeal = null;
			for(Meal m : location.getMeals()) {
				if(m.toString().equals(meal)) {
					selectedMeal = m;
				}
			}
			//if (selectedLocation.getDeliveryPoints().contains(selectedPoint)) {
			if (selectedMeal != null) {
				location.deleteMeal(selectedMeal);
				mealView.getItems().remove(selectedBox);
			}
		});
		/***************************finished add area**************************/

		// Create the GridPane
		GridPane pane = new GridPane();
		// Set the horizontal and vertical gaps between children
		pane.setHgap(10);
		pane.setVgap(5);
		// Add the two gui components
		pane.addColumn(0, mealSelection);
		pane.addColumn(1, buttons);
		pane.addColumn(2, foodSelection,addMeal);


		//        // Add the TextArea at position 2
		//        pane.addColumn(2, logging);

		// Set the Style-properties of the GridPane
		pane.setPadding(new Insets(25,25,25,25));

		// Create the Scene
		Scene scene = new Scene(pane, 600, 250);
		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title
		stage.setTitle("Food Settings");
		// Display the Stage
		stage.show();
	}
	public void addingEvent(ListView<HBox> foodView, ListView<HBox> mealView) {
		HashMap<FoodItem,Integer> foodList = new HashMap<>();
		boolean foundFood = false;
		for(HBox hbox : foodView.getItems()) {
			Text temp = (Text)(hbox.getChildren().get(0));
			String food = temp.getText();

			TextField temp2 = (TextField)(hbox.getChildren().get(1));
			String stringNum = temp2.getText();
			if (!stringNum.equals("") && !stringNum.equals("0")) {
				foundFood = true;
				//convert to integer
				try{
					int foodCount = Integer.parseInt(stringNum);
					//loop through foods and add food matching the food string
					for(FoodItem f : location.getFoods()) {
						if (f.toString().equals(food)) {
							foodList.put(f, foodCount);
							break;
						}
					}
				}
				//not a valid integer for preptime
				catch(Exception e) {
					System.out.println("The value " + stringNum +" cannot be used as a integer.");
					e.getMessage();
				}
			}
		}
		if(!foundFood) {
			System.out.println("Could not find any foods to make a meal");
		}
		else {
			//create meal
			Meal meal = new Meal(foodList,0);
			location.addMeal(meal);	//add new meal to location's list
			
			//add new meal to display
			TextField inputVal = new TextField();	//creates the textField
			HBox hbox = new HBox();
			//gets the food item as text
			Text temp = new Text(meal.toString());
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			mealView.getItems().add(hbox);	//adds the hbox to the arraylist
			hbox.setPrefWidth(20);
		}
		//should alert user and/or clear out textboxes
	}

}