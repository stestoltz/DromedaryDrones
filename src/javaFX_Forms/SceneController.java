package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javaClasses.DeliveryPoint;
import javaClasses.Drone;
import javaClasses.FoodItem;
import javaClasses.Location;
import javaClasses.Meal;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
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
	
	public SceneController(Stage stage) throws FileNotFoundException {
		location = new Location("Grove City", "SAC");
		
		this.stage = stage;
		
		logo = new Image(new FileInputStream("res/Temp_logo.jpg"));
		
		homeForm = new HomeForm(this, buildHomeBorderPane());
		droneForm = new DroneForm(this, buildSettingsBorderPane("Drone Settings"));
		foodForm = new FoodForm(this, buildSettingsBorderPane("Food Settings"));
		mealForm = new MealForm(this, buildSettingsBorderPane("Meal Settings"));
		mapForm = new MapForm(this, buildSettingsBorderPane("Map Settings"));
		
		Scene scene = new Scene(homeForm.getLayout());
		stage.setScene(scene);
	}
	
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
	
	public void switchToHome() {
		stage.getScene().setRoot(getHomeLayout());
	}
	
	public void switchToDrone() {
		droneForm.loadDrone(location.getDrone());
		stage.getScene().setRoot(getDroneLayout());
	}
	
	public void switchToFood() {
		foodForm.loadFoods(location.getFoods());
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
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		bottom.setCenter(startSimulation);
		
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
	
}