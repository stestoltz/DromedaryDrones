package javaFX_Forms;

import java.util.ArrayList;
import java.util.List;

import javaClasses.Location;
import javaClasses.ShiftDetails;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShiftSettingsForm extends Form {
	
	private ListView<HBox> order;
	private ShiftDetails shift;
	
	private TextField numShiftsField;
	private TextField hrsinShiftField;
	
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
			
			// if form is valid
			
			//make shift contain form data
				
			this.sc.replaceShift(shift);
			this.sc.switchToHome();
		});
		
		//Labels
		Label numShifts = new Label("Number of Shifts");
		pane.add(numShifts, 0, 1);
		
		numShiftsField = new TextField();
		pane.add(numShiftsField, 1, 1);
		
		Label hrsinShift = new Label("Hours in a Shift");
		pane.add(hrsinShift, 0, 2);
		
		hrsinShiftField = new TextField();
		pane.add(hrsinShiftField, 1, 2);
		
		
		//Orders per hour list
		Label ordersPerHour = new Label("Orders Per Hour");
		pane.add(ordersPerHour, 4,1);
		
		order = new ListView();
		order.setPrefSize(120,  100);
        
		pane.add(order, 4, 2);
		
		layout.setCenter(pane);
	}

	public void loadShift(ShiftDetails shiftDetails) {
		
		ArrayList<HBox> orderElements = new ArrayList();
		
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
		ObservableList<HBox> hoursList = FXCollections.<HBox>observableArrayList(orderElements);
		//makes the observable list a listview to be displayed
		
		// fill the food ListView
		order.getItems().clear();
		order.getItems().addAll(hoursList);
		
	}

}
