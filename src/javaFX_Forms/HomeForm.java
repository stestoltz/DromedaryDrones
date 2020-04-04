package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class HomeForm extends Form {
	
	private SceneController sc;
	private BorderPane layout;
	
	public HomeForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		this.layout = layout;
		this.sc = sc;
		
		//create map
		Image map = new Image(new FileInputStream("res/map.jfif"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitHeight(500);
		
		layout.setCenter(mapView);
		
		BorderPane top = (BorderPane) layout.getTop();
		MenuBar menuBar = (MenuBar) top.getRight();
		Menu menu = menuBar.getMenus().get(0);
		
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
	}

	@Override
	public BorderPane getLayout() {
		return layout;
	}

}
