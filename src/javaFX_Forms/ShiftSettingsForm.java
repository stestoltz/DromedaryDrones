package javaFX_Forms;

import java.util.ArrayList;
import java.util.List;

import javaClasses.ShiftDetails;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ShiftSettingsForm extends Form {

	private ListView<HBox> order;
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
		pane.setAlignment(Pos.TOP_LEFT);
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(25,25,25,25));

		//add buttons
		BorderPane bottom = (BorderPane) layout.getBottom();
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());

		cancel.setOnAction((event) -> {
			this.sc.switchToHome();
		});

		save.setOnAction((event) -> {

			//Whatever is inputed into the textField is updated and changed
			try {
				shift.setNumberOfShifts(Integer.parseInt(numShiftsField.getText()));
				shift.setHoursInShift(Integer.parseInt(hrsinShiftField.getText()));
				List<Integer> editOrderPerHour = new ArrayList<Integer>();
				for(HBox o : order.getItems()) {
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
				System.out.println("Not a valid integer input");
			}
		});

		//create and add the labels to the gridpane
		Label numShifts = new Label("Number of Shifts");
		pane.add(numShifts, 0, 3); //0,1
		
		Label description = new Label("Number of shifts and hours in a shift can be edited on the left. "
				+ "Clicking \"save hours\" updates the list on the right with a new entry for each hour. "
				+ "Then orders per hour can be edited within that list.");
		description.setFont(Font.font("Calibri", 15));
		pane.add(description, 0, 0, 2, 4);

		numShiftsField = new TextField();
		pane.add(numShiftsField, 1, 3); //1,1

		Label hrsinShift = new Label("Hours in a Shift");
		pane.add(hrsinShift, 0, 4); //0,2

		hrsinShiftField = new TextField();
		pane.add(hrsinShiftField, 1, 4); //1,2

		Button saveHrs = new Button("Save Hours");


		//creates a button that allows the user to update the 
		//hours in a shift and populates the order per hours list
		//after the user clicks the save button
		saveHrs.setOnAction(event->{
			shiftHrs(hrsinShiftField,numShiftsField);
		});

		//adds the save button to the form
		pane.add(saveHrs, 0, 5); //0,3


		//create and set up the orders per hour list and add
		//it to the gridpane
		Label ordersPerHour = new Label("Orders Per Hour");
		pane.add(ordersPerHour, 4,3); //4,1

		order = new ListView<>();
		order.setPrefSize(120,  100);

		pane.add(order, 4, 5); //4,3

		layout.setCenter(pane);
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
		//cretes an ArrayList of HBox's that holds the data
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
			inputVal.setText(Integer.toString(shiftDetails.getOrdersPerHour().get(i)));
			HBox hbox = new HBox();
			Text temp = new Text("Hour " + (i + 1));
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
		order.getItems().clear();
		order.getItems().addAll(hoursList);

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

		//cretes an ArrayList of HBox's
		ArrayList<HBox> orderElements = new ArrayList<>();

		//Loop that goes through the shifts to create the orders per hour
		//list that is auto-populated from the info that is put in by the user
		try {
			for(int i = 0; i < Integer.parseInt(hrsinShiftField.getText()); i++) {
				TextField inputVal = new TextField();
				if(shift.getHoursInShift() > i) {
					inputVal.setText(Integer.toString(shift.getOrdersPerHour().get(i)));
				}

				HBox hbox = new HBox();
				Text temp = new Text("Hour " + (i + 1));
				hbox.getChildren().addAll(temp, inputVal);
				orderElements.add(hbox);
				hbox.setPrefWidth(20);
			}
		}

		catch(Exception e) {
			System.out.println("Hours in a shift could not be converted to an integer");
		}

		//makes the observable list a listview to be displayed
		ObservableList<HBox> hoursList = FXCollections.<HBox>observableArrayList(orderElements);


		// fill the orders per hour ListView
		order.getItems().clear();
		order.getItems().addAll(hoursList);
	}

}