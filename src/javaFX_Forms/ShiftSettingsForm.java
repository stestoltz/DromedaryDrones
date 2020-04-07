package javaFX_Forms;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShiftSettingsForm {
	
	public void start(Stage stage) {
		
		stage.setTitle("Shift Settings");
		
		// create a pane
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(25,25,25,25));
		
		// Title Text
		Text sceneTitle = new Text("Shift Settings");
		sceneTitle.setFont(Font.font("Arial",FontWeight.NORMAL, 20));
		pane.add(sceneTitle, 0, 0, 2, 1);
		
		//Labels
		Label numShifts = new Label("Number of Shifts");
		pane.add(numShifts, 0, 1);
		
		TextField numShiftsField = new TextField();
		pane.add(numShiftsField, 1, 1);
		
		Label hrsinShift = new Label("Hours in a Shift");
		pane.add(hrsinShift, 0, 2);
		
		TextField hrsinShiftField = new TextField();
		pane.add(hrsinShiftField, 1, 2);
		
		
		
		
		Scene scene = new Scene(pane, 300, 275);
		
		stage.setScene(scene);
		
		stage.show();
	}

}