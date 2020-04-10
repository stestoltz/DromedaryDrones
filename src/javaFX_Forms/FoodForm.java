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

	private ListView<FoodItem> foodView;
	private List<FoodItem> displayedFoods;
	private Drone drone;

	public FoodForm(SceneController sc, BorderPane layout) {
		super(sc, layout);
		
		/****************************setup the food list***************************/
		this.foodView = new ListView<>();

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

		//edit set on action is done in popup section of code
		//delete button action:
		delete.setOnAction((event) -> {
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
		addFood.setOnAction(event->{
			addingEvent(inputName,inputWeight,
					inputPrepTime,foodView);
		});

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
			this.sc.replaceFoods(displayedFoods);
			this.sc.switchToHome();
		});
		
		/***************************popup section******************************/
		GridPane popupPane = new GridPane();
		/****************************set up popup buttons area***************************/
		Label editLabel = new Label("Edit Food Item:");
		Text namePopup = new Text("Name: ");
		TextField input1 = new TextField();
		Text weightPopup = new Text("Weight: ");
		TextField input2 = new TextField();
		Text prepTimePopup = new Text("Prep Time: ");
		TextField input3 = new TextField();
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
		
		Button editSave = new Button("Save");

		VBox popupFields = new VBox();
		popupFields.setSpacing(10);
		popupFields.getChildren().addAll(editLabel,popupRow1,popupRow2,popupRow3,editSave);
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
			if(editSave(input1,input2,input3,foodView)) {
				popup.close();
			}
			
		});
		
		edit.setOnAction(event-> {
			
			FoodItem selectedFood = (FoodItem) foodView.getSelectionModel().getSelectedItem();
			System.out.println("edit: " + selectedFood);
			//if a food was selected, delete it
			if (selectedFood != null) {
				input1.setText(selectedFood.getName());
				input2.setText("" + selectedFood.getWeight());
				input3.setText("" + selectedFood.getPrepTime());
				popup.showAndWait();
			}
			else {
				System.out.println("Must select something to edit");
			}
		});
	}

	public void loadFoods(List<FoodItem> foods, Drone d) {

		this.drone = d;
		// given a list of foods, load it into the form

		ObservableList<FoodItem> foodList = FXCollections.<FoodItem>observableArrayList(foods);

		this.displayedFoods = foods;
		// reset the items in the foodView
		foodView.getItems().clear();
		foodView.getItems().addAll(foodList);
	}

	public void addingEvent(TextField inputName, TextField inputWeight, 
			TextField inputPrepTime, ListView<FoodItem> foodView) {

		boolean errorFound = false;
		double time = 0;
		double w = 0;
		String name = inputName.getText();
		String prepTime = inputPrepTime.getText();
		String weight = inputWeight.getText();

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
			//check if weight is under flight weight
			if(w> drone.getCargoWeight()) {
				errorFound = true;
				System.out.println("the weight is too much for a drone");
			}
		}
		//not a valid double for weight
		catch(Exception e) {
			errorFound = true;
			System.out.println("Weight was not a recognizable number.");
			e.getMessage();
		}
		//empty name
		if(name.isEmpty()) {
			System.out.println("Name for a new food cannot be empty.");
			errorFound = true;
		}
		//check if the food already exists
		for(FoodItem f : displayedFoods) {
			if(name.toLowerCase().equals(f.toString().toLowerCase())) {
				System.out.println("This food (" + name + ") already exists");
				errorFound = true;
			}
		}
		//if there were no errors
		if(!errorFound){

			FoodItem newFood = new FoodItem(name, w, time);
			//display the newly added food 
			foodView.getItems().add(newFood);
			displayedFoods.add(newFood);
			System.out.println("added " + name);

			//clear out the text boxes
			inputName.setText("");
			inputWeight.setText("");
			inputPrepTime.setText("");
		}
	}
	
	
	public boolean editSave(TextField input1, TextField input2, 
			TextField input3, ListView<FoodItem> foodView) {

		FoodItem selectedFood = (FoodItem) foodView.getSelectionModel().getSelectedItem();
		
		boolean errorFound = false;
		double time = 0;
		double w = 0;
		String name = input1.getText();
		String weight = input2.getText();
		String prepTime = input3.getText();

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
			//check if weight is under flight weight
			if(w> drone.getCargoWeight()) {
				errorFound = true;
				System.out.println("the weight is too much for a drone");
			}
		}
		//not a valid double for weight
		catch(Exception e) {
			errorFound = true;
			System.out.println("Weight was not a recognizable number.");
			e.getMessage();
		}
		//empty name
		if(name.isEmpty()) {
			System.out.println("Name for a new food cannot be empty.");
			errorFound = true;
		}
		//check if the food already exists (and isnt the food being edited)
		for(FoodItem f : displayedFoods) {
			if(name.toLowerCase().equals(f.toString().toLowerCase())
					&& !(name.toLowerCase().equals(selectedFood.toString().toLowerCase()))) {
				System.out.println("This food (" + name + ") already exists");
				errorFound = true;
			}
		}
		//if there were no errors
		if(!errorFound){
			FoodItem newFood = new FoodItem(name, w, time);
			//display the edited food
			foodView.getItems().remove(selectedFood);
			foodView.getItems().add(newFood);
			displayedFoods.remove(selectedFood);
			displayedFoods.add(newFood);
			System.out.println("successfully edited " + name);
			return true;
		}
		return false;
	}
}