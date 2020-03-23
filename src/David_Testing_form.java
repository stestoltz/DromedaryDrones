import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button; 

public class David_Testing_form extends Application
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
    	stage.setTitle("creating buttons"); 
    	
        // create a button 
        Button edit = new Button("Edit"); 
        Button delete = new Button("Delete");
        //button section on gui
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(edit,delete);	//adds buttons to vbox
        vbox.setPadding(new Insets(50, 10, 0, 0));	//above,right,below,left
  
        // Create the TextArea
        logging = new TextArea();
        logging.setMaxWidth(300);
        logging.setMaxHeight(150);
 
        // Create the Labels
        Label foodLabel = new Label("Select Food: ");
                 
        // Create the Lists for the ListViews
        ArrayList<String> foods = new ArrayList<>();
        foods.add("Burger");
        foods.add("Fries");
        foods.add("Drink");
        foods.add("Pie");
        foods.add("Quesadilla");
        foods.add("Ice Cream");
        foods.add("Pretzels");
        foods.add("Milkshake");

        ObservableList<String> foodList = FXCollections.<String>observableArrayList(foods);
         
        // Create the ListView for the seasons
        ListView<String> foodView = new ListView<>(foodList);
        // Set the Orientation of the ListView
        foodView.setOrientation(Orientation.VERTICAL);
        // Set the Size of the ListView
        foodView.setPrefSize(120, 100);
 
        // Update the TextArea when the selected season changes
//        foodView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
//        {
//            public void changed(ObservableValue<? extends String> ov,
//                    final String oldvalue, final String newvalue) 
//            {
//            	foodSelectChanged(ov, oldvalue, newvalue);
//        }});
 
        
        // Create the Season VBox
        VBox foodSelection = new VBox();
        // Set Spacing to 10 pixels
        foodSelection.setSpacing(10);
        // Add the Label and the List to the VBox
        foodSelection.getChildren().addAll(foodLabel,foodView);
         
         
        // Create the GridPane
        GridPane pane = new GridPane();
        // Set the horizontal and vertical gaps between children
        pane.setHgap(10);
        pane.setVgap(5);
        // Add the two gui components
        pane.addColumn(0, foodSelection);
        pane.addColumn(1, vbox);

//        // Add the TextArea at position 2
//        pane.addColumn(2, logging);
             
        // Set the Style-properties of the GridPane
        pane.setStyle("-fx-padding: 10;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;");
         
        // Create the Scene
        Scene scene = new Scene(pane);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("A simple ListView Example");
        // Display the Stage
        stage.show();
    }
 
    // Method to display the Season, which has been changed
    public void foodSelectChanged(ObservableValue<? extends String> observable,String oldValue,String newValue) 
    {
        String oldText = oldValue == null ? "null" : oldValue.toString();
        String newText = newValue == null ? "null" : newValue.toString();
         
        logging.appendText("food selection changed: old = " + oldText + ", new = " + newText + "\n");
    }
}