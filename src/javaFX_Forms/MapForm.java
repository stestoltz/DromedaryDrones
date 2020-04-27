package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javaClasses.DeliveryPoint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MapForm extends Form {
	
	private ListView<HBox> pointsView;
	
	private List<DeliveryPoint> points;
	
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
			HBox hbox = pointsView.getSelectionModel().getSelectedItem();
			
			if (hbox != null) {
				DeliveryPoint selectedPoint = getDeliveryPoint(hbox);
				
				points.remove(selectedPoint);
				pointsView.getItems().remove(hbox);
			}
		});
		
		edit.setOnAction((event) -> {
			HBox hbox = pointsView.getSelectionModel().getSelectedItem();
			
			if (hbox != null) {
				TextField text = (TextField) hbox.getChildren().get(0);
				
				String currentVal = text.getText();
				
				// temporarily listener to prevent empty strings in text fields
				ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
				    @Override
				    public void changed(ObservableValue<? extends Boolean> hasFocus, Boolean oldValue, Boolean newValue) {
				    	// lost focus
						if (!newValue) {
							if (text.getText().equals("")) {
								// if empty, revert to old
								text.setText(currentVal);
								
								// remove this listener
								text.focusedProperty().removeListener(this);
							}
							
							// change the name in the list
							getDeliveryPoint(hbox).setName(text.getText());
						}
				    }
				};
				
				text.focusedProperty().addListener(listener);		
				
				text.setEditable(true);
				text.requestFocus();
			}
		});
		
		VBox left = new VBox();
		left.getChildren().add(pointsView);
		left.getChildren().add(listButtons);
		
		//description
		Label description = new Label("Double click the map at a given coordinate that you wish to add. "
				+ "This will add the new delivery point to the list. Delivery points can be toggled on "
				+ "and off using the checkboxes.\n"
				+ "The map displays all delivery points and highlights points that will be used in the simulation.");
		VBox center = new VBox();
		center.getChildren().addAll(description,mapView);
		layout.setCenter(center);
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
			
			Map<DeliveryPoint, Boolean> endPoints = new HashMap<>();

			boolean atLeastOneActivePoint = false;
			
			for (HBox hbox : pointsView.getItems()) {
				boolean active = getActive(hbox);
				endPoints.put(getDeliveryPoint(hbox), active);
				
				if (active) {
					atLeastOneActivePoint = true;
				}
			}
			
			if (atLeastOneActivePoint) {
				this.sc.replaceDeliveryPoints(endPoints);
				this.sc.switchToHome();
			} else {
				System.out.println("No active delivery points selected");
			}
		});
	}
	
	/**
	 * load a hashmap of delivery points into the ListView<HBox>
	 * @param points
	 */
	public void loadPoints(Map<DeliveryPoint, Boolean> points) {
		this.points = new ArrayList<>();
		
		pointsView.getItems().clear();
				
		for (DeliveryPoint dp : points.keySet()) {
			
			this.points.add(dp);
			
			HBox hbox = new HBox();
			
			TextField text = new TextField();
			text.setText(dp.toString());
			text.setEditable(false);
			
			CheckBox check = new CheckBox();
			check.setSelected(points.get(dp));
			
			hbox.getChildren().addAll(text, check);
			pointsView.getItems().add(hbox);
		}
	}
	
	/**
	 * Retrieve a DeliveryPoint from a given HBox
	 * @param hbox
	 * @return
	 */
	public DeliveryPoint getDeliveryPoint(HBox hbox) {
		for (int i = 0; i < points.size(); i++) {
			if (hbox == pointsView.getItems().get(i)) {
				return points.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieve an active value for a DeliveryPoint from a given HBox
	 * @param hbox
	 * @return
	 */
	public boolean getActive(HBox hbox) {
		for (int i = 0; i < points.size(); i++) {
			if (hbox == pointsView.getItems().get(i)) {
				CheckBox check = (CheckBox) hbox.getChildren().get(1);
				
				return check.isSelected();
			}
		}
		
		return false;
	}
}