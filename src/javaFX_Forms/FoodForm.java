package javaFX_Forms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javaClasses.Drone;
import javaClasses.FoodItem;
import javaClasses.Meal;
import javaFX_Styling.StyleButton;
import javaFX_Styling.StyleLabel;
import javaFX_Styling.StyleTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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

public class FoodForm extends Form
{

	//variables
	private ListView<FoodItem> foodView;	//list of foods that are in the list view (displayed)
	private List<FoodItem> displayedFoods;	//a copy of the listView of foods (in a regular list)
	//this will eventually override the location's food list on save
	private Drone drone;	//a drone object to get the weight the drone can carry
	private List<Meal> meals; 	//meals for displaying on deletion of a food

	/**
	 * constructor used in main form for displaying the food form as needed
	 * @param sc
	 * @param layout
	 */
	public FoodForm(SceneController sc, BorderPane layout) {
		super(sc, layout);

		/****************************setup the food list***************************/
		this.foodView = new ListView<>();	//initialize foodView

		// Set the Orientation of the ListView
		foodView.setOrientation(Orientation.VERTICAL);
		// Set the Size of the ListView
		foodView.setPrefSize(120, 200);

		VBox foodSelection = new VBox();

		//create food label
		Label foodLabel = new StyleLabel("Current Foods:");
		foodLabel.setStyle("-fx-font-size: 15pt;");

		// Set Spacing to 10 pixels
		foodSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		foodSelection.getChildren().addAll(foodLabel,foodView);
		foodSelection.setPrefWidth(200);	//prevents a smooshed display

		/****************************set up buttons***************************/
		// create a button 
		Button edit = new StyleButton("Edit");
		Button delete = new StyleButton("Delete");


		//edit set on action is done in popup section of code (below)
		//delete button action:
		delete.setOnAction((event) -> {
			//get the food selected
			FoodItem selectedFood = (FoodItem) foodView.getSelectionModel().getSelectedItem();
			System.out.println("delete: " + selectedFood);
			//if a food was selected, delete it
			if (selectedFood != null) {
				deleteFood(selectedFood);
			}
		});

		//button section on gui
		VBox buttons = new VBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(edit,delete);	//adds buttons to vbox
		buttons.setPadding(new Insets(40, 10, 0, 0));	//above,right,below,left
		buttons.setPrefWidth(150);	//prevents buttons from showing up as "..."
		/***************************finished buttons**************************/

		/****************************set up add area***************************/
		Label addLabel = new StyleLabel("Add New Food:");
		addLabel.setStyle("-fx-font-size: 15pt;");
		//creates a text display alongside an editable textField for the 3 food parameters
		Label nameTitle = new StyleLabel("Name: ");
		TextField inputName = new StyleTextField();
		Label weightTitle = new StyleLabel("Weight: ");
		TextField inputWeight = new StyleTextField();
		Label prepTimeTitle = new StyleLabel("Prep Time: ");
		TextField inputPrepTime = new StyleTextField();



		//creates an HBox for each of the fields
		nameTitle.setMaxWidth(100);
		HBox.setHgrow(nameTitle,Priority.ALWAYS);
		HBox inputRow1 = new HBox();
		inputRow1.setSpacing(10);
		inputRow1.getChildren().addAll(nameTitle,inputName);	//adds buttons to hbox
		inputRow1.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left

		weightTitle.setMaxWidth(100);
		HBox.setHgrow(weightTitle,Priority.ALWAYS);
		HBox inputRow2 = new HBox();
		inputRow2.setSpacing(10);
		inputRow2.getChildren().addAll(weightTitle,inputWeight);	//adds buttons to hbox
		inputRow2.setPadding(new Insets(0, 10, 0, 0));

		prepTimeTitle.setMaxWidth(100);
		HBox.setHgrow(prepTimeTitle,Priority.ALWAYS);
		HBox inputRow3 = new HBox();
		inputRow3.setSpacing(10);
		inputRow3.getChildren().addAll(prepTimeTitle,inputPrepTime);	//adds buttons to hbox
		inputRow3.setPadding(new Insets(0, 10, 0, 0));

		Button addFood = new StyleButton("Add");

		//on click listener
		addFood.setOnAction(event->{
			//calls function to check if adding stuff is valid
			//checks for duplicates
			//refreshes the list upon completion
			addingEvent(inputName,inputWeight,
					inputPrepTime);
		});

		//vBox for all the HBoxes and the button created above
		VBox allFields = new VBox();
		allFields.setSpacing(10);
		allFields.getChildren().addAll(addLabel,inputRow1,inputRow2,inputRow3,addFood);
		allFields.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left
		/***************************finished add area**************************/

		// Create the GridPane
		GridPane pane = new GridPane();
		// Set the form to be displayed in the top center
		pane.setAlignment(Pos.TOP_CENTER);
		// Set the horizontal and vertical gaps between children
		pane.setHgap(10);
		pane.setVgap(10);
		// Add the gui components
		pane.addColumn(0, foodSelection);
		pane.addColumn(1, buttons);
		pane.addColumn(2, allFields);

		// Set the Style-properties of the GridPane
		pane.setPadding(new Insets(25,25,25,25));
		pane.setAlignment(Pos.TOP_CENTER);

		layout.setCenter(pane);

		// get buttons and set event handlers
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());

