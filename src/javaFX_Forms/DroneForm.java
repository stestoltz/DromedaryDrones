package javaFX_Forms;

import javaClasses.Drone;
import javaFX_Styling.StyleLabel;
import javaFX_Styling.StyleTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class DroneForm extends Form {

	private TextField txtCargoWeight;
	private TextField txtCruisingSpeed;
	private TextField txtMaxFlightTime;
	private TextField txtTurnAroundTime;
	private TextField txtDeliveryTime;
	private TextField txtUserSpecifiedWeight;
	private TextField txtNumberOfDrones;

	public DroneForm(SceneController sc, BorderPane layout) {
		super(sc, layout);
		
		Label description = new StyleLabel("All drone restrictions can be edited here. "
				+ "The restricted cargo weight can be lowered below the drone's carrying "
				+ "weight in order to extend battery life or drone life if desired.");

		description.setPrefHeight(75);
		description.setPrefWidth(800);
		description.setWrapText(true);
		description.setTextAlignment(TextAlignment.CENTER);

		Label label1 = new StyleLabel("Cargo weight (lb): ");
		txtCargoWeight = new StyleTextField();

		label1.setMaxWidth(300);
		HBox.setHgrow(label1,Priority.ALWAYS);
		HBox line1 = new HBox(label1, txtCargoWeight);

		Label label2 = new StyleLabel("Average cruising speed (mph): ");
		txtCruisingSpeed = new StyleTextField();

		label2.setMaxWidth(300);
		HBox.setHgrow(label2,Priority.ALWAYS);
		HBox line2 = new HBox(label2, txtCruisingSpeed);

		Label label3 = new StyleLabel("Maximum flight time (min): ");
		txtMaxFlightTime = new StyleTextField();

		label3.setMaxWidth(300);
		HBox.setHgrow(label3,Priority.ALWAYS);
		HBox line3 = new HBox(label3, txtMaxFlightTime);

		Label label4 = new StyleLabel("Turn around time (min): ");
		txtTurnAroundTime = new StyleTextField();

		label4.setMaxWidth(300);
		HBox.setHgrow(label4,Priority.ALWAYS);
		HBox line4 = new HBox(label4, txtTurnAroundTime);

		Label label5 = new StyleLabel("Delivery time (s): ");
		txtDeliveryTime = new StyleTextField();

		label5.setMaxWidth(300);
		HBox.setHgrow(label5,Priority.ALWAYS);
		HBox line5 = new HBox(label5, txtDeliveryTime);
		
		Label label6 = new StyleLabel("Restricted cargo weight (lb): ");
		txtUserSpecifiedWeight = new StyleTextField();

		label6.setMaxWidth(300);
		HBox.setHgrow(label6,Priority.ALWAYS);
		HBox line6 = new HBox(label6, txtUserSpecifiedWeight);
		
		Label label7 = new StyleLabel("Number of Drones: ");
		txtNumberOfDrones = new StyleTextField();
		
		label7.setMaxWidth(300);
		HBox.setHgrow(label7,Priority.ALWAYS);
		HBox line7 = new HBox(label7, txtNumberOfDrones);
		
		VBox form = new VBox(line1, line2, line3, line4, line5, line6, line7);
		//padding for centering
		form.setPadding(new Insets(0,0,0,200));

		// Create a gridpane for displaying the form VBox
		GridPane pane = new GridPane();
		
		// Set the form to be displayed in the top center
		pane.setAlignment(Pos.TOP_CENTER);
		
		// Add the form info to the pane
		pane.add(form, 0 , 1);
		pane.add(description,  0, 0);
		
		form.setSpacing(5);
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
				this.sc.getLocation().setNumberOfDrones(Integer.parseInt(txtNumberOfDrones.getText()));

				this.sc.switchToHome();
			}
		});

	}

	/**
	 * load drone data d into the drone form
	 * @param d
	 */
	public void loadForm(Drone d) {
		txtCargoWeight.setText(Double.toString(d.getCargoWeight()));
		txtCruisingSpeed.setText(Double.toString(d.getAverageCruisingSpeed()));
		txtMaxFlightTime.setText(Double.toString(d.getMaxFlightTime()));
		txtTurnAroundTime.setText(Double.toString(d.getTurnAroundTime()));
		txtDeliveryTime.setText(Double.toString(d.getDeliveryTime()));
		txtUserSpecifiedWeight.setText(Double.toString(d.getUserSpecifiedWeight()));
		txtNumberOfDrones.setText(Integer.toString(sc.getLocation().getNumberOfDrones()));
	}

	/**
	 * return a Drone containing the data in the form
	 * checks numberOfDrones but does not return that information
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
			int numberOfDrones = Integer.parseInt(txtNumberOfDrones.getText());
			
			if (userSpecifiedWeight > cargoWeight) {
				this.sc.runErrorPopUp("The specified weight must be less than or equal to the max cargo weight of the drone.");
				return null;
			}
			
			if (userSpecifiedWeight <= 0 || cargoWeight <= 0) {
				this.sc.runErrorPopUp("Drone cargo weight and specified weight must be above zero.");
				return null;
			}
			
			if (numberOfDrones <= 0) {
				this.sc.runErrorPopUp("There must be at least one drone to run a simulation.");
				return null;
			}
 
			if (cargoWeight > 0 && 
				cruisingSpeed > 0 && 
				maxFlightTime > 0 && 
				turnAroundTime >= 0 && 
				deliveryTime >= 0 && 
				userSpecifiedWeight >= 0 &&
				numberOfDrones > 0)
			{
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