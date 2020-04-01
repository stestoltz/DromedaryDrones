package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage stage;

	private Image logo;
	
	private Scene homeScene;
	private Scene droneScene;
	/*
	private Scene foodScene;
	private Scene mealScene;
	 */
	
	public SceneController(Stage stage) throws FileNotFoundException {
		this.stage = stage;
		
		logo = new Image(new FileInputStream("res/Temp_logo.jpg"));
		
		homeScene = new HomeScene(this, buildHomeBorderPane());
		droneScene = new DroneScene(this, buildSettingsBorderPane("Drone Settings"));
	}
	
	public Scene getHomeScene() {
		return homeScene;
	}
	
	public Scene getDroneScene() {
		return droneScene;
	}
	
	public void switchToHome() {
		stage.setScene(homeScene);
		stage.setMaximized(true);
	}
	
	public void switchToDrone() {
		stage.setScene(droneScene);
		stage.setMaximized(true);
	}
	
	/**
	 * builds a settings border pane
	 * @param headerText
	 * @return
	 */
	public BorderPane buildSettingsBorderPane(String headerText) {
		BorderPane layout = buildBaseBorderPane(headerText);
		
		Button delete = new Button("Delete");
		Button saveChanges = new Button("Save Changes");
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		bottom.setLeft(delete);
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
		MenuItem menuItem3 = new MenuItem("Order Settings");
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
