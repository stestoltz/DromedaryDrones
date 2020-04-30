package javaFX_Styling;
import javafx.scene.control.TextField;

public class StyleTextField extends TextField{
	
	public StyleTextField() {
		this.setStyle("-fx-font-size: 12pt;");
		this.setPrefWidth(100);

	}
	public StyleTextField(String text) {
		this.setText(text);
		this.setStyle("-fx-font-size: 12pt;");
		this.setPrefWidth(100);

	}
}
