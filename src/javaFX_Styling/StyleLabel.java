package javaFX_Styling;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class StyleLabel extends Label{
	
	public StyleLabel(String text) {
		this.setText(text);
		this.setFont(new Font("Verdana", 12));
		this.setStyle("-fx-font-size: 12pt;");

	}

}
