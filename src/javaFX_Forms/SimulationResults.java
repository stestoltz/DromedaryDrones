package javaFX_Forms;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javaClasses.Results;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class SimulationResults extends Application {

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
	
	
	layout.setCenter(lineChart);
	
///////////////////////////////
	//create pane and add max and average to it
	BorderPane resultBottom = new BorderPane();
	bottom.setLeft();
	bottom.setRight();
	bottom.setCenter();
	//do stuff
	
	Label label = new Label("A label with custom font set.");

	label.setFont(new Font("Arial", 24));
	
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");

	    Button buttonCurrent = new Button("Current");
	    buttonCurrent.setPrefSize(100, 20);

	    Button buttonProjected = new Button("Projected");
	    buttonProjected.setPrefSize(100, 20);
	    hbox.getChildren().addAll(buttonCurrent, buttonProjected);
}
