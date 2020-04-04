package javaFX_Forms;

import java.awt.Event;
import java.io.FileInputStream;

import javaClasses.DeliveryPoint;
import javaClasses.Location;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MapForm extends Application {
	
	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//create logo
		Image image = new Image(new FileInputStream("res/Temp_logo.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(150);

		//create map
		Image map = new Image(new FileInputStream("res/map2.jpg"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitWidth(900);
		
		mapView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			System.out.println(event.getX() + " " + event.getY());
		});
		
		Location bogus = generateBogusLocation();
		
		ComboBox<Location> locations = new ComboBox<>();
		locations.getItems().add(bogus);
		locations.setValue(bogus);
		
		ListView<DeliveryPoint> points = new ListView<>();
		
		for (DeliveryPoint dp : bogus.getDeliveryPoints()) {
			points.getItems().add(dp);
		}

		//set title
		stage.setTitle("Modify Mappings"); 

		//create buttons and header
		Button returnToMenu = new Button("Return to Menu");
		Button saveChanges = new Button("Save Changes");
		Label header = new Label("Modify Mappings");
		header.setFont(new Font("Comic Sans", 30));

		//create pane and add buttons to bottom corners
		BorderPane bottom = new BorderPane();
		bottom.setLeft(returnToMenu);
		bottom.setRight(saveChanges);
		//do stuff

		//create pane and add images
		BorderPane top = new BorderPane();
		top.setLeft(imageView);
		top.setCenter(header);
		
		HBox listButtons = new HBox();
		Button edit = new Button("Edit");
		Button delete = new Button("Delete");
		listButtons.getChildren().add(edit);
		listButtons.getChildren().add(delete);
		
		delete.setOnAction((event) -> {
			DeliveryPoint selectedPoint = points.getSelectionModel().getSelectedItem();
			Location selectedLocation = locations.getSelectionModel().getSelectedItem();
			
			//if (selectedLocation.getDeliveryPoints().contains(selectedPoint)) {
			if (selectedPoint != null) {
				selectedLocation.deletePoint(selectedPoint);
				points.getItems().remove(selectedPoint);
			}
		});
		
		VBox left = new VBox();
		left.getChildren().add(locations);
		left.getChildren().add(points);
		left.getChildren().add(listButtons);
		

		// Create the pane and add the other panes
		BorderPane layout = new BorderPane();
		layout.setBottom(bottom);
		layout.setTop(top);
		layout.setCenter(mapView);
		layout.setLeft(left);
		
		layout.setPadding(new Insets(10, 10, 10, 10));
		left.setPadding(new Insets(10, 10, 10, 10));
		left.setSpacing(10);
		listButtons.setSpacing(10);
		top.setPadding(new Insets(10, 10, 10, 10));
		bottom.setPadding(new Insets(10, 10, 10, 10));
		
		
		Scene scene = new Scene(layout);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// maximize screen and display the Stage
		stage.setMaximized(true);
		stage.show();
	}
	
	public Location generateBogusLocation() {
		Location location = new Location("Bogus", "SAC");
		
		location.addPoint(new DeliveryPoint("HAL", 25, 0));
		location.addPoint(new DeliveryPoint("Hoyt", -25, -30));
		location.addPoint(new DeliveryPoint("PLC", -25, 30));
		location.addPoint(new DeliveryPoint("STEM", -40, 0));
		location.addPoint(new DeliveryPoint("Rockwell", -50, 0));
		location.addPoint(new DeliveryPoint("Crawford", -75, -30));
		
		return location;
	}
}