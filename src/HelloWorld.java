import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloWorld extends Application
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
      stage.setTitle("Main Form"); 
      Button delete = new Button("Delete");
        //button section on gui
      
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(delete);  //adds buttons to vbox
        vbox.setPadding(new Insets(50, 10, 0, 0));  //above,right,below,left
      //do stuff
        
      // Create the GridPane
        GridPane pane = new GridPane();
        // Set the horizontal and vertical gaps between children
        pane.setHgap(10);
        pane.setVgap(5);
        // Add the two gui components
        pane.addColumn(1, vbox);

//        // Add the TextArea at position 2
//        pane.addColumn(2, logging);
             
        // Set the Style-properties of the GridPane
        /*pane.setStyle("-fx-padding: 10;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;");*/
      
      
      
      Scene scene = new Scene(pane);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Display the Stage
        
        stage.setMaximized(true);
        stage.show();
  }
}