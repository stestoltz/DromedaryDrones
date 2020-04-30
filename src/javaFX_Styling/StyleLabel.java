package javaFX_Styling;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class StyleLabel extends Label{
	
	public StyleLabel(String text) {
		this.setText(text);
		this.setStyle("-fx-font-size: 12pt;");
	}

}