		//cancel button on click listener
		cancel.setOnAction((event) -> {
			this.sc.switchToHome();	//switches to home without saving
		});

		//save button on click listener
		save.setOnAction((event) -> {
			//saves changes (by replacement) and switches to home
			this.sc.replaceFoods(displayedFoods);
			this.sc.replaceMeals(meals);
			this.sc.switchToHome();
		});

		/***************************popup section******************************/
		GridPane popupPane = new GridPane();
		/****************************set up popup buttons area***************************/
		//same setup as the add new food area but inside the popup
		Label editLabel = new StyleLabel("Edit Food Item:");
		editLabel.setStyle("-fx-font-size: 15pt;");
		Label namePopup = new StyleLabel("Name: ");
		TextField input1 = new StyleTextField();
		Label weightPopup = new StyleLabel("Weight: ");
		TextField input2 = new StyleTextField();
		Label prepTimePopup = new StyleLabel("Prep Time: ");
		TextField input3 = new StyleTextField();

		//add all textboxes and fields to Hboxes
		namePopup.setMaxWidth(120);
		HBox.setHgrow(namePopup,Priority.ALWAYS);
		HBox popupRow1 = new HBox();
		popupRow1.setSpacing(10);
		popupRow1.getChildren().addAll(namePopup,input1);	//adds buttons to vbox
		popupRow1.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left
		weightPopup.setMaxWidth(120);
		HBox.setHgrow(weightPopup,Priority.ALWAYS);
		HBox popupRow2 = new HBox();
		popupRow2.setSpacing(10);
		popupRow2.getChildren().addAll(weightPopup,input2);	//adds buttons to vbox
		popupRow2.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left
		prepTimePopup.setMaxWidth(120);
		HBox.setHgrow(prepTimePopup,Priority.ALWAYS);
		HBox popupRow3 = new HBox();
		popupRow3.setSpacing(10);
		popupRow3.getChildren().addAll(prepTimePopup,input3);	//adds buttons to vbox
		popupRow3.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left

		Button editSave = new StyleButton("Save");	//save edit button

		//add hboxes to the vbox
		VBox popupFields = new VBox();
		popupFields.setSpacing(10);
		popupFields.getChildren().addAll(editLabel,popupRow1,popupRow2,popupRow3,editSave);
		popupFields.setPadding(new Insets(25, 10, 0, 0));	//p
		/***************************end popup buttons area**************************/
		//setup popup to display in a column
		popupPane.addColumn(0, popupFields);
		Scene scene2 = new Scene(popupPane,300,300);	//set size of popup
		Stage popup = new Stage();
		popup.setScene(scene2);
		popup.initModality(Modality.APPLICATION_MODAL);	//makes it popup instead of new scene
		// Set the Style-properties of the GridPane
		popupPane.setPadding(new Insets(0,25,25,25));

