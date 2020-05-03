package javaFX_Styling;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class StyleButton extends Button{
	
	public StyleButton(String name) {
		super(name);
		this.setText(name);
		this.setStyle("-fx-background-color: Orange;"
				+ "-fx-font-size: 12pt;");
		
		this.setPrefWidth(80);
	}
	
	public StyleButton(String name, ImageView image) {
		super(name, image);
		this.setText(name);
		this.setStyle("-fx-background-color: Orange;"
				+ "-fx-font-size: 12pt;");

		this.setPrefWidth(80);
	}
}
