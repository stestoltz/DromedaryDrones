package javaFX_Forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javaClasses.Drone;
import javaClasses.FoodItem;
import javaClasses.Meal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button; 

public class MealForm extends Form
{
	
	private List<Meal> meals;
	private List<FoodItem> foods;

	private ListView<HBox> foodView;
	private ListView<HBox> mealView;
	
	private Drone drone;

	public MealForm(SceneController sc, BorderPane layout) {
		super(sc, layout);

		foodView = new ListView<>();
		mealView = new ListView<>();
		
		
		//makes the list be displayed vertically
		foodView.setOrientation(Orientation.VERTICAL);
		// Set the Size of the ListView
		foodView.setPrefSize(120, 100);
		
		// Set the Orientation of the ListView
		mealView.setOrientation(Orientation.VERTICAL);
		// Set the Size of the ListView
		mealView.setPrefSize(120, 100);

		
		
		
		
		//create food label
		Label newMealLabel = new Label("New Meal");		
		
		// Create the food VBox
		VBox foodSelection = new VBox();
		// Set Spacing to 10 pixels
		foodSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		foodSelection.getChildren().addAll(newMealLabel,foodView);
		foodSelection.setPrefWidth(200);	//prevents a smooshed display
		
		//create meal label
		Label mealLabel = new Label("Meals:");
		
		// Create the meal VBox
		VBox mealSelection = new VBox();
		// Set Spacing to 10 pixels
		mealSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		mealSelection.getChildren().addAll(mealLabel,mealView);
		mealSelection.setPrefWidth(300);	//prevents a smooshed display


		/****************************set up edit list buttons***************************/
		// create a button 
		Button edit = new Button("Edit"); 
		Button delete = new Button("Delete");
		
		//button section on gui
		VBox buttons = new VBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(edit,delete);	//adds buttons to vbox
		buttons.setPadding(new Insets(50, 10, 0, 0));	//above,right,below,left
		buttons.setPrefWidth(150);	//prevents buttons from showing up as "..."
		/***************************finished list buttons**************************/

		/****************************set up new order list***************************/


		/***************************finished new order list**************************/

		/****************************set up add area***************************/


		Button addMeal = new Button("Add New Meal");

		//calls function to check if adding stuff is valid
		//need to check for duplicates
		//need to refresh the list
		addMeal.setOnAction(event-> {
			addingEvent(foodView,mealView);
		});

		delete.setOnAction((event) -> {
			HBox selectedBox = (HBox) mealView.getSelectionModel().getSelectedItem();
			Meal selectedMeal = null;
			try {
				Text temp = (Text)(selectedBox.getChildren().get(0));
				String meal = temp.getText();

				for(Meal m : meals) {
					if(m.toString().equals(meal)) {
						selectedMeal = m;
					}
				}
			}
			catch(NullPointerException e) {
				System.out.println("no meal selected");
			}
			//if (selectedLocation.getDeliveryPoints().contains(selectedPoint)) {
			if (selectedMeal != null) {
				meals.remove(selectedMeal);
				mealView.getItems().remove(selectedBox);
			}
		});

		edit.setOnAction((event) -> {
			System.out.println("Temporarily threw this in to check "
					+ "percentage total: (" +percentsValid(mealView)+")");
			System.out.println("edit food");
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

		layout.setCenter(pane);

		
		// get buttons and set event handlers
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());
		
		cancel.setOnAction((event) -> {
			this.sc.switchToHome();
		});
		
		save.setOnAction((event) -> {
			
			// if form is valid
				
			this.sc.replaceMeals(meals);
			this.sc.switchToHome();
		});
	}
	
	public void addingEvent(ListView<HBox> foodView, ListView<HBox> mealView) {
		HashMap<FoodItem,Integer> foodList = new HashMap<>();
		double mealWeight = 0.0;
		boolean foundFood = false;
		for(HBox hbox : foodView.getItems()) {
			Text temp = (Text)(hbox.getChildren().get(0));
			String food = temp.getText();

			TextField temp2 = (TextField)(hbox.getChildren().get(1));
			String stringNum = temp2.getText();
			temp2.setText("");
			if (!stringNum.equals("") && !stringNum.equals("0")) {
				foundFood = true;
				try{
					int foodCount = Integer.parseInt(stringNum);	//convert to integer
					//loop through foods and add food matching the food string
					for(FoodItem f : foods) {
						if (f.toString().equals(food)) {
							foodList.put(f, foodCount);
							mealWeight += foodCount * f.getWeight();
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
		else if (mealWeight > drone.getCargoWeight()) {
			System.out.println("The meal being created is weighs too much");
		}
		else {
			//create meal
			Meal meal = new Meal(foodList,0);
			boolean mealAdded = meals.add(meal);	//add new meal to location's list
			if(mealAdded){
				//add new meal to display
				TextField inputVal = new TextField("0");	//creates the textField
				HBox hbox = new HBox();
				//gets the food item as text
				Text temp = new Text(meal.toString());
				hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
				mealView.getItems().add(hbox);	//adds the hbox to the arraylist
				hbox.setPrefWidth(20);
				System.out.println("meal added!");
			}
		}
		//should alert user and/or clear out textboxes
	}

	/**
	 * Method for checking if the percents add up to 100
	 * (run upon exiting the program)
	 * @return
	 */
	public boolean percentsValid(ListView<HBox> mealView) {
		double pTotal = 0.0;
		for(HBox selectedBox : mealView.getItems()) {
			TextField temp = (TextField)(selectedBox.getChildren().get(1));
			String stringP = temp.getText();

			try{
				pTotal += Double.parseDouble(stringP);
			}
			//not a valid double for percentage
			catch(Exception e) {
				System.out.println("The value " + stringP +" is not a valid number.");
				e.getMessage();
			}

		}
		if (pTotal != 100) {
			System.out.println("The percentages do not add up to 100");
			return false;
		}
		//add correct (possibly new) percentages to each meal
		for(HBox selectedBox : mealView.getItems()) {
			TextField temp = (TextField)(selectedBox.getChildren().get(1));
			String stringP = temp.getText();

			try{
				double percent = Double.parseDouble(stringP);
			}
			//not a valid double for percentage
			catch(Exception e) {
				System.out.println("The value " + stringP +" is not a valid percentage.");
				e.getMessage();
			}
		}
		return true;
	}

	public void loadMeals(List<Meal> meals, List<FoodItem> foods, Drone d) {
		
		this.meals = meals;
		this.foods = foods;
		this.drone = d;

		/**********************set up food list**********************************/

		//creates arraylist to store all hboxes going into the listview
		ArrayList<HBox> foodElements = new ArrayList<>();
		for(int i=0; i<foods.size(); i++) {
			TextField inputVal = new TextField();	//creates the textField

			HBox hbox = new HBox();
			//gets the food item as text
			Text temp = new Text(foods.get(i).toString());
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			foodElements.add(hbox);	//adds the hbox to the arraylist
			hbox.setPrefWidth(20);
		}

		//makes the arraylist of hboxes an observable list
		ObservableList<HBox> foodsList = FXCollections.<HBox>observableArrayList(foodElements);
		//makes the observable list a listview to be displayed
		
		// fill the food ListView
		
		foodView.getItems().clear();
		foodView.getItems().addAll(foodsList);
		
		/****************************finished food list***************************/

		/****************************set up meal list***************************/

		ArrayList<HBox> mealElements = new ArrayList<>();
		for(int i=0; i<meals.size(); i++) {
			//creates the textField
			TextField inputVal = new TextField(""+meals.get(i).getPercentage());	

			HBox hbox = new HBox();
			//gets the food item as text
			Text temp = new Text(meals.get(i).toString());
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			mealElements.add(hbox);	//adds the hbox to the arraylist
			hbox.setPrefWidth(20);
		}

		ObservableList<HBox> mealList = FXCollections.<HBox>observableArrayList(mealElements);

		// fill the meal ListView
		
		mealView.getItems().clear();
		mealView.getItems().addAll(mealList);

		
		/****************************finished meal list***************************/
	}

}