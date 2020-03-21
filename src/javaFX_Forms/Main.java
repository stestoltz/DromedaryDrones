package javaFX_Forms;

import java.io.FileInputStream;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

		//create pane and add images
		BorderPane top = new BorderPane();
		top.setLeft(imageView);
		top.setCenter(header);

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
		// Add the Scene to the Stage
		stage.setScene(scene);
		// maximize screen and display the Stage
		stage.setMaximized(true);
		stage.show();
	}
}