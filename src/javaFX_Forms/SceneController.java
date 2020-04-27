package javaFX_Forms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javaClasses.DeliveryPoint;
import javaClasses.Drone;
import javaClasses.FoodItem;
import javaClasses.Location;
import javaClasses.Meal;
import javaClasses.ShiftDetails;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SceneController {
	
	private Location location;
	
	private Stage stage;

	private Image logo;
	
	private HomeForm homeForm;
	private DroneForm droneForm;
	private FoodForm foodForm;
	private MealForm mealForm;
	private MapForm mapForm;
	private ShiftSettingsForm shiftForm;
	private SimulationResultsForm resultsForm;
	
	private FileChooser chooser;
	
	public SceneController(Stage stage) throws Exception {
		location = new Location("Grove City", "SAC");
		
		this.stage = stage;
		
		logo = new Image(new FileInputStream("res/Temp_logo.jpg"));
		
		homeForm = new HomeForm(this, buildHomeBorderPane());
		droneForm = new DroneForm(this, buildSettingsBorderPane("Drone Settings"));
		foodForm = new FoodForm(this, buildSettingsBorderPane("Food Settings"));
		mealForm = new MealForm(this, buildSettingsBorderPane("Meal Settings"));
		mapForm = new MapForm(this, buildSettingsBorderPane("Map Settings"));

		shiftForm = new ShiftSettingsForm(this, buildSettingsBorderPane("Shift Settings"));

		resultsForm = new SimulationResultsForm(this, buildResultsBorderPane());
		
		Scene scene = new Scene(homeForm.getLayout());
		stage.setScene(scene);
		
		chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt", "*.txt"));
	}
	
	// each form has a getLayout() method to get its layout,
	//  a switchTo method to switch forms to it,
	//  and a replace(object) method to replace its data in Location
	
	public BorderPane getHomeLayout() {
		return homeForm.getLayout();
	}
	
	public BorderPane getDroneLayout() {
		return droneForm.getLayout();
	}
	
	public BorderPane getFoodLayout() {
		return foodForm.getLayout();
	}
	
	public BorderPane getMealLayout() {
		return mealForm.getLayout();
	}
	
	public BorderPane getMapLayout() {
		return mapForm.getLayout();
	}
	
	public BorderPane getShiftLayout() {
		return shiftForm.getLayout();
	}
	
	public BorderPane getResultsLayout() {
		return resultsForm.getLayout();
	}
	
	
	public void switchToHome() {
		stage.getScene().setRoot(getHomeLayout());
	}
	
	public void switchToDrone() {
		droneForm.loadDrone(location.getDrone());
		stage.getScene().setRoot(getDroneLayout());
	}
	
	public void switchToFood() {
		foodForm.loadFoods(location.getFoods(), location.getMeals(), location.getDrone());
		stage.getScene().setRoot(getFoodLayout());
	}
	
	public void switchToMeal() {
		mealForm.loadMeals(location.getMeals(), location.getFoods(), location.getDrone());
		stage.getScene().setRoot(getMealLayout());
	}
	
	public void switchToMap() {
		mapForm.loadPoints(location.getDeliveryPointsMap());
		stage.getScene().setRoot(getMapLayout());
	}
	
	public void switchToShifts() {
		shiftForm.loadShift(location.getShiftDetails());
		stage.getScene().setRoot(getShiftLayout());
	}
	
	public void switchToResults() {
		try {
			resultsForm.runSimulation(location);
			stage.getScene().setRoot(getResultsLayout());
		} catch (Exception e) {
			System.out.println("Results list was empty");
		}
	}
	
	
	public void replaceDrone(Drone d) {
		this.location.setDrone(d);
	}
	
	public void replaceFoods(List<FoodItem> foods) {
		this.location.setFoods(foods);
	}
	
	public void replaceMeals(List<Meal> meals) {
		this.location.setMeals(meals);
	}
	
	public void replaceDeliveryPoints(Map<DeliveryPoint, Boolean> points) {
		this.location.setDeliveryPoints(points);
	}
	
	public void replaceShift(ShiftDetails shift) {
		this.location.setShiftDetails(shift);
	}
	
	
	/**
	 * builds a settings border pane
	 * @param headerText
	 * @return
	 */
	public BorderPane buildSettingsBorderPane(String headerText) {
		BorderPane layout = buildBaseBorderPane(headerText);
		
		Button cancel = new Button("Cancel and Return");
		Button saveChanges = new Button("Save Changes");
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		bottom.setLeft(cancel);
		bottom.setRight(saveChanges);
		
		return layout;
	}
	
	/**
	 * builds the home border pane
	 * @return
	 */
	public BorderPane buildHomeBorderPane() {
		BorderPane layout = buildBaseBorderPane("Dromedary Drones");
		
		Button startSimulation = new Button("Start Simulation");
		Label loc = new Label("Location: " + location.getName());
		loc.setFont(Font.font("Comic Sans", FontWeight.BOLD, 20));
		Button changeName = new Button("Change Location Name");
		Button uploadLocation = new Button("Upload Location");
		Button saveLocation = new Button("Save Location");
		
		HBox editLocation = new HBox();
		editLocation.setSpacing(10);
		editLocation.getChildren().addAll(uploadLocation, saveLocation);
		
		HBox locationName = new HBox();
		locationName.setSpacing(10);
		locationName.getChildren().addAll(loc, changeName);
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		bottom.setCenter(startSimulation);
		bottom.setLeft(locationName);
		bottom.setRight(editLocation);
		
		uploadLocation.setOnAction((event) -> {
			changeLocation();
		});
		
		saveLocation.setOnAction((event) -> {
			saveLocation();
		});
		
		changeName.setOnAction((event) -> {
			changeLocationName();
		});
		
		//create a menubar for the hamburger menu
		MenuBar menuBar = new MenuBar();
		
		Menu menu1 = new Menu("Simulation Settings");
		menuBar.getMenus().add(menu1);
		MenuItem menuItem1 = new MenuItem("Modify Mapping");
		MenuItem menuItem2 = new MenuItem("Food Settings");
		MenuItem menuItem3 = new MenuItem("Meal Settings");
		MenuItem menuItem4 = new MenuItem("Shift Settings");
		MenuItem menuItem5 = new MenuItem("Drone Settings");

		menu1.getItems().add(menuItem1);
		menu1.getItems().add(menuItem2);
		menu1.getItems().add(menuItem3);
		menu1.getItems().add(menuItem4);
		menu1.getItems().add(menuItem5);
		
		BorderPane top = ((BorderPane) layout.getTop());
		top.setRight(menuBar);
		
		return layout;
	}
	
	/**
	 * builds the simulation results border pane
	 * @return
	 */
	private BorderPane buildResultsBorderPane() {
		
		BorderPane layout = buildBaseBorderPane("Simulation Results");
		
		Button rerun = new Button("Rerun Simulation");
		Button returnHome = new Button("Back");
		Button save = new Button("Save Results");
		
		rerun.setOnAction((event) -> {
			switchToResults();
		});
		
		returnHome.setOnAction((event) -> {
			switchToHome();
		});
		
		BorderPane bottom = new BorderPane();
		bottom.setCenter(rerun);
		bottom.setRight(returnHome);
		bottom.setLeft(save);
		layout.setBottom(bottom);
		
		return layout;
	}
	
	/**
	 * builds a border pane with all elements that all border panes share
	 * @return
	 */
	private BorderPane buildBaseBorderPane(String headerText) {
		
		BorderPane layout = new BorderPane();
		
		BorderPane top = new BorderPane();
		BorderPane bottom = new BorderPane();
		
		Label header = new Label(headerText);
		header.setFont(new Font("Comic Sans", 30));
		
		ImageView imageView = new ImageView(logo);
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(150);
		
		top.setLeft(imageView);
		top.setCenter(header);
		
		layout.setBottom(bottom);
		layout.setTop(top);
		
		// Set the padding of the layout
		layout.setStyle("-fx-padding: 10;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 5;" +
				"-fx-border-radius: 5;");
		
		return layout;
		
	}	
	
	/**
	 * this method lets the user upload a file to change the location object
	 * then the location object details are all updated to match the file
	 * @throws FileNotFoundException 
	 */
	private void changeLocation() {
		chooser.setTitle("Choose Location File");
		File file = chooser.showOpenDialog(null);
		
		if (file != null) {
			try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			Object obj = objectIn.readObject();
			location = (Location) obj;
			
			//close streams
			fileIn.close();
			objectIn.close();
			
			objectIn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	/**
	 * this method saves the location to an object file
	 */
	private void saveLocation() {
		chooser.setTitle("Choose a Save Location");
		File saveFile = chooser.showSaveDialog(stage);

		if (saveFile != null) {
			String filepath = saveFile.getAbsolutePath();
			try {
				FileOutputStream fileOut = new FileOutputStream(filepath);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(location);
				
				//close files
				objectOut.close();
				fileOut.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void changeLocationName() {
		Label locationName = new Label("Location Name: ");
		TextField textLocationName = new TextField(location.getName());
		GridPane popUpPane = new GridPane();
			
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
			location.setName(textLocationName.getText());
		
			namePopUp.close();
			buildHomeBorderPane();
		});
			
		namePopUp.showAndWait();
	}

}