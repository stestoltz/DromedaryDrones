package javaFX_Forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javaClasses.Drone;
import javaClasses.FoodItem;
import javaClasses.Meal;
import javaFX_Styling.StyleButton;
import javaFX_Styling.StyleLabel;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

	/**
	 * Constructor that creates the Meal form from the data used in the simulation
	 * @param sc the SceneController that calls different scenes to be used
	 * @param layout the BorderPane that is set up to hold the meal form
	 */
	public MealForm(SceneController sc, BorderPane layout) {
		super(sc, layout);

		foodView = new ListView<>();
		mealView = new ListView<>();


		//makes the lists be displayed vertically
		foodView.setOrientation(Orientation.VERTICAL);

		mealView.setOrientation(Orientation.VERTICAL);


		//create food label
		Label newMealLabel = new StyleLabel("New Meal");
		newMealLabel.setStyle("-fx-font-size: 15pt;");
		
		Button addMeal = new StyleButton("Add New Meal");
		addMeal.setPrefWidth(140);

		//calls function to check if adding stuff is valid
		//need to check for duplicates
		//need to refresh the list
		addMeal.setOnAction(event-> {
			addingEvent();
		});

		// Create the food VBox
		VBox foodSelection = new VBox();
		// Set Spacing to 10 pixels
		foodSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		foodSelection.getChildren().addAll(newMealLabel,foodView, addMeal);

		//create meal label
		Label mealLabel = new StyleLabel("Meals:");
		mealLabel.setStyle("-fx-font-size: 15pt;");

		// Create the meal VBox
		VBox mealSelection = new VBox();
		// Set Spacing to 10 pixels
		mealSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		mealSelection.getChildren().addAll(mealLabel,mealView);


		/****************************set up edit list buttons***************************/
		// create a button 
		edit = new StyleButton("Edit"); 
		Button delete = new StyleButton("Delete");

		//button section on gui
		VBox buttons = new VBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(edit,delete);	//adds buttons to vbox
		buttons.setPadding(new Insets (50, 10, 0, 0));	//above,right,below,left
		buttons.setPrefWidth(150);	//prevents buttons from being too close to food list
		/***************************finished list buttons**************************/


		delete.setOnAction((event) -> {
			HBox selectedBox = (HBox) mealView.getSelectionModel().getSelectedItem();
			Meal selectedMeal = null;
			try {
				Label temp = (Label)(selectedBox.getChildren().get(0));
				String meal = temp.getText();

				for(Meal m : meals) {
					if(m.toString().equals(meal)) {
						selectedMeal = m;
					}
				}
			}
			catch(NullPointerException e) {
				this.sc.runErrorPopUp("No meal selected.");
			}
			//if (selectedLocation.getDeliveryPoints().contains(selectedPoint)) {
			if (selectedMeal != null) {
				meals.remove(selectedMeal);
				mealView.getItems().remove(selectedBox);
			}
		});

		// Create the GridPane
		GridPane pane = new GridPane();
		// Set the horizontal and vertical gaps between children
		pane.setHgap(10);
		pane.setVgap(5);
		// Add the two gui components
		pane.addColumn(0, mealSelection);
		pane.addColumn(1, buttons);
		pane.addColumn(2, foodSelection);

		// Set the Style-properties of the GridPane
		pane.setPadding(new Insets(25,25,25,25));
		
		BorderPane completePane = new BorderPane();
		Label description = new StyleLabel("All meal options are shown to the left with food quantities in the parenthesis. "
				+ "The likelihood of a meal being ordered can be edited\nin the textbox to the right of the meal. "
				+ "All percentages must add up to 100 in order to save changes. "
				+ "When creating a new meal,\nenter the quantities desired for each food. "
				+ "Once you create the meal, it will be displayed in the list of meals on the left where its\n"
				+ "percentage can be edited.");
		completePane.setTop(description);
		completePane.setCenter(pane);

		layout.setCenter(completePane);


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

	}


