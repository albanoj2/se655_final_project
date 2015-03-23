package edu.earu.se655.finalproj.sp2015.teama;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.GraphFactory;

import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSetLoader;
import edu.earu.se655.finalproj.sp2015.teama.tinkerpop.TinkerpopSocialNetworkDatabaseExecutor;

public class Main {
	
	private static int ITERATION_COUNT = 30;

	public static void main(String[] args) throws Exception {
		
		// Create the data sets
		List<DataSet> dataSets = Main.createDataSets();
		
		// A list containing all the database executors
		List<DatabaseExecutor> databaseExecutors = new ArrayList<>();
		
		// A list of the configurations (representing each graph type)
		@SuppressWarnings("serial")
		List<String> configurations = new ArrayList<String>() {{
			add("graph_configs/neo4j.properties");
			add("graph_configs/orientdb.properties");
			add("graph_configs/rexster.properties");
			add("graph_configs/tinker.properties");
		}};

		// Add the executors for each of the algorithms and workloads
		databaseExecutors.addAll(Main.createExecutors(TinkerpopSocialNetworkDatabaseExecutor.class, configurations, dataSets));
		
		for (DatabaseExecutor executor : databaseExecutors) {
			// Repeat for each of the executors
			List<Long> executionTimes = new ArrayList<>();
			
			// Header for execution
			System.out.println("Executing " + executor + "...");
			
			// Populate the database
			System.out.print("\t+ Populating...");
			executor.populate();
			System.out.println("completed with " + executor.getDataSet().getNumberOfNodes() + " nodes and " + executor.getDataSet().getNumberOfRelationships() + " relationships.");

			for (int i = 0; i < ITERATION_COUNT; i++) {
				// Execute the algorithm
				executor.tick().execute().tock();
				
				// Record the execution time
				executionTimes.add(executor.getEllapsedTime());
				
				if (i != 0 && i % 5 == 0) {
					// Report the completion of every 5th iteration 
					System.out.println("\t+ Completed iteration [" + i + "/" + ITERATION_COUNT + "]");
				}
			}
			
			// Output the results
			System.out.println("\t+ Mean: " + executionTimes.stream().mapToLong(Long::longValue).average().getAsDouble());
			System.out.println("\t+ Sample values:" + executionTimes + " in ms");
			
			// Shutdown the executor
			executor.shutdown();
		}
	}
	
	public static List<DataSet> createDataSets () {
		
		// The list containing the data sets
		List<DataSet> dataSets = new ArrayList<>();
		
		// Notify the user that the data sets are being loaded
		System.out.print("Loading social network data sets...");
		
		// Add the data sets to the list
		dataSets.add(new DataSetLoader().load("social_network/small.json").getDataSet());
		dataSets.add(new DataSetLoader().load("social_network/medium.json").getDataSet());
		dataSets.add(new DataSetLoader().load("social_network/large.json").getDataSet());
		dataSets.add(new DataSetLoader().load("social_network/very_large.json").getDataSet());
		
		// The loading is complete
		System.out.println("done.");
		
		return dataSets;
	}
	
	public static <T extends DatabaseExecutor> List<DatabaseExecutor> createExecutors (Class<T> executorClass, List<String> configurations, List<DataSet> dataSets) {
		
		// A list of executors to collect
		List<DatabaseExecutor> executors = new ArrayList<>();
		
		configurations.forEach((configuration) -> {
			// Iterate through the configurations
			dataSets.forEach((dataSet) -> {
				
				try {
					// Create an executor for each configuration and data set
					executors.add(executorClass.getConstructor(Graph.class, DataSet.class).newInstance(GraphFactory.open(configuration), dataSet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		
		return executors;
	}

}
