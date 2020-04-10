package javaFX_Forms;
import java.util.List;

import javaClasses.Drone;
import javaClasses.FoodItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class FoodForm extends Form
{

	//variables
	private ListView<FoodItem> foodView;	//list of foods that are in the list view (displayed)
	private List<FoodItem> displayedFoods;	//a copy of the listView of foods (in a regular list)
	//this will eventually override the location's food list on save
	private Drone drone;	//a drone object to get the weight the drone can carry

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
		foodView.setPrefSize(120, 100);

		VBox foodSelection = new VBox();

		//create food label
		Label foodLabel = new Label("Current Foods:");

		// Set Spacing to 10 pixels
		foodSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		foodSelection.getChildren().addAll(foodLabel,foodView);
		foodSelection.setPrefWidth(200);	//prevents a smooshed display

		/****************************set up buttons***************************/
		// create a button 
		Button edit = new Button("Edit"); 
		Button delete = new Button("Delete");

		//edit set on action is done in popup section of code (below)
		//delete button action:
		delete.setOnAction((event) -> {
			//get the food selected
			FoodItem selectedFood = (FoodItem) foodView.getSelectionModel().getSelectedItem();
			System.out.println("delete: " + selectedFood);
			//if a food was selected, delete it
			if (selectedFood != null) {
				displayedFoods.remove(selectedFood);
				foodView.getItems().remove(selectedFood);
			}
		});

		//button section on gui
		VBox buttons = new VBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(edit,delete);	//adds buttons to vbox
		buttons.setPadding(new Insets(50, 10, 0, 0));	//above,right,below,left
		buttons.setPrefWidth(150);	//prevents buttons from showing up as "..."
		/***************************finished buttons**************************/

		/****************************set up add area***************************/
		//creates a text display alongside an editable textField for the 3 food parameters
		Text nameTitle = new Text("Name: ");
		TextField inputName = new TextField();
		Text weightTitle = new Text("Weight: ");
		TextField inputWeight = new TextField();
		Text prepTimeTitle = new Text("Prep Time: ");
		TextField inputPrepTime = new TextField();

		//creates an HBox for each of the fields
		HBox inputRow1 = new HBox();
		inputRow1.setSpacing(10);
		inputRow1.getChildren().addAll(nameTitle,inputName);	//adds buttons to hbox
		inputRow1.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left

		HBox inputRow2 = new HBox();
		inputRow2.setSpacing(10);
		inputRow2.getChildren().addAll(weightTitle,inputWeight);	//adds buttons to hbox
		inputRow2.setPadding(new Insets(0, 10, 0, 0));

		HBox inputRow3 = new HBox();
		inputRow3.setSpacing(10);
		inputRow3.getChildren().addAll(prepTimeTitle,inputPrepTime);	//adds buttons to hbox
		inputRow3.setPadding(new Insets(0, 10, 0, 0));

		Button addFood = new Button("Add New Food");

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
		allFields.getChildren().addAll(inputRow1,inputRow2,inputRow3,addFood);
		allFields.setPadding(new Insets(25, 10, 0, 0));	//above,right,below,left
		/***************************finished add area**************************/

		// Create the GridPane
		GridPane pane = new GridPane();
		// Set the horizontal and vertical gaps between children
		pane.setHgap(10);
		pane.setVgap(5);
		// Add the gui components
		pane.addColumn(0, foodSelection);
		pane.addColumn(1, buttons);
		pane.addColumn(2, allFields);

		// Set the Style-properties of the GridPane
		pane.setPadding(new Insets(25,25,25,25));

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
			this.sc.switchToHome();
		});

		/***************************popup section******************************/
		GridPane popupPane = new GridPane();
		/****************************set up popup buttons area***************************/
		//same setup as the add new food area but inside the popup
		Label editLabel = new Label("Edit Food Item:");
		Text namePopup = new Text("Name: ");
		TextField input1 = new TextField();
		Text weightPopup = new Text("Weight: ");
		TextField input2 = new TextField();
		Text prepTimePopup = new Text("Prep Time: ");
		TextField input3 = new TextField();

		//add all textboxes and fields to Hboxes
		HBox popupRow1 = new HBox();
		popupRow1.setSpacing(10);
		popupRow1.getChildren().addAll(namePopup,input1);	//adds buttons to vbox
		popupRow1.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left
		HBox popupRow2 = new HBox();
		popupRow2.setSpacing(10);
		popupRow2.getChildren().addAll(weightPopup,input2);	//adds buttons to vbox
		popupRow2.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left
		HBox popupRow3 = new HBox();
		popupRow3.setSpacing(10);
		popupRow3.getChildren().addAll(prepTimePopup,input3);	//adds buttons to vbox
		popupRow3.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left

		Button editSave = new Button("Save");	//save edit button

		//add hboxes to the vbox
		VBox popupFields = new VBox();
		popupFields.setSpacing(10);
		popupFields.getChildren().addAll(editLabel,popupRow1,popupRow2,popupRow3,editSave);
		popupFields.setPadding(new Insets(25, 10, 0, 0));	//p
		/***************************end popup buttons area**************************/
		//setup popup to display in a column
		popupPane.addColumn(0, popupFields);
		Scene scene2 = new Scene(popupPane,200,200);	//set size of popup
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
	public void loadFoods(List<FoodItem> foods, Drone d) {

		this.drone = d;

		// given a list of foods, load it into the form
		ObservableList<FoodItem> foodList = FXCollections.<FoodItem>observableArrayList(foods);
		this.displayedFoods = foods;

		// reset the items in the foodView
		foodView.getItems().clear();
		foodView.getItems().addAll(foodList);
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
			System.out.println("Prep time was not a recognizable number.");
		}
		try{
			w = Double.parseDouble(weight);
			//check if weight is under drone carrying capacity
			if(w> drone.getCargoWeight()) {
				errorFound = true;	//if the food is too large dont add its
				System.out.println("the weight is too much for a drone");
			}
		}
		//not a valid double for weight
		catch(Exception e) {
			errorFound = true;
			//inform user weight wasnt valid
			System.out.println("Weight was not a recognizable number.");
		}
		//empty name - if name is nothing then dont add the food
		if(name.isEmpty()) {
			System.out.println("Name for a new food cannot be empty.");
			errorFound = true;
		}
		//check if the food already exists
		for(FoodItem f : displayedFoods) {
			//convert foods to lowercase and see if the name of the food matches an existing food
			if(name.toLowerCase().equals(f.toString().toLowerCase())) {
				System.out.println("This food (" + name + ") already exists");
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
			System.out.println("Prep time was not a recognizable number.");
		}
		try{
			w = Double.parseDouble(weight);
			//check if weight is under drone carrying capacity
			if(w> drone.getCargoWeight()) {
				errorFound = true;	//if the food is too large dont add its
				System.out.println("the weight is too much for a drone");
			}
		}
		//not a valid double for weight
		catch(Exception e) {
			errorFound = true;
			//inform user weight wasnt valid
			System.out.println("Weight was not a recognizable number.");
		}
		//empty name - if name is nothing then dont add the food
		if(name.isEmpty()) {
			System.out.println("Name for a new food cannot be empty.");
			errorFound = true;
		}
		
		//check if the food already exists (and isnt the food being edited)
		for(FoodItem f : displayedFoods) {	//go through all the foods
			//if the foods are equal and not the food being edited
			if(name.toLowerCase().equals(f.toString().toLowerCase())
					&& !(name.toLowerCase().equals(selectedFood.toString().toLowerCase()))) {
				
				//inform user and dont add the food
				System.out.println("This food (" + name + ") already exists");
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
			//inform user
			System.out.println("successfully edited " + name);
			return true;	//return true since it added correctly
		}
		return false;		//could not add food so return false
	}
}