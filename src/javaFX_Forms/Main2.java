package javaFX_Forms;

import javaClasses.Location;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main2 extends Application {

	public static void main(String[] args) {
		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		SceneController controller = new SceneController(stage);
		
		controller.switchToHome();

		stage.setMaximized(true);
		stage.show();
		
	}
}
