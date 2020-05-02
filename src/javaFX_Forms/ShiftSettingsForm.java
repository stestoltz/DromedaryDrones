package javaFX_Forms;

import java.util.ArrayList;
import java.util.List;

import javaClasses.ShiftDetails;
import javaFX_Styling.StyleButton;
import javaFX_Styling.StyleLabel;
import javaFX_Styling.StyleTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ShiftSettingsForm extends Form {

	private ListView<HBox> hours;
	private ShiftDetails shift;

	private TextField numShiftsField;
	private TextField hrsinShiftField;

	/**
	 * Constructor that creates and sets up the 
	 * shift settings form
	 * @param sc SceneController that calls the shift settings
	 * form when the button is clicked on the drop down
	 * @param layout BorderPane that is called to display the
	 * shift settings form
	 */
	public ShiftSettingsForm(SceneController sc, BorderPane layout) {
		super(sc, layout);

		// create a pane
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setHgap(20);
		pane.setVgap(10);
		pane.setPadding(new Insets(25,25,25,25));

		//add buttons
		BorderPane bottom = (BorderPane) layout.getBottom();
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());
		
		Label description = new StyleLabel("Number of shifts and hours in a shift can " 
				+ "be edited on the left. Clicking \"Save Hours\" updates the list "
				+ "on the right with a new entry for each hour. Then orders per hour " 
				+ "can be edited within that list.");
		
		description.setPrefWidth(700);
		description.setWrapText(true);
		description.setTextAlignment(TextAlignment.CENTER);

		cancel.setOnAction((event) -> {
			this.sc.switchToHome();
		});

		save.setOnAction((event) -> {
			
			boolean badInput = false;
			
			//check that number of orders is greater than or equal to zero
			try {
				shift.setNumberOfShifts(Integer.parseInt(numShiftsField.getText()));
				shift.setHoursInShift(Integer.parseInt(hrsinShiftField.getText()));
				
				for (HBox o : hours.getItems()) {
					TextField inputVal = (TextField)o.getChildren().get(1);
					int number = Integer.parseInt(inputVal.getText());
					if (number < 0) {
						this.sc.runErrorPopUp("Number of orders per hour must be greater than or equal to zero.");
						badInput = true;
					}
				}
			} 
			catch(Exception e) {
				badInput = true;
				this.sc.runErrorPopUp("All values must be valid integers.");
			}
			
			if (!badInput) {
			//Whatever is inputed into the textField is updated and changed
				try {
					shift.setNumberOfShifts(Integer.parseInt(numShiftsField.getText()));
					shift.setHoursInShift(Integer.parseInt(hrsinShiftField.getText()));
					List<Integer> editOrderPerHour = new ArrayList<Integer>();
					for(HBox o : hours.getItems()) {
						TextField inputVal = (TextField)o.getChildren().get(1);
						editOrderPerHour.add(Integer.parseInt(inputVal.getText()));
					}
					shift.setOrdersPerHour(editOrderPerHour);
					this.sc.replaceShift(shift);
					this.sc.switchToHome();
				}
	
				//if the user does not put in an integer than tells
				//the user they need to make it an integer
				catch(Exception e) {
					this.sc.runErrorPopUp("All values must be valid integers.");
				}
			}
		});

		
		//create and add the labels to the gridpane
		Label numShifts = new StyleLabel("Number of Shifts:");
		//numShifts.setFont
		
		numShiftsField = new StyleTextField();
		numShiftsField.setMaxWidth(50);

		Label hrsinShift = new StyleLabel("Hours in a Shift:");

		hrsinShiftField = new StyleTextField();
		hrsinShiftField.setMaxWidth(50);
		
		Button saveHrs = new StyleButton("Save Hours");
		saveHrs.setPrefWidth(120);
		VBox col1 = new VBox();
		col1.getChildren().addAll(numShifts, hrsinShift);
		VBox col2 = new VBox();
		col2.getChildren().addAll(numShiftsField,hrsinShiftField, saveHrs);
		

		//creates a button that allows the user to update the 
		//hours in a shift and populates the order per hours list
		//after the user clicks the save button
		saveHrs.setOnAction(event->{
			if (Integer.parseInt(hrsinShiftField.getText()) <= 0 || 
					Integer.parseInt(numShiftsField.getText()) <= 0) {
				this.sc.runErrorPopUp("Number of shifts and hours in a shift must be greater than zero.");
			} else {
				shiftHrs(hrsinShiftField,numShiftsField);
			}
		});

		//adds the save button to the form
		col1.setSpacing(30);
		col2.setSpacing(30);
		pane.add(col1, 1, 0);
		pane.add(col2, 2, 0);


		//create and set up the orders per hour list and add
		//it to the gridpane
		Label ordersPerHour = new StyleLabel("Orders Per Hour:");

		hours = new ListView<>();
		hours.setPrefSize(200,  200);
		hours.setStyle("-fx-font-size: 12pt;");
		
		VBox col3 = new VBox();
		col3.getChildren().addAll(ordersPerHour, hours);
		col3.setSpacing(30);
		pane.add(col3, 5, 0);
		
		VBox middleItems = new VBox();
		middleItems.getChildren().addAll(description, pane);
		middleItems.setAlignment(Pos.TOP_CENTER);
		
		layout.setCenter(middleItems);
	}

	/**
	 * Method that allows the shift settings form
	 * to be pre loaded with all the information from
	 * shiftDetails that is already in the simulation
	 * @param shiftDetails ShiftDetails that grabs the information
	 * needed from the simulation in order to populate the shift
	 * settings form
	 */
	public void loadShift(ShiftDetails shiftDetails) {
		//creates an ArrayList of HBox's that holds the data
		//from shiftDetails
		// if hours > length of list, fill with empty slots
		while (shiftDetails.getHoursInShift() > shiftDetails.getOrdersPerHour().size()) {
			shiftDetails.getOrdersPerHour().add(0);
		}
		ArrayList<HBox> orderElements = new ArrayList<>();

		//Loop that goes through the shifts to create the orders per hour
		//list that is auto-populated from the info that is already inside
		// the current simulation
		for(int i = 0; i < shiftDetails.getHoursInShift(); i++) {
			TextField inputVal = new TextField();
			inputVal.setPrefWidth(40);
			inputVal.setText(Integer.toString(shiftDetails.getOrdersPerHour().get(i)));
			HBox hbox = new HBox();
			Label temp = new Label("Hour " + (i + 1));
			temp.setMaxWidth(200);
			HBox.setHgrow(temp, Priority.ALWAYS);
			hbox.getChildren().addAll(temp, inputVal);
			orderElements.add(hbox);
			hbox.setPrefWidth(20);
		}

		this.shift = shiftDetails;

		numShiftsField.setText(Integer.toString(shiftDetails.getNumberOfShifts()));
		hrsinShiftField.setText(Integer.toString(shiftDetails.getHoursInShift()));

		//makes the observable list a listview to be displayed
		ObservableList<HBox> hoursList = FXCollections.<HBox>observableArrayList(orderElements);


		// fill the orders per hour ListView
		hours.getItems().clear();
		hours.getItems().addAll(hoursList);

	}

	/**
	 * Method that updates the hours in a shift
	 * to whatever the user entered and adds/deletes the hours
	 * from the orders per hour list based on what the user inputs
	 * Runs by clicking the save button
	 * @param hrsinShiftField TextField that stores the hours in a shift
	 * @param numShiftsField TextField that stores the number of shifts in a simulation
	 */
	public void shiftHrs(TextField hrsinShiftField, TextField numShiftsField) {

		//creates an ArrayList of HBox's
		ArrayList<HBox> orderElements = new ArrayList<>();

		//Loop that goes through the shifts to create the orders per hour
		//list that is auto-populated from the info that is put in by the user
		try {
			for(int i = 0; i < Integer.parseInt(hrsinShiftField.getText()); i++) {
				TextField inputVal = new TextField();
				inputVal.setPrefWidth(30);
				if(shift.getHoursInShift() > i) {
					inputVal.setText(Integer.toString(shift.getOrdersPerHour().get(i)));
				}

				HBox hbox = new HBox();
				Label temp = new Label("Hour " + (i + 1));
				temp.setMaxWidth(100);
				HBox.setHgrow(temp, Priority.ALWAYS);
				hbox.getChildren().addAll(temp, inputVal);
				orderElements.add(hbox);
				hbox.setPrefWidth(20);
			}
		}

		catch(Exception e) {
			this.sc.runErrorPopUp("Hours in a shift could not be converted to an integer");
		}

		//makes the observable list a listview to be displayed
		ObservableList<HBox> hoursList = FXCollections.<HBox>observableArrayList(orderElements);


		// fill the orders per hour ListView
		hours.getItems().clear();
		hours.getItems().addAll(hoursList);
	}

}