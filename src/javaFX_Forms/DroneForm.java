package javaFX_Forms;

import javaClasses.Drone;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DroneForm extends Form {
	
	private SceneController sc;
	private BorderPane layout;
	
	private TextArea txtCargoWeight;
	private TextArea txtCruisingSpeed;
	private TextArea txtMaxFlightTime;
	private TextArea txtTurnAroundTime;
	private TextArea txtDeliveryTime;
	
	public DroneForm(SceneController sc, BorderPane layout) {
		this.layout = layout;
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
		
		layout.setCenter(form);
		
		// get buttons and set event handlers
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());
		
		cancel.setOnAction((event) -> {
			this.sc.switchToHome();
		});
		
		save.setOnAction((event) -> {
			
			Drone d = getFormData();
			
			if (d != null) {
				
				this.sc.replaceDrone(d);
				
				this.sc.switchToHome();
				
			} else {
				System.out.println("User validation error on drone form");
			}
		});
		
	}
	
	@Override
	public BorderPane getLayout() {
		return layout;
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