		//calls function to save edited stuff (if valid)
		editSave.setOnAction(event->{
			//if successfully edited close the popup
			if(editSave(input1,input2,input3)) {
				popup.close();
			}

		});

		//on click listener for the edit button (for a food item in the list)
		edit.setOnAction(event-> {
			//get the food they selected
			FoodItem selectedFood = (FoodItem) foodView.getSelectionModel().getSelectedItem();
			System.out.println("edit: " + selectedFood);

			//if a food was selected, edit it
			if (selectedFood != null) {
				//prepopulates the popup with the food's info
				input1.setText(selectedFood.getName());
				input2.setText("" + selectedFood.getWeight());
				input3.setText("" + selectedFood.getPrepTime());

				//displays the popup for editing
				popup.showAndWait();
			}
			//informs user they did not have something selected
			else {
				System.out.println("Must select something to edit");
			}
		});
	}

	/**
	 * function for loading all the foods into the display
	 * @param foods - takes in a list of foods
	 * @param d - takes in the drone (for carrying capacity checking)
	 */
	public void loadFoods(List<FoodItem> foods, List<Meal> meals, Drone d) {

		this.drone = d;
		this.meals = meals;

		// given a list of foods, load it into the form
		ObservableList<FoodItem> foodList = FXCollections.<FoodItem>observableArrayList(foods);
		this.displayedFoods = foods;

		// reset the items in the foodView
		foodView.getItems().clear();
		foodView.getItems().addAll(foodList);
		foodView.setStyle("-fx-font-size: 12pt;");
	}

	/**
	 * This function is called when the user attempts to add a new food.
	 * It checks to see if the input is valid and adds the food if it is
	 * 
	 * @param inputName - the name of the food the user gave
	 * @param inputWeight - the weight they gave
	 * @param inputPrepTime - the preptime the user gave
	 */
	public void addingEvent(TextField inputName, TextField inputWeight, 
			TextField inputPrepTime) {

		//variables
		boolean errorFound = false; 	//error tracking boolean
		double time = 0;				//preptime variable
		double w = 0;					//weight variable
		String name = inputName.getText();			//gets the name as a string
		String prepTime = inputPrepTime.getText();	//converts preptime given to a string
		String weight = inputWeight.getText();		//converts weight to a string

		//try to make weight and preptime doubles (if it fails inform the user and dont add the food)
		try{
			time = Double.parseDouble(prepTime);
		}
		//not a valid double for preptime
		catch(Exception e) {
			errorFound = true;	//food will not be added since there was an error
			//inform user
			this.sc.runErrorPopUp("Prep time is not a recognizable number.");
		}
		try{
			w = Double.parseDouble(weight);
			//check if weight is under drone carrying capacity
			if(w> drone.getCargoWeight()) {
				errorFound = true;	//if the food is too large dont add its
				this.sc.runErrorPopUp("The weight entered is too much for the drone given.");
			}
		}
		//not a valid double for weight
		catch(Exception e) {
			errorFound = true;
			//inform user weight wasnt valid
			this.sc.runErrorPopUp("Weight entered is not a recognizable number.");
		}
		//empty name - if name is nothing then dont add the food
		if(name.isEmpty()) {
			this.sc.runErrorPopUp("Name for a new food cannot be empty.");
			errorFound = true;
		}
		//check if the food already exists
		for(FoodItem f : displayedFoods) {
			//convert foods to lowercase and see if the name of the food matches an existing food
			if(name.toLowerCase().equals(f.toString().toLowerCase())) {
				this.sc.runErrorPopUp("This food (" + name + ") already exists");
				errorFound = true;
			}
		}
		//if there were no errors
		if(!errorFound){
			//create the new food
			FoodItem newFood = new FoodItem(name, w, time);
			//display the newly added food 
			foodView.getItems().add(newFood);
			//add the newly added food to the list of foods
			displayedFoods.add(newFood);
			System.out.println("added " + name);

			//reset the text boxes
			inputName.setText("");
			inputWeight.setText("");
			inputPrepTime.setText("");
		}
	}

	/**
	 * function that allows the user to edit an existing food in the list
	 * This function is very similar to the addEvent function, with the addition
	 * of deleting the food they selected to edit before adding the new (edited) food
	 * @param input1 - takes in the name the user gave
	 * @param input2 - the weight the user gave
	 * @param input3 - the preptime the user gave
	 * @return
	 */
	public boolean editSave(TextField input1, TextField input2, 
			TextField input3) {

		FoodItem selectedFood = (FoodItem) foodView.getSelectionModel().getSelectedItem();

		//variables
		boolean errorFound = false; 	//error tracking boolean
		double time = 0;				//preptime variable
		double w = 0;					//weight variable
		String name = input1.getText();			//gets the name as a string
		String prepTime = input3.getText();	//converts preptime given to a string
		String weight = input2.getText();		//converts weight to a string

		//try to make weight and preptime doubles (if it fails inform the user and dont add the food)
		try{
			time = Double.parseDouble(prepTime);
		}
		//not a valid double for preptime
		catch(Exception e) {
			errorFound = true;	//food will not be added since there was an error
			//inform user
			this.sc.runErrorPopUp("Prep time is not a recognizable number.");
		}
		try{
			w = Double.parseDouble(weight);
			//check if weight is under drone carrying capacity
			if(w> drone.getCargoWeight()) {
				errorFound = true;	//if the food is too large dont add its
				//System.out.println("the weight is too much for a drone");
				this.sc.runErrorPopUp("The weight entered is too much for the drone given");
			}
		}
		//not a valid double for weight
		catch(Exception e) {
			errorFound = true;
			//inform user weight wasnt valid
			//System.out.println("Weight was not a recognizable number.");
			this.sc.runErrorPopUp("The weight entered is not a recognizable number");
		}
		//empty name - if name is nothing then dont add the food
		if(name.isEmpty()) {
			this.sc.runErrorPopUp("The name for a new food cannot be empty.");
			errorFound = true;
		}

		//check if the food already exists (and isnt the food being edited)
		for(FoodItem f : displayedFoods) {	//go through all the foods
			//if the foods are equal and not the food being edited
			if(name.toLowerCase().equals(f.toString().toLowerCase())
					&& !(name.toLowerCase().equals(selectedFood.toString().toLowerCase()))) {

				//inform user and dont add the food
				this.sc.runErrorPopUp("This food (" + name + ") already exists");
				errorFound = true;
			}
		}

		//if there were no errors
		if(!errorFound){


			//create the food
			FoodItem newFood = new FoodItem(name, w, time);

			//display the edited food after removing the old version of it
			foodView.getItems().remove(selectedFood);
			foodView.getItems().add(newFood);

			//add the edited food to the list after removing the old version
			displayedFoods.remove(selectedFood);
			displayedFoods.add(newFood);


			//if the food was changed at all then adjust the meals it was in
			if(!selectedFood.getName().equals(newFood.getName())
					|| selectedFood.getPrepTime() != newFood.getPrepTime()
					|| selectedFood.getWeight() != newFood.getWeight()) {

				//get meals that will be affected
				for(int i=0; i<meals.size(); i++) {
					if(meals.get(i).toString().contains(selectedFood.toString())){
						HashMap<FoodItem, Integer> map = meals.get(i).getMeal();
						for(HashMap.Entry<FoodItem, Integer> f : map.entrySet()) {
							if(f.getKey().toString().equals(selectedFood.toString())) {
								int tempCount = f.getValue();
								meals.get(i).deleteFood(f.getKey());
								meals.get(i).addFood(newFood, tempCount);
								break;
							}
						}
					}
				}
			}


			//inform user
			this.sc.runErrorPopUp("Successfully edited " + name + "!");
			return true;	//return true since it added correctly
		}
		return false;		//could not add food so return false
	}

	/**
	 * delete the selected food item
	 * @param selectedFood
	 */
	private void deleteFood(FoodItem selectedFood) {
		List<HBox> mealElements = new ArrayList<>();
		List<Meal> tempMeals = new ArrayList<>();	//for storing meals that wont be deleted
		//check which meals have the food being deleted to inform user (and add them to display)
		for(int i=0; i<meals.size(); i++) {
			if(meals.get(i).toString().contains(selectedFood.toString())){
				//creates the textField
				TextField inputVal = new TextField(""+meals.get(i).getPercentage());
				inputVal.setPrefWidth(50);
				inputVal.setDisable(true);

				HBox hbox = new HBox();
				//gets the food item as text
				Label temp = new Label(meals.get(i).toString());
				temp.setMaxWidth(300);
				HBox.setHgrow(temp,Priority.ALWAYS);
				hbox.getChildren().addAll(temp, inputVal);	//creates hbox with the food and the textField
				mealElements.add(hbox);	//adds the hbox to the arraylist
				hbox.setPrefWidth(20);
			}
			else {
				tempMeals.add(meals.get(i));
			}
		}
		if(mealElements.size()==0) {
			displayedFoods.remove(selectedFood);
			foodView.getItems().remove(selectedFood);
			return;
		}


		ListView<HBox> mealView = new ListView<>();
		mealView.setStyle("-fx-font-size: 12pt;");
		mealView.setPrefWidth(300);

		ObservableList<HBox> mealList = FXCollections.<HBox>observableArrayList(mealElements);

		// fill the meal ListView

		mealView.getItems().clear();
		mealView.getItems().addAll(mealList);


		GridPane popupPane = new GridPane();
		Label mealsLabel = new StyleLabel("Meals that will be deleted:");


		Button cancelButton = new StyleButton("Cancel");
		Button confirmButton = new StyleButton("Confirm");
		HBox popupButtons = new HBox();
		popupButtons.setSpacing(10);
		popupButtons.getChildren().addAll(cancelButton, confirmButton);

		VBox popupFields = new VBox();
		popupFields.setSpacing(10);
		popupFields.getChildren().addAll(mealsLabel, mealView, popupButtons);
		popupFields.setPadding(new Insets(25, 10, 0, 0));	//above,right,below,left
		/***************************end popup buttons area**************************/
		popupPane.addColumn(0, popupFields);
		Scene scene2 = new Scene(popupPane,500,400);
		Stage popup = new Stage();
		popup.setScene(scene2);
		popup.initModality(Modality.APPLICATION_MODAL);
		popupPane.setPadding(new Insets(0,25,25,25));

		confirmButton.setOnAction(event->{
			//deletes the meals displayed (from list stored here not location's list yet)
			meals = tempMeals;	

			//update percentages to add to 100
			double totalPercent = 0.0;
			for(Meal m : meals) {
				totalPercent += m.getPercentage();
			}
			if(totalPercent !=0) {
				for(Meal m: meals) {
					double temp = m.getPercentage();
					DecimalFormat df = new DecimalFormat("#.#");	//format decimals
					double perc = Double.parseDouble(df.format(temp* (100 / totalPercent)));
					m.setPercentage(perc);
				}
				this.sc.runErrorPopUp("Remaining meals' percentages have increased proportionately");
			}
			popup.close();
			//deletes the food
			displayedFoods.remove(selectedFood);
			foodView.getItems().remove(selectedFood);
		});
		cancelButton.setOnAction(event->{
			popup.close();
		});

		popup.showAndWait();
	}


}