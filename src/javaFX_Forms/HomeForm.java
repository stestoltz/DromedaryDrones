package javaFX_Forms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javaClasses.Location;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeForm extends Form {
	
	private FileChooser chooser;
	
	private Label locationNameLabel;
	
	public HomeForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		super(sc, layout);
		
		chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt", "*.txt"));
		
		//create map
		Image map = new Image(new FileInputStream("res/map.jfif"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitHeight(500);
		
		layout.setCenter(mapView);
		
		BorderPane top = (BorderPane) layout.getTop();
		MenuBar menuBar = (MenuBar) top.getRight();
		Menu menu = menuBar.getMenus().get(0);
		
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
		Image foodIcon = new Image(new FileInputStream("res/food.jpg"));
		ImageView food = new ImageView(foodIcon);
		food.setFitHeight(50);
		food.setFitWidth(50);
		foodItem.setGraphic(food);
		
		foodItem.setOnAction((event)->{
			this.sc.switchToFood();
		});
		
		MenuItem mealItem = menu.getItems().get(2);
		Image mealIcon = new Image(new FileInputStream("res/meal.jpg"));
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
		HBox locationName = (HBox) bottom.getLeft();
		Button changeName = (Button) locationName.getChildren().get(1);
		locationNameLabel = (Label) locationName.getChildren().get(0);
		
		HBox editLocation = ((HBox) bottom.getRight());
		Button changeLocation = ((Button) editLocation.getChildren().get(0));
		Button saveLocation = ((Button) editLocation.getChildren().get(1));
		
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
		locationNameLabel.setText("Location: " + location.getName());
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
			
		HBox editLocation = new HBox();
		editLocation.getChildren().addAll(locationName, textLocationName);
			
		VBox popUpColumn = new VBox();
		popUpColumn.getChildren().addAll(editLocation, save);
			
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
		Label directions = new Label("Please select whether you would like to upload a location from"
				+ " a save file or enter information for a new location through the system.");
		directions.setWrapText(true);
		directions.setTextAlignment(TextAlignment.CENTER);
		
		Button uploadLocation = new Button("Upload Location");
		Button enterInformation = new Button("Enter Location Information");
		
		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(uploadLocation, enterInformation);
		buttons.setAlignment(Pos.CENTER);
		
		VBox allItems = new VBox(30);
		allItems.getChildren().addAll(directions, buttons);
		
		GridPane changePane = new GridPane();
		changePane.setAlignment(Pos.CENTER);
		
		changePane.addColumn(0, allItems);
		Scene popUpScene = new Scene(changePane, 350, 150);
		Stage change = new Stage();
		change.setScene(popUpScene);
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
		
	}
}
