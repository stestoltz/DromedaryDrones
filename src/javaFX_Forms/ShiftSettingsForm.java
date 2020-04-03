package javaFX_Forms;

import java.util.List;

import javafx.application.Application;
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

public class ShiftSettingsForm extends Application {
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	public void start(Stage stage) {
		
		stage.setTitle("Shift Settings");
		
		// create a pane
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER_LEFT);
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
		
		
		//Orders per hour list
		Label ordersPerHour = new Label("Orders Per Hour");
		pane.add(ordersPerHour, 4,1);
		
		ListView listView = new ListView();
		HBox hbox = new HBox();
		listView.add(hbox);	//adds the hbox to the arraylist
		hbox.setPrefWidth(20);

        listView.getItems().addAll("Hour 1",hbox);
        listView.getItems().addAll("Hour 2", hbox);
        listView.getItems().addAll("Hour 3", hbox);
        listView.getItems().addAll("Hour 4", hbox);
        listView.getItems().addAll("Hour 5", hbox);
        listView.getItems().addAll("Hour 6", hbox);
        listView.getItems().addAll("Hour 7", hbox);
        listView.getItems().addAll("Hour 8", hbox);
        listView.getItems().addAll("Hour 9", hbox);

        pane.add(listView, 4, 2);
		
		
		Scene scene = new Scene(pane, 600, 300);
		
		stage.setScene(scene);
		
		stage.show();
	}

}
