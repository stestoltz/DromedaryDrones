package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeForm extends Form {
	
	public HomeForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		super(sc, layout);
		
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
		
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button startSimulation = ((Button) bottom.getCenter());
		HBox editLocation = ((HBox) bottom.getLeft());
		Button changeName = ((Button) editLocation.getChildren().get(1));
		
		startSimulation.setOnAction((event) -> {
			this.sc.switchToResults();
		});
		
		changeName.setOnAction((event) -> {
			changeLocationName();
		});
	}
	
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

}
