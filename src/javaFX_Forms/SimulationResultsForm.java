package javaFX_Forms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javaClasses.GreedyAlgorithm;
import javaClasses.Location;
import javaClasses.Results;
import javaClasses.RoutingAlgorithm;
import javaClasses.Simulation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SimulationResultsForm extends Form {

	private Results[] simResults;

	public SimulationResultsForm(SceneController sc, BorderPane layout) {
		super(sc, layout);

		// set up saveResults event handler
		BorderPane bottom = (BorderPane) layout.getBottom();
		Button save = (Button) bottom.getLeft();

		save.setOnAction((event) -> {
			saveResults();
		});
	}

	public void runSimulation(Location location) throws Exception {
		//run the simulation to get the results
		Simulation sim = new Simulation(location);

		RoutingAlgorithm ra = new GreedyAlgorithm();
		//RoutingAlgorithm ra = new BacktrackingSearch();
		simResults = sim.runSimulation(ra);

		//create the graph
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Order Time (s)");
		yAxis.setLabel("Percentage of Orders");

		LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

		lineChart.setTitle("Simulation Results");
		
		// largest time should be the same for both algorithm so that buckets line up
		double largestTime = 0;
		for (Results r : simResults) {
			List<Double> times = r.getTimes();

			Collections.sort(times);
			
			if (times.get(times.size() - 1) > largestTime) {
				largestTime = times.get(times.size() - 1);
			}
			
		}

		for (int index = 0; index < simResults.length; index++) {
			
			Results r = simResults[index];
			
			Series<Number, Number> series = new XYChart.Series<>();

			int numBuckets = 25;
			double[] buckets = new double[numBuckets + 1];
			int[] counts = new int[numBuckets];

			List<Double> times = r.getTimes();

			// sort times so that bucketing is easier
			Collections.sort(times);

			for (int i = 0; i <= numBuckets; i++) {
				buckets[i] = i * (largestTime / numBuckets);
			}

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
			
			if (index == 0) {
				series.setName("FIFO");
			} else if (index == 1) {
				series.setName("Knapsack");
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
		values.setSpacing(30);
		Label fifo = new Label("FIFO Worst Time: " + simResults[0].worstTime() + "\n" +
				"FIFO Average Time: " + simResults[0].averageTime());
		Label knapsack = new Label("Knapsack Worst Time: " + simResults[1].worstTime() + "\n" +
				"Knapsack Average Time: " + simResults[1].averageTime());
		values.getChildren().addAll(fifo, knapsack);
		insideResults.setBottom(values);

		//put the results (graph, max, etc) in the middle of the screen
		layout.setCenter(insideResults);

	}

	public void saveResults() {
		if (simResults != null) {
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName("results");
			fileChooser.setTitle("Save Results");
			
			ExtensionFilter csv = new ExtensionFilter("CSV (Comma delimited)", "*.csv");
			fileChooser.getExtensionFilters().add(csv);
			fileChooser.setSelectedExtensionFilter(csv);
			
			File file = fileChooser.showSaveDialog(null);

			try {
				FileWriter write = new FileWriter(file);
				
				StringBuilder sb = new StringBuilder();
				
				sb.append("FIFO\n");
				
				sb.append(simResults[0].getTimesString(","));
				sb.append("\n");
				sb.append("Worst,");
				sb.append(simResults[0].worstTime());
				sb.append("\n");
				sb.append("Average,");
				sb.append(simResults[0].averageTime());
				sb.append("\n");
				sb.append("\n");
				
				sb.append("Knapsack\n");
				
				sb.append(simResults[1].getTimesString(","));
				sb.append("\n");
				sb.append("Worst,");
				sb.append(simResults[1].worstTime());
				sb.append("\n");
				sb.append("Average,");
				sb.append(simResults[1].averageTime());
				sb.append("\n");
				sb.append("\n");
				
				write.write(sb.toString());
				
				write.close();
				
			} catch (IOException e) {
				System.out.println("File error saving results; file name=" + file.getName());
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Results list empty in saveResults");
				e.printStackTrace();
			}

		}
	}


}
