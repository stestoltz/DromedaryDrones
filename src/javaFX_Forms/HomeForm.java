package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javaClasses.GreedyAlgorithm;
import javaClasses.Location;
import javaClasses.Results;
import javaClasses.RoutingAlgorithm;
import javaClasses.Simulation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
		
		MenuItem mapItem = menu.getItems().get(0);
		
		mapItem.setOnAction((event)->{
			this.sc.switchToMap();
		});
		
		MenuItem foodItem = menu.getItems().get(1);
		
		foodItem.setOnAction((event)->{
			this.sc.switchToFood();
		});
		
		MenuItem mealItem = menu.getItems().get(2);
		
		mealItem.setOnAction((event)->{
			this.sc.switchToMeal();
		});
		
		MenuItem droneItem = menu.getItems().get(4);
		
		droneItem.setOnAction((event)->{
			this.sc.switchToDrone();
		});
		
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button startSimulation = ((Button) bottom.getCenter());
		
		startSimulation.setOnAction((event) -> {
			this.sc.switchToResults();
		});
		
	}

}
