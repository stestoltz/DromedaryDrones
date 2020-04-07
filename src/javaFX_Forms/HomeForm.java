package javaFX_Forms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javaClasses.GreedyAlgorithm;
import javaClasses.Location;
import javaClasses.Results;
import javaClasses.RoutingAlgorithm;
import javaClasses.Simulation;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class HomeForm extends Form {
	
	public HomeForm(SceneController sc, BorderPane layout) throws FileNotFoundException {
		super(sc, layout);
		
		//create map
		Image map = new Image(new FileInputStream("res/map.jfif"));
		ImageView mapView = new ImageView(map);
		mapView.setPreserveRatio(true);
		mapView.setFitHeight(500);
		
		layout.setCenter(mapView);
		
		BorderPane top = (BorderPane) layout.getTop();
		MenuBar menuBar = (MenuBar) top.getRight();
		Menu menu = menuBar.getMenus().get(0);
		
		MenuItem mapItem = menu.getItems().get(0);
		
		mapItem.setOnAction((event)->{
			this.sc.switchToMap();
		});
		
		MenuItem foodItem = menu.getItems().get(1);
		
		foodItem.setOnAction((event)->{
			this.sc.switchToFood();
		});
		
		MenuItem mealItem = menu.getItems().get(2);
		
		mealItem.setOnAction((event)->{
			this.sc.switchToMeal();
		});
		
		MenuItem droneItem = menu.getItems().get(4);
		
		droneItem.setOnAction((event)->{
			this.sc.switchToDrone();
		});
		
		
		BorderPane bottom = ((BorderPane) layout.getBottom());
		Button startSimulation = ((Button) bottom.getCenter());
		
		startSimulation.setOnAction((event) -> {
			Location groveCity = new Location("Grove City", "SAC");
			Simulation sim = new Simulation(groveCity);
			
			RoutingAlgorithm ra = new GreedyAlgorithm();
			//RoutingAlgorithm ra = new BacktrackingSearch();
			Results[] simResults = sim.runSimulation(ra);
			
			for (Results r : simResults) {
				//System.out.println(r.getTimes());
				System.out.println("Number of orders: " + r.getTimes().size());
				
				try {
					System.out.println("Worst (s): " + r.worstTime());
					System.out.println("Average (s): " + r.averageTime());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("\n");
			}
			
			NumberAxis xAxis = new NumberAxis();
			NumberAxis yAxis = new NumberAxis();

			xAxis.setLabel("Order Time (s)");
			yAxis.setLabel("Percentage of Orders");

			LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

			lineChart.setTitle("Simulation Results");

			for (Results r : simResults) {
				Series<Number, Number> series = new XYChart.Series<>();

				int numBuckets = 25;
				double[] buckets = new double[numBuckets + 1];
				int[] counts = new int[numBuckets];

				List<Double> times = r.getTimes();

				for (int i = 0; i < times.size(); i++) {
					if (times.get(i) > 100000) {
						System.out.println("Giant time at index " + i + ": " + times.get(i));
						times.set(i, 0.0);
					}
				}

				Collections.sort(times);

				double largestTime = times.get(times.size() - 1);

				for (int i = 0; i <= numBuckets; i++) {
					buckets[i] = i * (largestTime / numBuckets);
				}

				System.out.println(Arrays.asList(buckets));

				// build histogram
				Iterator<Double> itr = times.iterator();

				int currentBucket = 0;

				while (itr.hasNext()) {
					double next = itr.next();

					// if in bucket
					if (buckets[currentBucket] <= next && next <= buckets[currentBucket + 1]) {
						counts[currentBucket]++;
					} else {

						// not in bucket - find right bucket
						while (!(buckets[currentBucket] <= next && next <= buckets[currentBucket + 1])) {
							currentBucket++;
						}

						counts[currentBucket]++;
					}
				}

				for (int i = 0; i < numBuckets; i++) {
					series.getData().add(new XYChart.Data<Number, Number>((buckets[i] + buckets[i + 1]) / 2, ((double)counts[i] / times.size()) * 100));
				}

				lineChart.getData().addAll(series);
			}
			//graph has been created
			
			
			//now set up the screen
			
			//contain results in this border pane
			BorderPane insideResults = new BorderPane();
			//put the chart in the center of the inside borderpane
			insideResults.setCenter(lineChart);
			//put the maximum and average in the bottom of the inside borderpane
			HBox values = new HBox();
			values.setAlignment(Pos.CENTER);
			values.setSpacing(10);
			Label fifo = new Label("This" + "\n" + "new line");
			try {
				fifo = new Label("FIFO Worst Time: " + simResults[0].worstTime() + "\n" +
											"FIFO Average Time" + simResults[0].averageTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Label knapsack = new Label("This" + "\n" + "new line");
			try {
				knapsack = new Label("Knapsack Worst Time: " + simResults[1].worstTime() + "\n" +
												"Knapsack Average Time" + simResults[1].averageTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			values.getChildren().addAll(fifo, knapsack);
			insideResults.setBottom(values);
			
			//put the results (graph, max, etc) in the middle of the screen
			layout.setCenter(insideResults);
		});
	}

}