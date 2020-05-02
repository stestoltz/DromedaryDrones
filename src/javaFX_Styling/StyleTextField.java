package javaFX_Styling;
import javafx.scene.control.TextField;

public class StyleTextField extends TextField{
	
	public StyleTextField() {
		super();
		this.setStyle("-fx-font-size: 12pt;");
		this.setPrefWidth(100);

	}
	public StyleTextField(String text) {
		super(text);
		this.setText(text);
		this.setStyle("-fx-font-size: 12pt;");
		this.setPrefWidth(100);

	}
}
