package javaFX_Forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button; 

public class MealForm extends Form
{

	private List<Meal> meals;
	private List<FoodItem> foods;

	private ListView<HBox> foodView;
	private ListView<HBox> mealView;

	private Drone drone;

	private Button edit;

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
		edit = new Button("Edit"); 
		Button delete = new Button("Delete");

		//button section on gui
		VBox buttons = new VBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(edit,delete);	//adds buttons to vbox
		buttons.setPadding(new Insets(50, 10, 0, 0));	//above,right,below,left
		buttons.setPrefWidth(150);	//prevents buttons from showing up as "..."
		/***************************finished list buttons**************************/

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
			if(percentsValid(mealView)){
				this.sc.replaceMeals(meals);
				this.sc.switchToHome();
			}
		});

		/***************************popup section******************************/

	}



	public void addingEvent(ListView<HBox> foodView, ListView<HBox> mealView) {
		HashMap<FoodItem,Integer> foodList = new HashMap<>();	//stores foods being added
		double mealWeight = 0.0;	//total weight for the meal
		boolean foundFood = false;	//if food was found or not
		//Meal selectedMeal = (Meal)mealView.getSelectionModel().getSelectedItem();

		for(HBox hbox : popupList.getItems()) {	//loop through all the foods

			Text temp = (Text)(hbox.getChildren().get(0));
			String food = temp.getText();	//gets the food

			TextField temp2 = (TextField)(hbox.getChildren().get(1));
			String stringNum = temp2.getText();	//gets the user's text
			temp2.setText("");	//resets the text to be empty

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
					foundFood = false;
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
			boolean mealSuccess = false;
			Meal m = new Meal(foodList, 0);
			String[] tempM = m.toString().split(" ");
			List<String> tempNewList = Arrays.asList(tempM);
			Collections.sort(tempNewList);	//sorts the new list

			//check if potential new meal doesnt already exist
			for(HBox hbox : mealView.getItems()) {	//loop through all the foods
				mealSuccess = false;
				Text temp = (Text)(hbox.getChildren().get(0));
				String[] tempMeal = temp.getText().split(" ");
				List<String> meal = Arrays.asList(tempMeal);
				Collections.sort(meal);	//sorts the new list
				if(meal.size() == tempNewList.size()) {
					for(int i = 0; i<meal.size(); i++) {
						if(!meal.get(i).equals(tempNewList.get(i))) {
							mealSuccess = true;
						}
					}
					if(mealSuccess == false) {
						System.out.println("This meal already exists");
						break;
					}
				}
				else {
					mealSuccess = true;
				}
			}
			//create meal
			if(mealSuccess){
				//add new meal to display
				TextField inputVal = new TextField("0");	//creates the textField
				HBox hbox = new HBox();
				//gets the food item as text
				Text temp = new Text(m.toString());
				hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
				mealView.getItems().add(hbox);	//adds the hbox to the arraylist
				hbox.setPrefWidth(20);
				meals.add(m);
				System.out.println("meal added!");
			}
		}
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
		int index = 0;
		for(HBox selectedBox : mealView.getItems()) {
			TextField temp = (TextField)(selectedBox.getChildren().get(1));
			String stringP = temp.getText();

			try{
				meals.get(index).setPercentage(Double.parseDouble(stringP));
			}
			//not a valid double for percentage
			catch(Exception e) {
				System.out.println("The value " + stringP +" is not a valid percentage.");
				e.getMessage();
			}
			index++;
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
		GridPane popupPane = new GridPane();
		/****************************set up popup buttons area***************************/
		ListView<HBox> popupList = new ListView<HBox>();
		Label editMeal = new Label("Edit Meal");
		//creates arraylist to store all hboxes going into the listview
		ArrayList<HBox> foodElem = new ArrayList<>();
		for(int i=0; i<foods.size(); i++) {
			TextField inputVal = new TextField();	//creates the textField

			HBox hbox = new HBox();
			//gets the food item as text
			Text temp = new Text(foods.get(i).toString());
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			foodElem.add(hbox);	//adds the hbox to the arraylist
			hbox.setPrefWidth(20);
		}


		//makes the arraylist of hboxes an observable list
		ObservableList<HBox>ListOfFoods  = FXCollections.<HBox>observableArrayList(foodElem);

		// fill the food ListView

		popupList.getItems().clear();
		popupList.getItems().addAll(ListOfFoods);

		/****************************finished food list***************************/	//above,right,below,left

		Button editSave = new Button("Save");

		VBox popupFields = new VBox();
		popupFields.setSpacing(10);
		popupFields.getChildren().addAll(editMeal, popupList, editSave);
		popupFields.setPadding(new Insets(25, 10, 0, 0));	//above,right,below,left
		/***************************end popup buttons area**************************/
		popupPane.addColumn(0, popupFields);
		Scene scene2 = new Scene(popupPane,200,200);
		Stage popup = new Stage();
		popup.setScene(scene2);
		popup.initModality(Modality.APPLICATION_MODAL);
		// Set the Style-properties of the GridPane
		popupPane.setPadding(new Insets(0,25,25,25));

		//calls function to check if save edited stuff (if valid)
		editSave.setOnAction(event->{
			//if successfully edited
			//if(editSave(input1,input2,input3,foodView)) {
			popup.close();
			//}

		});

		edit.setOnAction(event-> {

			HBox selectedBox = (HBox) mealView.getSelectionModel().getSelectedItem();
			Meal selectedMeal = null;
			try {
				Text temp = (Text)(selectedBox.getChildren().get(0));
				String meal = temp.getText();

				for(Meal m : meals) {
					if(m.toString().equals(meal)) {
						selectedMeal = m;
						for(HBox fBox : foodElem) {
							Text foodText = ((Text)fBox.getChildren().get(0));
							TextField foodField = ((TextField)fBox.getChildren().get(1));
							if(meal.contains(foodText.getText())) {
								for(HashMap.Entry<FoodItem, Integer> food : m.getMeal().entrySet()) {
									FoodItem key = food.getKey();
									int value = food.getValue();
									if(key.toString().equals(foodText.getText())) {
										foodField.setText("" + value);
									}
								}
							}
						}
						break;
					}
				}
			}
			catch(NullPointerException e) {
				System.out.println("no meal selected");
			}
			if (selectedMeal != null) {
				System.out.println("edit: " + selectedMeal);
				popup.showAndWait();
			}

		});
	}
	public void editEvent(ListView<HBox> popupList, ListView<HBox> mealView) {

	}

}