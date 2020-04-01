package javaFX_Forms;
import java.util.ArrayList;

import javaClasses.DeliveryPoint;
import javaClasses.Drone;
import javaClasses.FoodItem;
import javaClasses.Location;
import javaClasses.Meal;
import javaClasses.ShiftDetails;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
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

public class FoodForm extends Application
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
		/****************************set up food list***************************/
		//create food label
		Label foodLabel = new Label("Current Foods:");

//		FoodItem burger = new FoodItem("Burger", 2, 2);
//		location.addFood(burger);
//		FoodItem fries = new FoodItem("Fries", .5, 1);
//		location.addFood(fries);
//		FoodItem drink = new FoodItem("Drink", 2, 0);
//		location.addFood(drink);
//		FoodItem pie = new FoodItem("Pie", 2.5, 8);
//		location.addFood(pie);
//		location.getFoods();
		

		ObservableList<FoodItem> foodList = FXCollections.<FoodItem>observableArrayList(location.getFoods());

		// Create the ListView for the seasons
		ListView<FoodItem> foodView = new ListView<>(foodList);
		// Set the Orientation of the ListView
		foodView.setOrientation(Orientation.VERTICAL);
		// Set the Size of the ListView
		foodView.setPrefSize(120, 100);

		// Create the Season VBox
		VBox foodSelection = new VBox();
		// Set Spacing to 10 pixels
		foodSelection.setSpacing(10);
		// Add the Label and the List to the VBox
		foodSelection.getChildren().addAll(foodLabel,foodView);
		foodSelection.setPrefWidth(200);	//prevents a smooshed display
		/****************************finished food list***************************/

		/****************************set up edit list buttons***************************/
		// create a button 
		Button edit = new Button("Edit"); 
		Button delete = new Button("Delete");

		edit.setOnAction(event->
		System.out.println("edit: " + foodView.getSelectionModel().getSelectedItem()));
		delete.setOnAction(event->
		System.out.println("delete: " + foodView.getSelectionModel().getSelectedItem()));
		//button section on gui
		VBox buttons = new VBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(edit,delete);	//adds buttons to vbox
		buttons.setPadding(new Insets(50, 10, 0, 0));	//above,right,below,left
		buttons.setPrefWidth(150);	//prevents buttons from showing up as "..."
		/***************************finished list buttons**************************/

		/****************************set up add area***************************/
		Text nameTitle = new Text("Name: ");
		TextField inputName = new TextField();
		Text weightTitle = new Text("Weight: ");
		TextField inputWeight = new TextField();
		Text prepTimeTitle = new Text("Prep Time: ");
		TextField inputPrepTime = new TextField();
		HBox inputRow1 = new HBox();
		inputRow1.setSpacing(10);
		inputRow1.getChildren().addAll(nameTitle,inputName);	//adds buttons to vbox
		inputRow1.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left
		HBox inputRow2 = new HBox();
		inputRow2.setSpacing(10);
		inputRow2.getChildren().addAll(weightTitle,inputWeight);	//adds buttons to vbox
		inputRow2.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left
		HBox inputRow3 = new HBox();
		inputRow3.setSpacing(10);
		inputRow3.getChildren().addAll(prepTimeTitle,inputPrepTime);	//adds buttons to vbox
		inputRow3.setPadding(new Insets(0, 10, 0, 0));	//above,right,below,left

		Button addFood = new Button("Add New Food");

		//calls function to check if adding stuff is valid
		//need to check for duplicates
		//need to refresh the list
		addFood.setOnAction(event->
		addingEvent(inputName.getText(),inputWeight.getText(),
				inputPrepTime.getText(),foodView)
				);

		delete.setOnAction((event) -> {
			FoodItem selectedFood = (FoodItem) foodView.getSelectionModel().getSelectedItem();
			//Location selectedLocation = locations.getSelectionModel().getSelectedItem();
			
			//if (selectedLocation.getDeliveryPoints().contains(selectedPoint)) {
			if (selectedFood != null) {
				location.deleteFood(selectedFood);
				foodView.getItems().remove(selectedFood);
			}
		});
//		foodView.refresh();

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
		// Add the two gui components
		pane.addColumn(0, foodSelection);
		pane.addColumn(1, buttons);
		pane.addColumn(2, allFields);



		//        // Add the TextArea at position 2
		//        pane.addColumn(2, logging);

		// Set the Style-properties of the GridPane
		pane.setPadding(new Insets(25,25,25,25));

		// Create the Scene
		Scene scene = new Scene(pane, 400, 200);
		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title
		stage.setTitle("Food Settings");
		// Display the Stage
		stage.show();
	}

	// Method to display the Season, which has been changed
	public void foodSelectChanged(ObservableValue<? extends String> observable,String oldValue,String newValue) 
	{
		String oldText = oldValue == null ? "null" : oldValue.toString();
		String newText = newValue == null ? "null" : newValue.toString();

		logging.appendText("food selection changed: old = " + oldText + ", new = " + newText + "\n");
	}

	public void addingEvent(String inputName, String weight, 
			String prepTime, ListView<FoodItem> foodView) {
		
		boolean errorFound = false;
		double time = 0;
		double w = 0;
		String name = "";
		
		try{
			time = Double.parseDouble(prepTime);
		}
		//not a valid double for preptime
		catch(Exception e) {
			errorFound = true;
			System.out.println("Prep time was not a recognizable number.");
			e.getMessage();
		}
		try{
			w = Double.parseDouble(weight);
		}
		//not a valid double for weight
		catch(Exception e) {
			errorFound = true;
			System.out.println("Weight was not a recognizable number.");
			e.getMessage();
		}
		//empty name
		if(inputName.isEmpty()) {
			System.out.println("Name for a new food cannot be empty.");
			errorFound = true;
		}
		//if there were no errors
		else if(!errorFound){
			FoodItem newFood = new FoodItem(inputName, w, time);
			//display the newly added food if it was added correctly
			if(location.addFood(newFood)) {
				foodView.getItems().add(newFood);
				System.out.println("added " + inputName);
			}
		}
	}
}