package javaFX_Styling;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StyleButton extends Button{
	
	public StyleButton(String name) {
		super(name);
		this.setText(name);
		this.setStyle("-fx-background-color: Orange;"
				+ "-fx-font-size: 12pt;");
		
		this.setPrefWidth(80);
		
		this.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
	          @Override
	          public void handle(MouseEvent e) {
	            setStyle("-fx-text-fill: White;"
	            		+ "-fx-background-color: Orange;"
	    				+ "-fx-font-size: 12pt;");
	            }
	        });
		
		this.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
	          @Override
	          public void handle(MouseEvent e) {
	            setStyle("-fx-background-color: Orange;"
	    				+ "-fx-font-size: 12pt;");
	          }
	        });
	}
	
	public StyleButton(String name, ImageView image) {
		super(name, image);
		this.setText(name);
		this.setStyle("-fx-background-color: Orange;"
				+ "-fx-font-size: 12pt;");

		this.setPrefWidth(80);
		
		this.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
	          @Override
	          public void handle(MouseEvent e) {
	            setStyle("-fx-text-fill: White;"
	            		+ "-fx-background-color: Orange;"
	    				+ "-fx-font-size: 12pt;");
	            }
	        });
		
		this.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
	          @Override
	          public void handle(MouseEvent e) {
	            setStyle("-fx-background-color: Orange;"
	    				+ "-fx-font-size: 12pt;");
	          }
	        });
	}
}
