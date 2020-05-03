package javaFX_Forms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javaClasses.Location;
import javaFX_Styling.StyleButton;
import javaFX_Styling.StyleLabel;
import javaFX_Styling.StyleMenu;
import javaFX_Styling.StyleTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeForm extends Form {

	private FileChooser chooser;

	private Label locationName;

	public HomeForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		super(sc, layout);

		//file chooser that is used to saving and uploading location files
		chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt", "*.txt"));

		//create the buttons and text to be used in the middle of the home form
		locationName = new Label("Current Location: " + this.sc.getLocation().getName());
		locationName.setFont(Font.font("Verdana", 20));
		locationName.setAlignment(Pos.CENTER);

		Button changeName = new StyleButton("Change Location Name");
		Button changeLocation = new StyleButton("Change Location");
		Button saveLocation = new StyleButton("Save Location");

		changeName.setPrefSize(200, 50);
		changeLocation.setPrefSize(200, 50);
		saveLocation.setPrefSize(200, 50);

		//create the logo
		ImageView logo = new ImageView(this.sc.getLogo());
		logo.setPreserveRatio(true);
		logo.setFitWidth(300);

		//put the buttons in a VBox
		VBox rightSide = new VBox(20);
		rightSide.getChildren().addAll(changeName, changeLocation, saveLocation);
		rightSide.setAlignment(Pos.CENTER);

		//put the logo next to the buttons in an HBox
		HBox logoAndButtons = new HBox(100);
		logoAndButtons.getChildren().addAll(logo, rightSide);
		logoAndButtons.setAlignment(Pos.CENTER);

		//put the location name above all of that
		VBox middle = new VBox(25);
		middle.getChildren().addAll(logoAndButtons, locationName);
		middle.setAlignment(Pos.CENTER);

		layout.setCenter(middle);

		BorderPane top = (BorderPane) layout.getTop();
		MenuBar menuBar = (MenuBar) top.getRight();
		Menu menu = new Menu();
		menu = menuBar.getMenus().get(0);


		Image settingsIcon = new Image(new FileInputStream("res/settings.png"));
		ImageView settings = new ImageView(settingsIcon);
		settings.setFitHeight(50);
		settings.setFitWidth(50);
		menu.setGraphic(settings);

		MenuItem mapItem = menu.getItems().get(0);
		Image mapIcon = new Image(new FileInputStream("res/map.png"));
		ImageView map2 = new ImageView(mapIcon);
		map2.setFitHeight(50);
		map2.setFitWidth(50);
		mapItem.setGraphic(map2);

		mapItem.setOnAction((event)->{
			this.sc.switchToMap();
		});

		MenuItem foodItem = menu.getItems().get(1);
		Image foodIcon = new Image(new FileInputStream("res/food.png"));
		ImageView food = new ImageView(foodIcon);
		food.setFitHeight(50);
		food.setFitWidth(50);
		foodItem.setGraphic(food);

		foodItem.setOnAction((event)->{
			this.sc.switchToFood();
		});

		MenuItem mealItem = menu.getItems().get(2);
		Image mealIcon = new Image(new FileInputStream("res/meal.png"));
		ImageView meal = new ImageView(mealIcon);
		meal.setFitHeight(50);
		meal.setFitWidth(50);
		mealItem.setGraphic(meal);

		mealItem.setOnAction((event)->{
			this.sc.switchToMeal();
		});

		MenuItem shiftItem = menu.getItems().get(3);
		Image shiftIcon = new Image(new FileInputStream("res/shift.png"));
		ImageView shift = new ImageView(shiftIcon);
		shift.setFitHeight(50);
		shift.setFitWidth(50);
		shiftItem.setGraphic(shift);

		shiftItem.setOnAction((event)->{
			this.sc.switchToShifts();
		});

		MenuItem droneItem = menu.getItems().get(4);
		Image droneIcon = new Image(new FileInputStream("res/drone.png"));
		ImageView drone = new ImageView(droneIcon);
		drone.setFitHeight(50);
		drone.setFitWidth(50);
		droneItem.setGraphic(drone);

		droneItem.setOnAction((event)-> {
			this.sc.switchToDrone();
		});


		BorderPane bottom = (BorderPane) layout.getBottom();
		Button startSimulation = (Button) bottom.getCenter();

		startSimulation.setOnAction((event) -> {
			if (this.sc.getLocation().getMeals().isEmpty()) {
				this.sc.runErrorPopUp("There needs to be at least one meal to run the simulation.");
			} else if (this.sc.getLocation().getFoods().isEmpty()) {
				this.sc.runErrorPopUp("There needs to be at least one food to run the simulation.");
			} else if (this.sc.getLocation().getDeliveryPoints().isEmpty()) {
				this.sc.runErrorPopUp("There needs to be at least one delivery point to run the simulation.");
			} else {
				this.sc.switchToResults();
			}
		});

		changeName.setOnAction((event) -> {
			changeLocationName();
		});

		changeLocation.setOnAction((event) -> {
			changeLocation();
		});

		saveLocation.setOnAction((event) -> {
			saveLocation();
		});
	}

	/**
	 * 
	 */
	public void loadHomeForm(Location location) {
		locationName.setText("Current Location: " + location.getName());
	}


	/**
	 * this method allows the user to change the name of the location through 
	 * 		a pop up
	 */
	private void changeLocationName() {
		Label locationName = new Label("Location Name: ");
		TextField textLocationName = new TextField(this.sc.getLocation().getName());
		GridPane popUpPane = new GridPane();
		popUpPane.setAlignment(Pos.CENTER);

		Button save = new Button("Save");
		save.setAlignment(Pos.CENTER);

		HBox editLocation = new HBox();
		editLocation.getChildren().addAll(locationName, textLocationName);

		VBox popUpColumn = new VBox(20);
		popUpColumn.getChildren().addAll(editLocation, save);
		popUpColumn.setAlignment(Pos.CENTER);

		popUpPane.addColumn(0, popUpColumn);
		Scene popUpScene = new Scene(popUpPane,300,100);
		Stage namePopUp = new Stage();
		namePopUp.setScene(popUpScene);
		namePopUp.initModality(Modality.APPLICATION_MODAL);

		save.setOnAction((event) -> {
			this.sc.getLocation().setName(textLocationName.getText());

			namePopUp.close();
			this.sc.switchToHome();
		});

		namePopUp.showAndWait();
	}

	/**
	 * this method gives a pop up for the user to choose to upload a file or 
	 * 		to file in prompted information through the GUI
	 */
	private void changeLocation() {
		Label directions = new StyleLabel("Please select whether you would like to upload a location from"
				+ " a save file or enter information for a new location through the system.");
		directions.setWrapText(true);
		directions.setTextAlignment(TextAlignment.CENTER);

		Button uploadLocation = new StyleButton("Upload Location");
		Button enterInformation = new StyleButton("Enter Location Information");
		uploadLocation.setPrefWidth(250);
		enterInformation.setPrefWidth(250);

		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(uploadLocation, enterInformation);
		buttons.setAlignment(Pos.CENTER);

		VBox allItems = new VBox(30);
		allItems.getChildren().addAll(directions, buttons);

		GridPane changePane = new GridPane();
		changePane.setAlignment(Pos.CENTER);

		changePane.addColumn(0, allItems);
		Scene popUpScene = new Scene(changePane, 600, 250);
		Stage change = new Stage();
		change.setScene(popUpScene);
		change.setTitle("Change Location");
		change.initModality(Modality.APPLICATION_MODAL);

		uploadLocation.setOnAction((event) -> {
			change.close();
			uploadFile();
		}); 

		enterInformation.setOnAction((event) -> {
			change.close();
			newLocationGUI();
		});

		change.showAndWait();
	}

	/**
	 * this method saves the location to an object file
	 */
	private void saveLocation() {
		chooser.setTitle("Choose a Save Location");
		File saveFile = chooser.showSaveDialog(sc.getStage());

		if (saveFile != null) {
			String filepath = saveFile.getAbsolutePath();
			try {
				FileOutputStream fileOut = new FileOutputStream(filepath);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(this.sc.getLocation());

				//close files
				objectOut.close();
				fileOut.close();

				this.sc.runErrorPopUp("Location saved to file successfully!");

			} catch (Exception ex) {
				this.sc.runErrorPopUp("There was a problem saving the file.");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * this method lets the user upload a file to change the location object
	 * then the location object details are all updated to match the file
	 * @throws FileNotFoundException 
	 */
	private void uploadFile()
	{
		chooser.setTitle("Choose Location File");
		File file = chooser.showOpenDialog(null);

		if (file != null) {
			try {
				FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);

				Object obj = objectIn.readObject();
				this.sc.setLocation((Location) obj);

				//close streams
				fileIn.close();
				objectIn.close();

				this.sc.runErrorPopUp("Location file uploaded successfully!");
				this.sc.switchToHome();

			} catch (Exception ex) {
				this.sc.runErrorPopUp("There was a problem loading the file.");
				ex.printStackTrace();
			}
		}
	} 

	private void newLocationGUI() {
		//vbox for the whole pop up
		VBox newLocationList = new VBox(15);
		newLocationList.setAlignment(Pos.CENTER);
		newLocationList.setPadding(new Insets(15, 15, 15, 15));
		
		//title for the pop up
		Label header = new Label("New Location");
		header.setFont(new Font("Verdana", 35));
		header.setTextAlignment(TextAlignment.CENTER);
		header.setTextFill(Color.web("Orange"));
		
		Label description = new StyleLabel("Please enter all values below. Some information, such as "
				+ "delivery points will be entered through the settings menus instead of this form.");
		description.setAlignment(Pos.CENTER);
		description.setWrapText(true);
		
		//create all of the user input values ///////////////////////////////////
		
		//name
		Label name = new StyleLabel("Location Name: ");
		TextField txtName = new StyleTextField();
		txtName.setDisable(false);
		name.setMaxWidth(300);
		HBox.setHgrow(name, Priority.ALWAYS);
		HBox nameLine = new HBox(name, txtName);
		
		//cargo weight
		Label cargoWeight = new StyleLabel("Cargo weight (lb): ");
		TextField txtCargoWeight = new StyleTextField();
		txtCargoWeight.setDisable(false);
		cargoWeight.setMaxWidth(300);
		HBox.setHgrow(cargoWeight,Priority.ALWAYS);
		HBox cargoWeightLine = new HBox(cargoWeight, txtCargoWeight);
		
		//cruise speed
		Label cruiseSpeed = new StyleLabel("Average cruising speed (mph): ");
		TextField txtCruisingSpeed = new StyleTextField();
		txtCruisingSpeed.setDisable(false);
		cruiseSpeed.setMaxWidth(300);
		HBox.setHgrow(cruiseSpeed,Priority.ALWAYS);
		HBox cruiseSpeedLine = new HBox(cruiseSpeed, txtCruisingSpeed);

		//max flight time
		Label maxFlightTime = new StyleLabel("Maximum flight time (min): ");
		TextField txtMaxFlightTime = new StyleTextField();
		maxFlightTime.setMaxWidth(300);
		HBox.setHgrow(maxFlightTime,Priority.ALWAYS);
		HBox maxFlightTimeLine = new HBox(maxFlightTime, txtMaxFlightTime);

		//turn around time
		Label turnAroundTime = new StyleLabel("Turn around time (min): ");
		TextField txtTurnAroundTime = new StyleTextField();
		turnAroundTime.setMaxWidth(300);
		HBox.setHgrow(turnAroundTime,Priority.ALWAYS);
		HBox turnAroundTimeLine = new HBox(turnAroundTime, txtTurnAroundTime);

		//delivery time
		Label deliveryTime = new StyleLabel("Delivery time (s): ");
		TextField txtDeliveryTime = new StyleTextField();
		deliveryTime.setMaxWidth(300);
		HBox.setHgrow(deliveryTime,Priority.ALWAYS);
		HBox deliveryTimeLine = new HBox(deliveryTime, txtDeliveryTime);
		
		//restricted cargo weight
		Label userSpecifiedWeight = new StyleLabel("Restricted cargo weight (lb): ");
		TextField txtUserSpecifiedWeight = new StyleTextField();
		userSpecifiedWeight.setMaxWidth(300);
		HBox.setHgrow(userSpecifiedWeight,Priority.ALWAYS);
		HBox userSpecifiedWeightLine = new HBox(userSpecifiedWeight, txtUserSpecifiedWeight);
		
		//number of drones
		Label numberOfDrones = new StyleLabel("Number of Drones: ");
		TextField txtNumberOfDrones = new StyleTextField();
		numberOfDrones.setMaxWidth(300);
		HBox.setHgrow(numberOfDrones,Priority.ALWAYS);
		HBox numberOfDronesLine = new HBox(numberOfDrones, txtNumberOfDrones);
		
		//number of shifts
		Label numShifts = new StyleLabel("Number of Shifts: ");
		TextField txtNumShifts = new StyleTextField();
		numShifts.setMaxWidth(300);
		HBox.setHgrow(numShifts, Priority.ALWAYS);
		HBox numShiftsLine = new HBox(numShifts, txtNumShifts);
		
		//shift hours
		Label shiftHours = new StyleLabel("Hours in a Shift: ");
		TextField txtShiftHours = new StyleTextField();
		shiftHours.setMaxWidth(300);
		HBox.setHgrow(shiftHours, Priority.ALWAYS);
		HBox shiftHoursLine = new HBox(shiftHours, txtShiftHours);
		// end of user input values //////////////////////////////////////////////////////
		
		
		//scroller for all of the inputs
		ArrayList<HBox> boxList = new ArrayList<>();
		boxList.add(nameLine);
		boxList.add(cargoWeightLine);
		boxList.add(cruiseSpeedLine);
		boxList.add(maxFlightTimeLine);
		boxList.add(turnAroundTimeLine);
		boxList.add(deliveryTimeLine);
		boxList.add(userSpecifiedWeightLine);
		boxList.add(numberOfDronesLine);
		boxList.add(numShiftsLine);
		boxList.add(shiftHoursLine);
		
		ObservableList<HBox> inputList = FXCollections.<HBox>observableArrayList(boxList);
		
		ListView<HBox> scroller = new ListView<>(inputList);
		
		scroller.setOrientation(Orientation.VERTICAL);
		scroller.setPrefWidth(350);
	    
		//create the buttons for the bottom
		Button createLocation = new StyleButton("Create Location");
		createLocation.setPrefWidth(150);
		
		Button cancel = new StyleButton("Cancel");
		
		HBox buttons = new HBox(40);
		buttons.getChildren().addAll(cancel, createLocation);
		
		//put everything in the vbox for the whole window
		newLocationList.getChildren().addAll(
				header,
				description,
				scroller,
				buttons);
		
		//create the actual pop up
		Scene newLocationScene = new Scene(newLocationList, 600, 400);
		Stage newLocationPopUp = new Stage();
		newLocationPopUp.setScene(newLocationScene);
		newLocationPopUp.initModality(Modality.APPLICATION_MODAL);
		
		createLocation.setOnAction((event) -> {
			//verifyLocationInputs()
			
			this.sc.runErrorPopUp("All shifts will automatically have 15 meals "
					+ "per shift and that can be edited through the settings page");
			newLocationPopUp.close();
			
			//take the user to the map to add the home 
			//and other delivery points
			this.sc.switchToMap();
		});
		
		cancel.setOnAction((event) -> {;
			newLocationPopUp.close();
		});
		
		newLocationPopUp.showAndWait();
	}
}
