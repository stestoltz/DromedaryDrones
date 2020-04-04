package javaFX_Forms;

import javaClasses.Drone;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DronePane extends BorderPane {
	
	private SceneController sc;
	
	private TextArea txtCargoWeight;
	private TextArea txtCruisingSpeed;
	private TextArea txtMaxFlightTime;
	private TextArea txtTurnAroundTime;
	private TextArea txtDeliveryTime;
	
	public DronePane(SceneController sc, BorderPane layout) {
		super();
		
		this.sc = sc;
		
		Label label1 = new Label("Cargo weight (lb): ");
		txtCargoWeight = new TextArea();
	
		HBox line1 = new HBox(label1, txtCargoWeight);
		
		Label label2 = new Label("Average cruising speed (mph): ");
		txtCruisingSpeed = new TextArea();
		
		HBox line2 = new HBox(label2, txtCruisingSpeed);
		
		Label label3 = new Label("Maximum flight time (min): ");
		txtMaxFlightTime = new TextArea();
		
		HBox line3 = new HBox(label3, txtMaxFlightTime);
		
		Label label4 = new Label("Turn around time (min): ");
		txtTurnAroundTime = new TextArea();
		
		HBox line4 = new HBox(label4, txtTurnAroundTime);
		
		Label label5 = new Label("Delivery time (s): ");
		txtDeliveryTime = new TextArea();
		
		HBox line5 = new HBox(label5, txtDeliveryTime);
		
		VBox form = new VBox(line1, line2, line3, line4, line5);
		
		this.setCenter(form);
		
		// get buttons and set event handlers
		
		BorderPane bottom = ((BorderPane) this.getBottom());
		Button delete = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());
		
		delete.setOnAction((event) -> {
			sc.switchToHome();
		});
		
		save.setOnAction((event) -> {
			
			Drone d = getFormData();
			
			if (d != null) {
				
				sc.replaceDrone(d);
				
				sc.switchToHome();
				
			} else {
				System.out.println("User validation error on drone form");
			}
		});
		
	}
	
	public void loadDrone(Drone d) {
		txtCargoWeight.setText(Double.toString(d.getCargoWeight()));
		txtCruisingSpeed.setText(Double.toString(d.getAverageCruisingSpeed()));
		txtMaxFlightTime.setText(Double.toString(d.getMaxFlightTime()));
		txtTurnAroundTime.setText(Double.toString(d.getTurnAroundTime()));
		txtDeliveryTime.setText(Double.toString(d.getDeliveryTime()));
	}
	
	public Drone getFormData() {
		double cargoWeight = Double.parseDouble(txtCargoWeight.getText());
		double cruisingSpeed = Double.parseDouble(txtCruisingSpeed.getText());
		double maxFlightTime = Double.parseDouble(txtMaxFlightTime.getText());
		double turnAroundTime = Double.parseDouble(txtTurnAroundTime.getText());
		double deliveryTime = Double.parseDouble(txtDeliveryTime.getText());
		
		return new Drone(cargoWeight, cruisingSpeed, maxFlightTime, turnAroundTime, deliveryTime);
	}

}
