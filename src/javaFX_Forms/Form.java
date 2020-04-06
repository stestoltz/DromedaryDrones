package javaFX_Forms;

import javafx.scene.layout.BorderPane;

public abstract class Form {
	
	protected SceneController sc;
	protected BorderPane layout;
	
	public Form(SceneController sc, BorderPane layout) {
		this.sc = sc;
		this.layout = layout;
	}

	public BorderPane getLayout() {
		return layout;
	}
}
