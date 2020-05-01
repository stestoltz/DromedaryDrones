package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javaClasses.DeliveryPoint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class MapForm extends Form {
	
	private String ACTIVE_ICON = "red-dot";
	private String INACTIVE_ICON = "grey";
	private String HOME_ICON = "green";
	private String HIGHLIGHT_ACTIVE = "yellow-dot";
	private String HIGHLIGHT_INACTIVE = "yellow";
	
	private ListView<HBox> pointsView;
	
	private DeliveryPoint home;
	private HBox homeHBox;
	private List<DeliveryPoint> points;
	
	private Connector connector;
	private JSObject jsObject;
	
	private String locationName;
	
	public MapForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		super(sc, layout);
		
		pointsView = new ListView<>();
		
		connector = new Connector(this);

		//create map
		Image map = new Image(new FileInputStream("res/map2.jpg"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitWidth(900);
		
		mapView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			System.out.println(event.getX() + " " + event.getY());
		});
		
		HBox listButtons = new HBox();
		Button edit = new Button("Edit");
		Button delete = new Button("Delete");
		listButtons.getChildren().add(edit);
		listButtons.getChildren().add(delete);
		
		delete.setOnAction((event) -> {
			
			if (jsObject != null) {
				HBox hbox = pointsView.getSelectionModel().getSelectedItem();
				
				if (hbox != null) {
					DeliveryPoint selectedPoint = getDeliveryPoint(hbox);
					
					if (selectedPoint != home) {
					
						points.remove(selectedPoint);
						pointsView.getItems().remove(hbox);
						
						jsObject.call("deleteMarker", selectedPoint.getName());
					}
				}
			}
		});
		
		edit.setOnAction((event) -> {
			HBox hbox = pointsView.getSelectionModel().getSelectedItem();
			
			if (hbox != null) {
				TextField text = (TextField) hbox.getChildren().get(0);
				
				String currentVal = text.getText();
				
				// temporarily listener to prevent empty strings in text fields
				ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
				    @Override
				    public void changed(ObservableValue<? extends Boolean> hasFocus, Boolean oldValue, Boolean newValue) {
				    	// lost focus
						if (!newValue) {
							if (text.getText().equals("") || isNameTaken(text.getText())) {
								// if empty, revert to old
								text.setText(currentVal);
							} else {
							
								// change the name in the list
								getDeliveryPoint(hbox).setName(text.getText());
								
								jsObject.call("editMarker", currentVal, text.getText());
							}

							// remove this listener
							text.focusedProperty().removeListener(this);
						}
				    }
				};
				
				text.focusedProperty().addListener(listener);
				
				text.setEditable(true);
				text.requestFocus();
			}
		});
		
		VBox left = new VBox();
		left.getChildren().add(pointsView);
		left.getChildren().add(listButtons);
		
		//layout.setCenter(mapView);
		layout.setLeft(left);
		
		layout.setPadding(new Insets(10, 10, 10, 10));
		left.setPadding(new Insets(10, 10, 10, 10));
		left.setSpacing(10);
		listButtons.setSpacing(10);
		
		
		BorderPane top = ((BorderPane) layout.getTop());
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());
		
		top.setPadding(new Insets(10, 10, 10, 10));
		bottom.setPadding(new Insets(10, 10, 10, 10));
		
		cancel.setOnAction((event) -> {
			this.sc.switchToHome();
		});
		
		save.setOnAction((event) -> {
			
			Map<DeliveryPoint, Boolean> endPoints = new HashMap<>();

			boolean atLeastOneActivePoint = false;
			
			for (int i = 0; i < pointsView.getItems().size(); i++) {
				HBox hbox = pointsView.getItems().get(i);
				DeliveryPoint dp = getDeliveryPoint(hbox);
				
				if (dp != home) {
				
					boolean active = getActive(hbox);
					endPoints.put(getDeliveryPoint(hbox), active);
					
					if (active) {
						atLeastOneActivePoint = true;
					}
				}
			}
			
			if (atLeastOneActivePoint) {
				this.sc.replaceDeliveryPoints(endPoints, home);
				this.sc.switchToHome();
			} else {
				System.out.println("No active delivery points selected");
			}
		});
	}
	
	public boolean isNameTaken(String name) {
		if (name.equals(home.getName())) {
			return true;
		}
		
		for (DeliveryPoint dp : points) {
			if (name.equals(dp.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * load a hashmap of delivery points into the ListView<HBox>
	 * @param points
	 */
	public void loadPoints(Map<DeliveryPoint, Boolean> pointsMap, DeliveryPoint home, String locationName) {
		this.points = new ArrayList<>();
		
		pointsView.getItems().clear();
		
		this.locationName = locationName;
		
		this.home = home;
		
		homeHBox = new HBox();
		
		TextField textHome = new TextField();
		textHome.setText(home.toString());
		textHome.setEditable(false);
		
		homeHBox.getChildren().addAll(textHome);
		pointsView.getItems().add(homeHBox);
		
		pointsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HBox>() {
		    @Override
		    public void changed(ObservableValue<? extends HBox> observable, HBox oldValue, HBox newValue) {
		        
		    	//set color for old highlighted hbox
		    	if (oldValue != null) {
		    		if (oldValue == homeHBox) {
				        
				        jsObject.call("setMarkerColor", home.getName(), HOME_ICON);
		    			
		    		} else {
				    	DeliveryPoint dp = getDeliveryPoint(oldValue);
				    	
				        Boolean active = getActive(oldValue);
				        String color = active ? ACTIVE_ICON : INACTIVE_ICON;
				        
				        jsObject.call("setMarkerColor", dp.getName(), color);
		    		}
		    	}
		    	
		    	
		    	// set color for newly highlighted hbox
		    	DeliveryPoint dp = getDeliveryPoint(newValue);
		        
		        Boolean active = getActive(newValue);
		        String color = active ? HIGHLIGHT_ACTIVE : HIGHLIGHT_INACTIVE;
		        
		        jsObject.call("setMarkerColor", dp.getName(), color);
		    }
		});
		
		ChangeListener<Boolean> homeListener = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> hasFocus, Boolean oldValue, Boolean newValue) {
		    	// gained focus
				if (newValue) {
					pointsView.getSelectionModel().select(homeHBox);
				}
		    }
		};
		
		textHome.focusedProperty().addListener(homeListener);
		
		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        
        // listener for when engine is loaded
        webEngine.getLoadWorker().stateProperty().addListener(
	        new ChangeListener<State>() {
	            public void changed(ObservableValue ov, State oldState, State newState) {
	                if (newState == State.SUCCEEDED) {
	                	
	            		jsObject = (JSObject) webEngine.executeScript("window");
	            		
	            		jsObject.call("loadMap", home.getLat(), home.getLng(), ACTIVE_ICON);
	            		
	            		//jsObject.call("geocode", locationName);
	            		
	            		jsObject.setMember("java", connector);
	                	
	                	// add the home point to the map
	            		jsObject.call("addMarker", home.getLat(), home.getLng(), home.getName(), HOME_ICON);
	                	
	                	// loop to add delivery points to the map
	            		for (DeliveryPoint dp : pointsMap.keySet()) {
	            			addDeliveryPoint(dp, pointsMap.get(dp));
	            			
	            			String iconType = pointsMap.get(dp) ? ACTIVE_ICON : INACTIVE_ICON;
	            			
	            			jsObject.call("addMarker", dp.getLat(), dp.getLng(), dp.getName(), iconType);
	            		}

	                }
	            }
	        });
		
		webEngine.load(getClass().getResource("/javaFX_Forms/map/map.html").toString());
		
		layout.setCenter(webView);
	}
	
	/**
	 * Retrieve a DeliveryPoint from a given HBox
	 * @param hbox
	 * @return
	 */
	public DeliveryPoint getDeliveryPoint(HBox hbox) {
		// if first hbox - home delivery point
		if (hbox == homeHBox) {
			return home;
		}
		
		for (int i = 0; i < pointsView.getItems().size(); i++) {
			if (hbox == pointsView.getItems().get(i)) {
				return points.get(i - 1);
			}
		}
		
		return null;
	}
	
	private DeliveryPoint getDeliveryPoint(String name) {
		if (home.getName().equals(name)) {
			return home;
		}
		
		for (DeliveryPoint dp : points) {
			if (dp.getName().equals(name)) {
				return dp;
			}
		}
		
		return null;
	}
	
	public void addDeliveryPoint(DeliveryPoint dp, boolean active) {
		this.points.add(dp);
		
		HBox hbox = new HBox();
		
		TextField text = new TextField();
		text.setText(dp.toString());
		text.setEditable(false);
		
		CheckBox check = new CheckBox();
		check.setSelected(active);
		
		check.setOnAction((event) -> {
			
			boolean isActive = check.isSelected();
			boolean isHighlighted = hbox == pointsView.getSelectionModel().getSelectedItem();
			
			String color;
			
			if (isActive) {
				if (isHighlighted) {
					color = HIGHLIGHT_ACTIVE;
				} else {
					color = ACTIVE_ICON;
				}
			} else {
				if (isHighlighted) {
					color = HIGHLIGHT_INACTIVE;
				} else {
					color = INACTIVE_ICON;
				}
			}
			
			jsObject.call("setMarkerColor", dp.getName(), color);
		});
		
		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> hasFocus, Boolean oldValue, Boolean newValue) {
		    	// gained focus
				if (newValue) {
					pointsView.getSelectionModel().select(hbox);
				}
		    }
		};
		
		text.focusedProperty().addListener(listener);
		
		hbox.getChildren().addAll(text, check);
		pointsView.getItems().add(hbox);
	}
	
	/**
	 * Retrieve an active value for a DeliveryPoint from a given HBox
	 * @param hbox
	 * @return
	 */
	public boolean getActive(HBox hbox) {
		for (int i = 0; i < points.size(); i++) {
			if (hbox == pointsView.getItems().get(i + 1)) {
				CheckBox check = (CheckBox) hbox.getChildren().get(1);
				
				return check.isSelected();
			}
		}
		
		return false;
	}
	
	public double[] parseLatLngFromJS(String latLng) {
		
		double[] output = new double[2];
		
		String trimmed = latLng.substring(1, latLng.length() - 1);
		String[] points = trimmed.split(", ");
		double lat = Double.parseDouble(points[0]);
		double lng = Double.parseDouble(points[1]);
		
		output[0] = lat;
		output[1] = lng;
		
		return output;
	}

	public class Connector {
		
		private MapForm parent;
		
		public Connector(MapForm parent) {
			this.parent = parent;
		}
		
	    public void addDeliveryPoint(Object coords, Object name) {
	    	System.out.println("Connector called: addDeliveryPoint");
	    	
	    	String coordsString = (String) coords;
	    	String pointName = (String) name;
	    	
	    	double[] latLng = parent.parseLatLngFromJS(coordsString);
	    	
	    	DeliveryPoint dp = new DeliveryPoint(pointName, latLng[0], latLng[1]);
	    	
	    	parent.addDeliveryPoint(dp, true);
    		
    		System.out.println("Added point: " + latLng[0] + " " + latLng[1]);
	    }
	    
	    public void moveDeliveryPoint(Object coords, Object name) {
	    	System.out.println("Connector called: moveDeliveryPoint");
	    	
	    	String coordsString = (String) coords;
	    	String pointName = (String) name;
	    	
	    	double[] latLng = parent.parseLatLngFromJS(coordsString);
	    	
	    	DeliveryPoint dp = getDeliveryPoint(pointName);
	    	
	    	if (dp != null) {
	    		dp.setLat(latLng[0]);
	    		dp.setLng(latLng[1]);
	    	}
    		
    		System.out.println("Moved point: " + latLng[0] + " " + latLng[1]);
	    	
	    }
	    
	    public void log(Object msg) {
	    	System.out.println((String)msg);
	    }
	}
}