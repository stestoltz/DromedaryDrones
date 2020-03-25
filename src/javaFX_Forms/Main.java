package javaFX_Forms;

import java.io.FileInputStream;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javaClasses.Location;
import javaClasses.DeliveryPoint;


public class Main extends Application
{
	// Declaring the TextArea for Logging
	TextArea logging;

	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		//create logo
		Image image = new Image(new FileInputStream("res/Temp_logo.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(150);

		//create map
		Image map = new Image(new FileInputStream("res/map.jfif"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitHeight(500);

		//set title
		stage.setTitle("Main Form"); 

		//create buttons and header
		Button delete = new Button("Delete");
		Button saveChanges = new Button("Save Changes");
		Label header = new Label("Dromedary Drones");
		header.setFont(new Font("Comic Sans", 30));

		//create pane and add buttons to bottom corners
		BorderPane bottom = new BorderPane();
		bottom.setLeft(delete);
		bottom.setRight(saveChanges);
		//do stuff

		//create a menubar for the hamburger menu
				MenuBar menuBar = new MenuBar();
				VBox vBox = new VBox(menuBar);
				
				Menu menu1 = new Menu("Simulation Settings");
				menuBar.getMenus().add(menu1);
				MenuItem menuItem1 = new MenuItem("Modify Mapping");
				MenuItem menuItem2 = new MenuItem("Food Settings");
				MenuItem menuItem3 = new MenuItem("Order Settings");
				MenuItem menuItem4 = new MenuItem("Shift Settings");
				MenuItem menuItem5 = new MenuItem("Drone Settings");

				menu1.getItems().add(menuItem1);
				menu1.getItems().add(menuItem2);
				menu1.getItems().add(menuItem3);
				menu1.getItems().add(menuItem4);
				menu1.getItems().add(menuItem5);
		//create pane and add images
		BorderPane top = new BorderPane();
		top.setLeft(imageView);
		top.setCenter(header);
		top.setRight(menuBar);

		// Create the pane and add the other panes
		BorderPane layout = new BorderPane();
		layout.setBottom(bottom);
		layout.setTop(top);
		
		layout.setCenter(mapView);


		// Set the padding of the layout
		layout.setStyle("-fx-padding: 10;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 5;" +
				"-fx-border-radius: 5;");



		Scene scene = new Scene(layout);
		Scene menu = new Scene(vBox);
		// Add the Scene to the Stage
		stage.setScene(scene);
		// maximize screen and display the Stage
		stage.setMaximized(true);
		stage.show();
	}
	
	//pop up window
		public void start1(final Stage primaryStage) {
		    Button btn = new Button();
		    btn.setText("Open Dialog");
		    btn.setOnAction(
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                final Stage dialog = new Stage();
		                dialog.initModality(Modality.APPLICATION_MODAL);
		                dialog.initOwner(primaryStage);
		                VBox dialogVbox = new VBox(20);
		                dialogVbox.getChildren().add(new Text("This is a Dialog"));
		                Scene dialogScene = new Scene(dialogVbox, 300, 200);
		                dialog.setScene(dialogScene);
		                dialog.show();
		            }
		         });
		    }
	
	public Location generateBogusLocation() {
		Location location = new Location("Bogus", "SAC");
		
		location.addPoint(new DeliveryPoint("HAL", 25, 0));
		location.addPoint(new DeliveryPoint("Hoyt", -25, -30));
		location.addPoint(new DeliveryPoint("PLC", -25, 30));
		location.addPoint(new DeliveryPoint("STEM", -40, 0));
		location.addPoint(new DeliveryPoint("Rockwell", -50, 0));
		location.addPoint(new DeliveryPoint("Crawford", -75, -30));
		
		return location;
	}
}