/**
 * Method that handles adding a new meal to the simulation
 */
	public void addingEvent() {
		HashMap<FoodItem,Integer> foodList = new HashMap<>();	//stores foods being added
		double mealWeight = 0.0;	//total weight for the meal
		boolean foundFood = false;	//if food was found or not

		for(HBox hbox : foodView.getItems()) {	//loop through all the foods

			Label temp = (Label)(hbox.getChildren().get(0));
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
					this.sc.runErrorPopUp("The value " + stringNum +" cannot be used as a integer.");
					e.getMessage();
				}
			}
		}
		if(!foundFood) {
			this.sc.runErrorPopUp("Could not find any foods to make a meal");
		}
		else if (mealWeight > drone.getCargoWeight()) {
			this.sc.runErrorPopUp("The meal being created weighs too much for the drone.");
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
				Label temp = (Label)(hbox.getChildren().get(0));
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
						this.sc.runErrorPopUp("This meal already exists");
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
				inputVal.setPrefWidth(50);
				HBox hbox = new HBox();
				//gets the food item as text
				Label temp = new StyleLabel(m.toString());
				temp.setMaxWidth(310);
				HBox.setHgrow(temp,Priority.ALWAYS);
				hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
				mealView.getItems().add(hbox);	//adds the hbox to the arraylist
				meals.add(m);
				this.sc.runErrorPopUp("Meal added successfully!");
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
				this.sc.runErrorPopUp("The value " + stringP +" is not a valid number.");
				e.getMessage();
			}

		}
		if (pTotal != 100) {
			this.sc.runErrorPopUp("The percentages need to add up to 100 percent.");
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
				this.sc.runErrorPopUp("The value " + stringP +" is not a valid percentage.");
				e.getMessage();
			}
			index++;
		}
		return true;
	}

	/**
	 * Method that grabs the meals from the simulation
	 	* and adds them to the meal form. Also does the setup
	 	* and functionality for the popup to edit the meals
	 * @param meals a List of Meals that is used to set up the meal list
	 * @param foods a List of FoodItem that is used to set up the food list
	 * @param d a Drone that is used to determine how much food is allowed
	 	* on the drone to keep it under the maximum weight allowed
	 */
	public void loadMeals(List<Meal> meals, List<FoodItem> foods, Drone d) {

		this.meals = meals;
		this.foods = foods;
		this.drone = d;

		/**********************set up food list**********************************/

		//creates arraylist to store all hboxes going into the listview
		ArrayList<HBox> foodElements = new ArrayList<>();
		for(int i=0; i<foods.size(); i++) {
			TextField inputVal = new TextField();	//creates the textField
			inputVal.setPrefWidth(40);

			HBox hbox = new HBox();
			//gets the food item as text
			Label temp = new StyleLabel(foods.get(i).toString());
			temp.setMaxWidth(220);
			HBox.setHgrow(temp,Priority.ALWAYS);
			
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			foodElements.add(hbox);	//adds the hbox to the arraylist
		}


		//makes the arraylist of hboxes an observable list
		ObservableList<HBox> foodsList = FXCollections.<HBox>observableArrayList(foodElements);

		// fill the food ListView

		foodView.getItems().clear();
		foodView.getItems().addAll(foodsList);
		foodView.setPrefSize(200, 200);
		foodView.setStyle("-fx-font-size: 12pt;");

		/****************************finished food list***************************/

		/****************************set up meal list***************************/

		ArrayList<HBox> mealElements = new ArrayList<>();
		for(int i=0; i<meals.size(); i++) {
			HBox hbox = new HBox();
			
			//creates the textField
			TextField inputVal = new TextField(""+meals.get(i).getPercentage());	
			inputVal.setPrefWidth(50);
			//gets the food item as text
			Label temp = new StyleLabel(meals.get(i).toString());
			temp.setMaxWidth(310);
			HBox.setHgrow(temp,Priority.ALWAYS);
			
			hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
			mealElements.add(hbox);	//adds the hbox to the arraylist
		}

		ObservableList<HBox> mealList = FXCollections.<HBox>observableArrayList(mealElements);

		// fill the meal ListView

		mealView.getItems().clear();
		mealView.getItems().addAll(mealList);
		mealView.setPrefSize(325,250);
		mealView.setStyle("-fx-font-size: 12pt;");

		/****************************finished meal list***************************/
		GridPane popupPane = new GridPane();
		/****************************set up popup buttons area***************************/
		ListView<HBox> popupList = new ListView<HBox>();
		popupList.setStyle("-fx-font-size: 12pt;");
		Label editMeal = new StyleLabel("Edit Meal");
		editMeal.setStyle("-fx-font-size: 15pt;");
		//creates arraylist to store all hboxes going into the listview
		ArrayList<HBox> foodElem = new ArrayList<>();
		for(int i=0; i<foods.size(); i++) {
			TextField inputVal = new TextField();	//creates the textField
			inputVal.setPrefWidth(40);
			HBox hbox = new HBox();
			//gets the food item as text
			Label temp = new StyleLabel(foods.get(i).toString());
			temp.setMaxWidth(320);
			HBox.setHgrow(temp,Priority.ALWAYS);
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

		Button editSave = new StyleButton("Save");

		VBox popupFields = new VBox();
		popupFields.setSpacing(10);
		popupFields.getChildren().addAll(editMeal, popupList, editSave);
		popupFields.setPadding(new Insets(25, 10, 0, 0));	//above,right,below,left
		/***************************end popup buttons area**************************/
		popupPane.addColumn(0, popupFields);
		Scene scene2 = new Scene(popupPane,300,325);
		Stage popup = new Stage();
		popup.setScene(scene2);
		popup.initModality(Modality.APPLICATION_MODAL);
		// Set the Style-properties of the GridPane
		popupPane.setPadding(new Insets(0,25,25,25));

		//calls function to check if save edited stuff (if valid)
		editSave.setOnAction(event->{
			//if successfully edited
			if(editEvent(popupList)) {
				popup.close();
			}
			else {
				this.sc.runErrorPopUp("Could not save the edit made.");
			}

		});

		//if user clicks on the edit button brings up a popup window where they
		//can change the number of items in a Meal that already exists
		edit.setOnAction(event-> {

			HBox selectedBox = (HBox) mealView.getSelectionModel().getSelectedItem();
			Meal selectedMeal = null;
			try {
				Label temp = (Label)(selectedBox.getChildren().get(0));
				String meal = temp.getText();

				//Loops through the meals and foods to grab the quantity of how many foodItems
				// are in a certain meal and auto populates the edit meal list with these values
				for(Meal m : meals) {
					if(m.toString().equals(meal)) {
						selectedMeal = m;
						for(HBox fBox : foodElem) {
							Label foodText = ((Label)fBox.getChildren().get(0));
							TextField foodField = ((TextField)fBox.getChildren().get(1));
							if(meal.contains(foodText.getText())) {
								boolean found = false;
								for(HashMap.Entry<FoodItem, Integer> food : m.getMeal().entrySet()) {
									FoodItem key = food.getKey();
									int value = food.getValue();
									if(key.toString().equals(foodText.getText())) {
										foodField.setText("" + value);
										found = true;
									}
								}
								if (!found) {
									foodField.setText("0");
								}
							} else {
								foodField.setText("0");
							}
						}
						break;
					}
				}
			}
			catch(NullPointerException e) {
				this.sc.runErrorPopUp("Please select a meal.");
			}
			if (selectedMeal != null) {
				popup.showAndWait();
			}

		});
	}
	/**
	 * Method that lets the user edit whatever they
	 * changed and add it into the simulation
	 * @param popupList ListView of HBox's that are the foods
	 * that are being displayed in the popup
	 * @return foundFood if the food was successfully edited
	 */
	public boolean editEvent(ListView<HBox> popupList) {
		HashMap<FoodItem,Integer> foodList = new HashMap<>();	//stores foods being added
		double mealWeight = 0.0;	//total weight for the meal
		boolean foundFood = false;	//if food was found or not
		double percentage = 0.0;	//store the percent
		HBox selectedMeal = (HBox) mealView.getSelectionModel().getSelectedItem();

		Label tempMealText = (Label)(selectedMeal.getChildren().get(0));  
		String mealString = tempMealText.getText();  //selected meal as a string


		//attempts to get the percentage that was in the HBox
		try {
			TextField temp = (TextField)(selectedMeal.getChildren().get(1));
			percentage = Double.parseDouble(temp.getText());	//gets the percentage

		}catch(Exception e) {
			this.sc.runErrorPopUp("There was an error in retrieving the percentage.");
		}

		for(HBox hbox : popupList.getItems()) {	//loop through all the foods

			Label temp = (Label)(hbox.getChildren().get(0));
			String food = temp.getText();	//gets the food

			TextField temp2 = (TextField)(hbox.getChildren().get(1));
			String stringNum = temp2.getText();	//gets the user's text

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
				//user entered invalid input
				catch(Exception e) {
					foundFood = false;
					this.sc.runErrorPopUp("The value " + stringNum +" cannot be used as an integer.");
					e.getMessage();
				}
			}
		}
		if(!foundFood) {
			this.sc.runErrorPopUp("Could not find any foods to make a meal.");
		}
		else if (mealWeight > drone.getCargoWeight()) {
			this.sc.runErrorPopUp("The meal being created weighs too much for the drone.");
		}
		
		//if there were no errors
		else {
			//variables for checking for duplicates
			boolean mealSuccess = false;
			Meal m = new Meal(foodList, 0);
			String[] tempM = m.toString().split(" ");
			List<String> tempNewList = Arrays.asList(tempM);
			Collections.sort(tempNewList);	//sorts the new list

			//check if potential new meal doesnt already exist
			for(HBox hbox : mealView.getItems()) {	//loop through all the meals
				mealSuccess = false;
				Label temp = (Label)(hbox.getChildren().get(0));
				String[] tempMeal = temp.getText().split(" ");
				List<String> meal = Arrays.asList(tempMeal);
				Collections.sort(meal);	//sorts the new list
				
				//checks the size of the current meal with every meal in the list
				if(meal.size() == tempNewList.size()) {
					for(int i = 0; i<meal.size(); i++) {
						//if they are not the same meal
						if(!meal.get(i).equals(tempNewList.get(i))) {
							mealSuccess = true;
						}
					}
					if(mealSuccess == false) {
						this.sc.runErrorPopUp("This meal already exists.");
						break;
					}
				}
				else {
					mealSuccess = true;
				}
			}
			//create meal - since no errors and no duplicates
			if(mealSuccess){
				//add new meal to display
				TextField inputVal = new TextField(""+percentage);	//creates the textField
				inputVal.setPrefWidth(50);
				HBox hbox = new HBox();
				//gets the food item as text
				Label temp = new StyleLabel(m.toString());
				temp.setMaxWidth(310);
				HBox.setHgrow(temp,Priority.ALWAYS);
				hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
				mealView.getItems().remove(selectedMeal);
				mealView.getItems().add(hbox);	//adds the hbox to the arraylist
				hbox.setPrefWidth(20);
				m.setPercentage(percentage);

				//alphabetizes the selected meal and the each meal in the list of meals and compares them 
				String[] tempMeal = mealString.split(" ");
				List<String> listedMeal = Arrays.asList(tempMeal);
				Collections.sort(listedMeal);	//sorts the new list
				
				//removes the selected meal
				for(Meal meal : meals) {
					boolean mealFound = true;
					String[] tempMealArr = meal.toString().split(" ");
					List<String> mealList = Arrays.asList(tempMealArr);
					Collections.sort(mealList);	//sorts the new list
					
					if(mealList.size() == listedMeal.size()) {
						for(int i = 0; i<mealList.size(); i++) {
							if(!mealList.get(i).equals(listedMeal.get(i))) {
								mealFound = false;
							}
						}
						if(mealFound == true) {
							meals.remove(meal);
							//adds the changed meal into the list
							meals.add(m);
//							this.sc.runErrorPopUp("Meal edited successfully!");
							return true;
						}
					}
				}	
			}
		}
		return false;
	}
}