package javaFX_Styling;

import javafx.scene.control.Menu;

public class StyleMenu extends Menu{
	
	public StyleMenu() {
		super();
		this.setStyle("-fx-background-color: Orange;"
				+ "-fx-font-size: 12pt;");
	}
	public StyleMenu(String text) {
		super(text);
		this.setStyle("-fx-background-color: Orange;"
				+ "-fx-font-size: 12pt;");		
	}

}
