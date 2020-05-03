package javaFX_Forms;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javaClasses.DeliveryPoint;
import javaFX_Styling.StyleButton;
import javaFX_Styling.StyleLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class MapForm extends Form {
	
	// used for JavaScript map icons
	private final String ACTIVE_ICON = "red-dot";
	private final String INACTIVE_ICON = "grey";
	private final String HOME_ICON = "green";
	private final String HIGHLIGHT_ACTIVE = "yellow-dot";
	private final String HIGHLIGHT_INACTIVE = "yellow";
	
	private ListView<HBox> pointsView;
	
	private DeliveryPoint home;
	private HBox homeHBox;
	private List<DeliveryPoint> points;
	
	private WebEngine webEngine;
	private Connector connector;
	private JSObject javascript;
	
	public MapForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		super(sc, layout);
		
		pointsView = new ListView<>();
		pointsView.setStyle("-fx-font-size: 10pt;");
		
		connector = new Connector(this);
		
		WebView webView = new WebView();
		webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        
        // listener for when javascript engine is loaded
        webEngine.getLoadWorker().stateProperty().addListener(
	        new ChangeListener<State>() {
	            public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
	                if (newState == State.SUCCEEDED) {
	                	
	                	// setup javascript
	            		javascript = (JSObject) webEngine.executeScript("window");
	            		
	            		// give javascript a way to communicate with us
	            		javascript.setMember("java", connector);
	            		
	            		// allow the user to come to this form
	            		sc.enableMapping();
	                }
	            }
	        });
		
		webEngine.load(getClass().getResource("/javaFX_Forms/map/map.html").toString());

		//description
		Label description = new StyleLabel("Click the map at a given coordinate that you wish to add. "
				+ "This will add the new delivery point to the list. Delivery points can be toggled on "
				+ "and off using the checkboxes. "
				+ "The map displays all delivery points and highlights points that will be used in the simulation.");
		
		description.setWrapText(true);
		description.setPrefHeight(100);
		BorderPane center = new BorderPane();
		center.setTop(description);
		center.setCenter(webView);
		layout.setCenter(center);
		
		HBox listButtons = new HBox();
		Button edit = new StyleButton("Edit");
		Button delete = new StyleButton("Delete");
		listButtons.getChildren().add(edit);
		listButtons.getChildren().add(delete);
		
		// delete the selected delivery point
		delete.setOnAction((event) -> {
			
			HBox hbox = pointsView.getSelectionModel().getSelectedItem();

			if (hbox != null) {
				DeliveryPoint selectedPoint = getDeliveryPoint(hbox);

				// cannot delete home
				if (selectedPoint != home) {

					points.remove(selectedPoint);
					pointsView.getItems().remove(hbox);

					if (javascript != null) {
						javascript.call("deleteMarker", selectedPoint.getName());
					}
				}
			}
		});
		
		// edit the name of the selected delivery point
		edit.setOnAction((event) -> {
			HBox hbox = pointsView.getSelectionModel().getSelectedItem();
			
			if (hbox != null) {
				TextField text = (TextField) hbox.getChildren().get(0);
				
				String currentVal = text.getText();
				
				// temporarily listener to prevent invalid strings in text fields
				// reverts name to old name if user leaves it empty or the same name as another point
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
								
								javascript.call("editMarker", currentVal, text.getText());
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
		layout.setLeft(left);
		
		layout.setPadding(new Insets(10, 10, 10, 10));
		left.setPadding(new Insets(10, 10, 10, 10));
		left.setSpacing(10);
		listButtons.setSpacing(10);
		
		
		BorderPane top = ((BorderPane) layout.getTop());
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button cancel = ((Button) bottom.getLeft());
		Button save = ((Button) bottom.getRight());
		
		top.setPadding(new Insets(0, 10, 0, 10));
		bottom.setPadding(new Insets(10, 10, 10, 10));
		
		// user does not want changes to go into effect
		cancel.setOnAction((event) -> {
			javascript.call("clearMap");
			this.sc.switchToHome();
		});
		
		// commit changes to location object, return to home
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
				javascript.call("clearMap");
				this.sc.replaceDeliveryPoints(endPoints, home);
				this.sc.switchToHome();
			} else {
				this.sc.runErrorPopUp("No active delivery points selected.");
			}
		});
		
		
		// change highlighting when selected point changes
		pointsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HBox>() {
		    @Override
		    public void changed(ObservableValue<? extends HBox> observable, HBox oldValue, HBox newValue) {
		        
		    	// set color for old highlighted hbox
		    	if (oldValue != null) {
		    		if (oldValue == homeHBox) {
				        
				        javascript.call("setMarkerColor", home.getName(), HOME_ICON);
		    			
		    		} else {
				    	DeliveryPoint dp = getDeliveryPoint(oldValue);
				    	
				    	if (dp != null) {
				    	
					        Boolean active = getActive(oldValue);
					        String color = active ? ACTIVE_ICON : INACTIVE_ICON;
					        
					        javascript.call("setMarkerColor", dp.getName(), color);
				    	}
		    		}
		    	}
		    	
		    	// set color for newly highlighted hbox
		    	DeliveryPoint dp = getDeliveryPoint(newValue);
		        
		        Boolean active = getActive(newValue);
		        String color = active ? HIGHLIGHT_ACTIVE : HIGHLIGHT_INACTIVE;
		        
		        javascript.call("setMarkerColor", dp.getName(), color);
		        javascript.call("centerOnMarker", dp.getName());
		    }
		});
	}
	
	/**
	 * @param name
	 * @return true if any other delivery point has that name
	 */
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
	 * Given just a name, load in where Google thinks that is and make home point there too
	 * @param locationName
	 */
	public void loadEmptyLocation(String locationName) {
		this.points = new ArrayList<>();
		
		pointsView.getItems().clear();
		
		homeHBox = new HBox();
		pointsView.getItems().add(homeHBox);
		
		// center on given location
		javascript.call("geocodeNewLocation", locationName, ACTIVE_ICON, HOME_ICON);
	}
	
	/**
	 * Create the home point from lat and lng provided by JavaScript (called after loadEmptyLocation)
	 * @param name
	 * @param lat
	 * @param lng
	 */
	public void setHomePoint(String name, double lat, double lng) {
		
		TextField textHome = new TextField();
		textHome.setEditable(false);
		
		homeHBox.getChildren().addAll(textHome);
		
		home = new DeliveryPoint(name, lat, lng);
		
		ChangeListener<Boolean> homeListener = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> hasFocus, Boolean oldValue, Boolean newValue) {
		    	// gained focus
				if (newValue) {
					pointsView.getSelectionModel().select(homeHBox);
				}
		    }
		};
		
		textHome.setText(home.toString());
		
		textHome.focusedProperty().addListener(homeListener);
	}
	
	/**
	 * load a hashmap of delivery points into the ListView<HBox>
	 * @param points
	 */
	public void loadPoints(Map<DeliveryPoint, Boolean> pointsMap, DeliveryPoint home, String locationName) {
		this.points = new ArrayList<>();
		
		pointsView.getItems().clear();
		
		this.home = home;
		
		homeHBox = new HBox();
		
		TextField textHome = new TextField();
		textHome.setText(home.toString());
		textHome.setEditable(false);
		
		homeHBox.getChildren().addAll(textHome);
		pointsView.getItems().add(homeHBox);
		
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
		
		javascript.call("loadMap", home.getLat(), home.getLng(), ACTIVE_ICON);
		
    	// add the home point to the map
		javascript.call("addMarker", home.getLat(), home.getLng(), home.getName(), HOME_ICON);


    	// loop to add delivery points to the map
		for (DeliveryPoint dp : pointsMap.keySet()) {
			addDeliveryPoint(dp, pointsMap.get(dp));
			
			String iconType = pointsMap.get(dp) ? ACTIVE_ICON : INACTIVE_ICON;
			
			javascript.call("addMarker", dp.getLat(), dp.getLng(), dp.getName(), iconType);
		}
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
	
	/**
	 * Retrieve a DeliveryPoint given a name
	 * @param name
	 * @return
	 */
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
	
	/**
	 * Retrieve an HBox given a delivery point
	 * @param dp
	 * @return
	 */
	private HBox getHBox(DeliveryPoint dp) {
    	if (dp == home) {
    		return homeHBox;
    	} else {

    		for (HBox hbox : pointsView.getItems()) {
    			if (getDeliveryPoint(hbox) == dp) {
    				return hbox;
    			}
    		}
    	}
    	
    	return null;
	}
	
	/**
	 * Add a delivery point with its active status
	 * @param dp
	 * @param active
	 */
	public void addDeliveryPoint(DeliveryPoint dp, boolean active) {
		this.points.add(dp);
		
		HBox hbox = new HBox(10);
		
		TextField text = new TextField();
		text.setText(dp.toString());
		text.setEditable(false);
		
		CheckBox check = new CheckBox();
		check.setSelected(active);
		
		// respond correctly to activation/deactivation and highlighting/unhighlighting
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
			
			javascript.call("setMarkerColor", dp.getName(), color);
		});
		
		// select hbox if user clicks on text box
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
	
	/**
	 * Given a string from javascript in the format (lat, lng), convert to a double[] with 2 values
	 * @param latLng
	 * @return
	 */
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

	/**
	 * Called by javascript with "java.methodName"
	 * Allows javascript to communicate map events with Java
	 */
	public class Connector {
		
		private MapForm parent;
		
		public Connector(MapForm parent) {
			this.parent = parent;
		}
		
		/**
		 * Called in a new location by javascript, after getting latlng from Google Geocoding
		 * @param coords
		 * @param name
		 */
		public void addHomePointToEmptyLocation(Object coords, Object name) {
    		
			String coordsString = (String) coords;
	    	String pointName = (String) name;
	    	
	    	double[] latLng = parent.parseLatLngFromJS(coordsString);
	    	
	    	parent.setHomePoint(pointName, latLng[0], latLng[1]);
		}
		
		/**
		 * Called after the user clicks the map to add a delivery point
		 * @param coords
		 * @param name
		 */
	    public void addDeliveryPoint(Object coords, Object name) {
	    	
	    	String coordsString = (String) coords;
	    	String pointName = (String) name;
	    	
	    	double[] latLng = parent.parseLatLngFromJS(coordsString);
	    	
	    	DeliveryPoint dp = new DeliveryPoint(pointName, latLng[0], latLng[1]);
	    	
	    	parent.addDeliveryPoint(dp, true);
	    }
	    
	    /**
	     * Called after the user drags a marker on the map
	     * @param coords
	     * @param name
	     */
	    public void moveDeliveryPoint(Object coords, Object name) {
	    	
	    	String coordsString = (String) coords;
	    	String pointName = (String) name;
	    	
	    	double[] latLng = parent.parseLatLngFromJS(coordsString);
	    	
	    	DeliveryPoint dp = getDeliveryPoint(pointName);
	    	
	    	if (dp != null) {
	    		dp.setLat(latLng[0]);
	    		dp.setLng(latLng[1]);
	    	}
	    	
	    }
	    
	    /**
	     * Called after the user clicks a marker on the map
	     * @param name
	     */
	    public void markerClicked(Object name) {
	    	System.out.println("Connector called: markerClicked");
	    	String pointName = (String) name;
	    	
	    	DeliveryPoint dp = getDeliveryPoint(pointName);
	    	
	    	HBox hbox = getHBox(dp);
	    	
	    	pointsView.getSelectionModel().select(hbox);
	    	
	    	pointsView.scrollTo(hbox);
	    	
    		System.out.println("Highlighted marker: " + name);
	    }
	    
	    /**
	     * Allows javascript to print to the java console
	     * @param msg
	     */
	    public void log(Object msg) {
	    	System.out.println((String)msg);
	    }
	}
}