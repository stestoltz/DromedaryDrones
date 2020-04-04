package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class HomePane extends BorderPane {
	
	private SceneController sc;
	
	public HomePane(SceneController sc) throws FileNotFoundException {
		super();
		
		this.sc = sc;
		
		//create map
		Image map = new Image(new FileInputStream("res/map.jfif"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitHeight(500);
		
		this.setCenter(mapView);
		
		BorderPane top = (BorderPane) this.getTop();
		MenuBar menuBar = (MenuBar) top.getRight();
		Menu menu = menuBar.getMenus().get(0);
		MenuItem droneItem = menu.getItems().get(4);
		
		droneItem.setOnAction((event)->{
			sc.switchToDrone();
		});
	}

}
