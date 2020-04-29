package javaFX_Forms;

import javaClasses.Drone;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DroneForm extends Form {

	private TextField txtCargoWeight;
	private TextField txtCruisingSpeed;
	private TextField txtMaxFlightTime;
	private TextField txtTurnAroundTime;
	private TextField txtDeliveryTime;
	private TextField txtUserSpecifiedWeight;

	public DroneForm(SceneController sc, BorderPane layout) {
		super(sc, layout);
		
		Label description = new Label("All drone restrictions can be edited here. "
				+ "The restricted cargo weight can be lowered below the drone's carrying "
				+ "weight in order to extend battery life or drone life if desired.");
		description.setFont(new Font("Verdana", 14));

		Label label1 = new Label("Cargo weight (lb): ");
		label1.setFont(new Font("Verdana", 18));
		txtCargoWeight = new TextField();

		HBox line1 = new HBox(label1, txtCargoWeight);

		Label label2 = new Label("Average cruising speed (mph): ");
		label2.setFont(new Font("Verdana", 18));
		txtCruisingSpeed = new TextField();

		HBox line2 = new HBox(label2, txtCruisingSpeed);

		Label label3 = new Label("Maximum flight time (min): ");
		label3.setFont(new Font("Verdana", 18));
		txtMaxFlightTime = new TextField();

		HBox line3 = new HBox(label3, txtMaxFlightTime);

		Label label4 = new Label("Turn around time (min): ");
		label4.setFont(new Font("Verdana", 18));
		txtTurnAroundTime = new TextField();

		HBox line4 = new HBox(label4, txtTurnAroundTime);

		Label label5 = new Label("Delivery time (s): ");
		label5.setFont(new Font("Verdana", 18));
		txtDeliveryTime = new TextField();

		HBox line5 = new HBox(label5, txtDeliveryTime);
		
		Label label6 = new Label("Restricted cargo weight (lb): ");
		label6.setFont(new Font("Verdana", 18));
		txtUserSpecifiedWeight = new TextField();

		HBox line6 = new HBox(label6, txtUserSpecifiedWeight);

		VBox form = new VBox(description, line1, line2, line3, line4, line5, line6);
		
		// Create a gridpane for displaying the form VBox
		GridPane pane = new GridPane();
		
		// Set the form to be displayed in the top center
		pane.setAlignment(Pos.TOP_CENTER);
		
		// Add the form info to the pane
		pane.addColumn(0, form);	

		layout.setCenter(pane);

		// get buttons and set event handlers

		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());

		cancel.setOnAction((event) -> {
			this.sc.switchToHome();
		});

		save.setOnAction((event) -> {

			Drone d = getFormData();

			if (d != null){

				this.sc.replaceDrone(d);

				this.sc.switchToHome();

			} else {
				System.out.println("User validation error on drone form");
			}
		});

	}

	/**
	 * load drone data d into the drone form
	 * @param d
	 */
	public void loadDrone(Drone d) {
		txtCargoWeight.setText(Double.toString(d.getCargoWeight()));
		txtCruisingSpeed.setText(Double.toString(d.getAverageCruisingSpeed()));
		txtMaxFlightTime.setText(Double.toString(d.getMaxFlightTime()));
		txtTurnAroundTime.setText(Double.toString(d.getTurnAroundTime()));
		txtDeliveryTime.setText(Double.toString(d.getDeliveryTime()));
		txtUserSpecifiedWeight.setText(Double.toString(d.getUserSpecifiedWeight()));
	}

	/**
	 * return a Drone containing the data in the form
	 * @return null if data is missing or invalid
	 */
	public Drone getFormData() {

		try {
			double cargoWeight = Double.parseDouble(txtCargoWeight.getText());
			double cruisingSpeed = Double.parseDouble(txtCruisingSpeed.getText());
			double maxFlightTime = Double.parseDouble(txtMaxFlightTime.getText());
			double turnAroundTime = Double.parseDouble(txtTurnAroundTime.getText());
			double deliveryTime = Double.parseDouble(txtDeliveryTime.getText());
			double userSpecifiedWeight = Double.parseDouble(txtUserSpecifiedWeight.getText());
			
			if (userSpecifiedWeight > cargoWeight) {
				this.sc.runErrorPopUp("The specified weight must be less than or equal to the max cargo weight of the drone.");
				return null;
			}
			
			if (userSpecifiedWeight <= 0 || cargoWeight <= 0) {
				this.sc.runErrorPopUp("Drone cargo weight and specified weight must be above zero.");
				return null;
			}

			if (cargoWeight > 0 && cruisingSpeed > 0 && maxFlightTime > 0 && turnAroundTime >= 0 && deliveryTime >= 0 && userSpecifiedWeight >= 0) {
				return new Drone(cargoWeight, cruisingSpeed, maxFlightTime, turnAroundTime, deliveryTime, userSpecifiedWeight);
			}
			
			this.sc.runErrorPopUp("All values in the drone form must be positive values.");
			return null;

		} catch (Exception ex) {
			this.sc.runErrorPopUp("All values in the drone form must be valid numbers.");
			return null;
		}
	}

}