package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javaClasses.DeliveryPoint;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MapForm extends Form {
	
	private ListView<DeliveryPoint> pointsView;
	
	private Map<DeliveryPoint, Boolean> points;
	
	public MapForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		super(sc, layout);
		
		pointsView = new ListView<>();

		//create map
		Image map = new Image(new FileInputStream("res/map2.jpg"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitWidth(900);
		
		mapView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			System.out.println(event.getX() + " " + event.getY());
		});
		
		HBox listButtons = new HBox();
		Button edit = new Button("Edit");
		Button delete = new Button("Delete");
		listButtons.getChildren().add(edit);
		listButtons.getChildren().add(delete);
		
		delete.setOnAction((event) -> {
			DeliveryPoint selectedPoint = pointsView.getSelectionModel().getSelectedItem();
			
			//if (selectedLocation.getDeliveryPoints().contains(selectedPoint)) {
			if (selectedPoint != null) {
				points.remove(selectedPoint);
				pointsView.getItems().remove(selectedPoint);
			}
		});
		
		VBox left = new VBox();
		left.getChildren().add(pointsView);
		left.getChildren().add(listButtons);
		
		layout.setCenter(mapView);
		layout.setLeft(left);
		
		layout.setPadding(new Insets(10, 10, 10, 10));
		left.setPadding(new Insets(10, 10, 10, 10));
		left.setSpacing(10);
		listButtons.setSpacing(10);
		
		
		BorderPane top = ((BorderPane) layout.getTop());
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());
		
		top.setPadding(new Insets(10, 10, 10, 10));
		bottom.setPadding(new Insets(10, 10, 10, 10));
		
		cancel.setOnAction((event) -> {
			this.sc.switchToHome();
		});
		
		save.setOnAction((event) -> {
			
			// if form is valid
				
			this.sc.replaceDeliveryPoints(points);
			this.sc.switchToHome();
		});
	}
	
<<<<<<< HEAD
	public Location generateBogusLocation() {
		Location location = new Location("Bogus", "SAC");
		
		location.addPoint(new DeliveryPoint("HAL", 25, 0));
		location.addPoint(new DeliveryPoint("Hoyt", -25, -30));
		location.addPoint(new DeliveryPoint("PLC", -25, 30));
		location.addPoint(new DeliveryPoint("STEM", -40, 0));
		location.addPoint(new DeliveryPoint("Rockwell", -50, 0));
		location.addPoint(new DeliveryPoint("Crawford", -75, -30));
=======
	public void loadPoints(Map<DeliveryPoint, Boolean> points) {
		this.points = points;
>>>>>>> master
		
		pointsView.getItems().clear();
				
		for (DeliveryPoint dp : points.keySet()) {
			pointsView.getItems().add(dp);
		}
	}
}