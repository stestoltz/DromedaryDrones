package javaFX_Forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javaClasses.Drone;
import javaClasses.FoodItem;
import javaClasses.Location;
import javaClasses.Meal;
import javaClasses.ShiftDetails;
import javaFX_Styling.StyleButton;
import javaFX_Styling.StyleLabel;
import javaFX_Styling.StyleTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewLocationForm extends Form {
	
	private TextField txtLocationName;
	private TextField txtCargoWeight;
	private TextField txtCruisingSpeed;
	private TextField txtMaxFlightTime;
	private TextField txtTurnAroundTime;
	private TextField txtDeliveryTime;
	private TextField txtUserSpecifiedWeight;
	private TextField txtNumberOfDrones;
	private TextField txtNumShifts;
	private TextField txtShiftHours;
	private TextField txtFoodName;
	private TextField txtFoodWeight;
	private TextField txtFoodPrepTime;
	
	public NewLocationForm(SceneController sc, BorderPane layout) {
		super(sc, layout);

		Label description = new StyleLabel("Please enter all values below. Some information, such as "
				+ "delivery points will be entered through the settings menus instead of this form. "
				+ "Orders per hour will be automatically set to 15 orders for each hour but can be edited "
				+ "after the location is created. Please note that the simulation will use the location "
				+ "name to find the location on Google Maps.");
		description.setTextAlignment(TextAlignment.CENTER);
		description.setWrapText(true);

		//create all of the user input values ///////////////////////////////////

		//name
		Label name = new StyleLabel("Location Name: ");
		txtLocationName = new StyleTextField();
		name.setMaxWidth(250);
		HBox.setHgrow(name, Priority.ALWAYS);
		HBox nameLine = new HBox(name, txtLocationName);

		//cargo weight
		Label cargoWeight = new StyleLabel("Cargo weight (lb): ");
		txtCargoWeight = new StyleTextField();
		txtCargoWeight.setDisable(false);
		cargoWeight.setMaxWidth(250);
		HBox.setHgrow(cargoWeight,Priority.ALWAYS);
		HBox cargoWeightLine = new HBox(cargoWeight, txtCargoWeight);

		//cruise speed
		Label cruiseSpeed = new StyleLabel("Average cruising speed (mph): ");
		txtCruisingSpeed = new StyleTextField();
		txtCruisingSpeed.setDisable(false);
		cruiseSpeed.setMaxWidth(250);
		HBox.setHgrow(cruiseSpeed,Priority.ALWAYS);
		HBox cruiseSpeedLine = new HBox(cruiseSpeed, txtCruisingSpeed);

		//max flight time
		Label maxFlightTime = new StyleLabel("Maximum flight time (min): ");
		txtMaxFlightTime = new StyleTextField();
		maxFlightTime.setMaxWidth(250);
		HBox.setHgrow(maxFlightTime,Priority.ALWAYS);
		HBox maxFlightTimeLine = new HBox(maxFlightTime, txtMaxFlightTime);

		//turn around time
		Label turnAroundTime = new StyleLabel("Turn around time (min): ");
		txtTurnAroundTime = new StyleTextField();
		turnAroundTime.setMaxWidth(250);
		HBox.setHgrow(turnAroundTime,Priority.ALWAYS);
		HBox turnAroundTimeLine = new HBox(turnAroundTime, txtTurnAroundTime);

		//delivery time
		Label deliveryTime = new StyleLabel("Delivery time (s): ");
		txtDeliveryTime = new StyleTextField();
		deliveryTime.setMaxWidth(250);
		HBox.setHgrow(deliveryTime,Priority.ALWAYS);
		HBox deliveryTimeLine = new HBox(deliveryTime, txtDeliveryTime);

		//restricted cargo weight
		Label userSpecifiedWeight = new StyleLabel("Restricted cargo weight (lb): ");
		txtUserSpecifiedWeight = new StyleTextField();
		userSpecifiedWeight.setMaxWidth(250);
		HBox.setHgrow(userSpecifiedWeight,Priority.ALWAYS);
		HBox userSpecifiedWeightLine = new HBox(userSpecifiedWeight, txtUserSpecifiedWeight);

		//number of drones
		Label numberOfDrones = new StyleLabel("Number of Drones: ");
		txtNumberOfDrones = new StyleTextField();
		numberOfDrones.setMaxWidth(250);
		HBox.setHgrow(numberOfDrones,Priority.ALWAYS);
		HBox numberOfDronesLine = new HBox(numberOfDrones, txtNumberOfDrones);

		//number of shifts
		Label numShifts = new StyleLabel("Number of Shifts: ");
		txtNumShifts = new StyleTextField();
		numShifts.setMaxWidth(250);
		HBox.setHgrow(numShifts, Priority.ALWAYS);
		HBox numShiftsLine = new HBox(numShifts, txtNumShifts);

		//shift hours
		Label shiftHours = new StyleLabel("Hours in a Shift: ");
		txtShiftHours = new StyleTextField();
		shiftHours.setMaxWidth(250);
		HBox.setHgrow(shiftHours, Priority.ALWAYS);
		HBox shiftHoursLine = new HBox(shiftHours, txtShiftHours);
		
		//food name
		Label foodName = new StyleLabel("Food Name: ");
		txtFoodName = new StyleTextField();
		foodName.setMaxWidth(150);
		HBox.setHgrow(foodName, Priority.ALWAYS);
		HBox foodNameLine = new HBox(foodName, txtFoodName);
		
		//food weight
		Label foodWeight = new StyleLabel("Food Weight: ");
		txtFoodWeight = new StyleTextField();
		foodWeight.setMaxWidth(150);
		HBox.setHgrow(foodWeight, Priority.ALWAYS);
		HBox foodWeightLine = new HBox(foodWeight, txtFoodWeight);
		
		//food prep time
		Label foodPrepTime = new StyleLabel("Food Prep Time: ");
		txtFoodPrepTime = new StyleTextField();
		foodPrepTime.setMaxWidth(150);
		HBox.setHgrow(foodPrepTime, Priority.ALWAYS);
		HBox foodPrepTimeLine = new HBox(foodPrepTime, txtFoodPrepTime);
		
		// end of user input values //////////////////////////////////////////////////////


		//scroller for all of the left side inputs
		ArrayList<HBox> leftSideList = new ArrayList<>();
		leftSideList.add(nameLine);
		leftSideList.add(cargoWeightLine);
		leftSideList.add(cruiseSpeedLine);
		leftSideList.add(maxFlightTimeLine);
		leftSideList.add(turnAroundTimeLine);
		leftSideList.add(deliveryTimeLine);
		leftSideList.add(userSpecifiedWeightLine);
		leftSideList.add(numberOfDronesLine);
		leftSideList.add(numShiftsLine);
		leftSideList.add(shiftHoursLine);

		ObservableList<HBox> leftInputList = FXCollections.<HBox>observableArrayList(leftSideList);

		ListView<HBox> scroller = new ListView<>(leftInputList);

		scroller.setOrientation(Orientation.VERTICAL);
		scroller.setPrefHeight(250);
		scroller.setMinWidth(400);

		//pull the buttons and change 'save' to 'create location'
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button createLocation = ((Button) bottom.getRight());
		
		createLocation.setText("Create Location");
		createLocation.setPrefWidth(150);

		//create a description of what will happen with the food
		Label foodDescription = new StyleLabel("Please enter one food item. A meal of that food "
				+ "will automatically be created and other foods and meals can be added on their "
				+ "respective settings screens.");
		foodDescription.setWrapText(true);
		foodDescription.setTextAlignment(TextAlignment.CENTER);
		foodDescription.setMaxWidth(300);
		
		//put the food items in a Vbox
				VBox foodEdits = new VBox(10);
				foodEdits.getChildren().addAll(
						foodNameLine,
						foodWeightLine,
						foodPrepTimeLine,
						foodDescription
						);
			
		//put the edit items in an HBox
		HBox editors = new HBox(40);
		editors.getChildren().addAll(scroller, foodEdits);
		editors.setAlignment(Pos.CENTER);
		
		//put everything in a VBox
		VBox centerBox = new VBox(20);
		centerBox.getChildren().addAll(description, editors);
		centerBox.setAlignment(Pos.CENTER);
		
		layout.setCenter(centerBox);

		createLocation.setOnAction((event) -> {
			//verify location will let the user know of any problems with their inputs
			//this also sets up the location with all of the information if it is correct
			Location newLocation = createLocation();
					
			//if all inputs are correct then take the user to the next step of creating a location
			if(newLocation != null) {
				this.sc.setLocation(newLocation);

				//take the user to the map to add the home 
				//and other delivery points
				this.sc.switchToNewMap(txtLocationName.getText());
				this.sc.runErrorPopUp("Please enter map information for your new location. "
						+ "The home point has been created for you but its location can be moved "
						+ "by dragging the point around the map.");
			}
			
		});

		cancel.setOnAction((event) -> {;
			this.sc.switchToHome();
		});
	}
	
	private Location createLocation() {
		
		//start with the location being the same, this will be overwritten
		Location newLocation = new Location(this.sc.getLocation());
		
		//pull location name and check to make sure it has info
		String name = txtLocationName.getText();
		if (name.equals("")) {
			this.sc.runErrorPopUp("The location name cannot be empty.");
			return null;
		}
		
		newLocation.setName(name);
		
		try {
			//pull drone information
			double cargoWeight = Double.parseDouble(txtCargoWeight.getText());
			double cruisingSpeed = Double.parseDouble(txtCruisingSpeed.getText());
			double maxFlightTime = Double.parseDouble(txtMaxFlightTime.getText());
			double turnAroundTime = Double.parseDouble(txtTurnAroundTime.getText());
			double deliveryTime = Double.parseDouble(txtDeliveryTime.getText());
			double userSpecifiedWeight = Double.parseDouble(txtUserSpecifiedWeight.getText());
			int numberOfDrones = Integer.parseInt(txtNumberOfDrones.getText());
			
			//check drone values
			if (userSpecifiedWeight > cargoWeight) {
				this.sc.runErrorPopUp("The restricted drone weight must be less than or equal to the max cargo weight of the drone.");
				return null;
			}
			
			if (userSpecifiedWeight <= 0 || cargoWeight <= 0) {
				this.sc.runErrorPopUp("Drone cargo weight and restricted cargo weight must be above zero.");
				return null;
			}
			
			if (numberOfDrones <= 0) {
				this.sc.runErrorPopUp("There must be at least one drone to run a simulation.");
				return null;
			}
		
			Drone d;
			
			if (cargoWeight < 0 ||
				cruisingSpeed < 0  ||
				maxFlightTime < 0 ||
				turnAroundTime < 0 || 
				deliveryTime < 0 ||
				userSpecifiedWeight < 0 ||
				numberOfDrones < 0)
			{
				this.sc.runErrorPopUp("All values in the drone form must be positive values.");
				return null;
			} else {
				d = new Drone(
						cargoWeight, 
						cruisingSpeed, 
						maxFlightTime, 
						turnAroundTime, 
						deliveryTime, 
						userSpecifiedWeight);
			}
			
			//set the drone information to the new location
			newLocation.setDrone(d);
			newLocation.setNumberOfDrones(numberOfDrones);
			
			//pull shift information
			int numShifts = Integer.parseInt(txtNumShifts.getText());
			int shiftHours = Integer.parseInt(txtShiftHours.getText());
			
			if (numShifts <= 0) {
				this.sc.runErrorPopUp("The number of shifts must be greater than zero to create a location.");
				return null;
			}
			
			if (shiftHours <= 0) {
				this.sc.runErrorPopUp("The hours in a shift must be greater than zero to create a location.");
				return null;
			}
			
			//set shift information to location
			List<Integer> ordersPerHour = new ArrayList<>();
			for (int i=0; i<shiftHours; i++) {
				ordersPerHour.add(15);
			}
			ShiftDetails shiftDetails = new ShiftDetails(numShifts, shiftHours, ordersPerHour);
			newLocation.setShiftDetails(shiftDetails);
			
			//pull food information
			String foodName = txtFoodName.getText();
			double foodWeight = Double.parseDouble(txtFoodWeight.getText());
			double foodPrepTime = Double.parseDouble(txtFoodPrepTime.getText());
			
			if (foodName.equals("")) {
				this.sc.runErrorPopUp("The food must have a name to create a new location.");
				return null;
			}
			
			if (foodWeight <= 0) {
				this.sc.runErrorPopUp("Food weight must be greater than zero.");
				return null;
			}
			
			if (foodWeight > cargoWeight) {
				this.sc.runErrorPopUp("Food weight cannot be more than drone cargo weight.");
				return null;
			}
			
			if (foodPrepTime < 0) {
				this.sc.runErrorPopUp("Food prep time cannot be negative.");
				return null;
			}
			
			//create the food and meal information
			List<FoodItem> foods = new ArrayList<>();
			foods.add(new FoodItem(foodName, foodWeight, foodPrepTime));
			List<Meal> meals = new ArrayList<>();
			HashMap<FoodItem, Integer> meal = new HashMap<>();
			meal.put(new FoodItem(foodName, foodWeight, foodPrepTime), 1);
			meals.add(new Meal(meal, 100));
			
			//set that info to location
			newLocation.setFoods(foods);
			newLocation.setMeals(meals);
			
		} catch (Exception e) {
			this.sc.runErrorPopUp("All inputs must be in a valid format.");
			return null;
		}
		
		return newLocation;
	}
}
