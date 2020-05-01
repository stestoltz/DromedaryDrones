package javaFX_Styling;

import javafx.scene.control.Button;

public class StyleButton extends Button{
	
	public StyleButton(String name) {
		this.setText(name);
		this.setStyle("-fx-background-color: Orange;"
				+ "-fx-font-size: 12pt;");
		this.setPrefWidth(80);
	}
}
