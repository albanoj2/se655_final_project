package edu.earu.se655.finalproj.sp2015.teama;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSetLoader;
import edu.earu.se655.finalproj.sp2015.teama.tinkerpop.TinkerpopGetPropertyDatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.tinkerpop.TinkerpopShiftRelationshipDatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.tinkerpop.TinkerpopSocialNetworkDatabaseExecutor;

public class Main {
	
	private static int ITERATION_COUNT = 30;
	
	public static void main (String[] args) throws Exception {
		new Main().main();
	}

	public void main () throws Exception {
		
		// Create the data sets
		List<DataSet> dataSets = Main.createDataSets();
		
		// A list containing all the database executors
		List<DatabaseExecutor> databaseExecutors = new ArrayList<>();
		
		// A list of the executors (represented by classes) to be executed
		@SuppressWarnings("serial")
		List<Class<? extends DatabaseExecutor>> executors = new ArrayList<Class<? extends DatabaseExecutor>>() {{
			add(TinkerpopSocialNetworkDatabaseExecutor.class);
			add(TinkerpopGetPropertyDatabaseExecutor.class);
			add(TinkerpopShiftRelationshipDatabaseExecutor.class);
		}};
		
		// A list of the configurations (representing each graph type)
		@SuppressWarnings("serial")
		List<String> configurations = new ArrayList<String>() {{
			add("graph_configs/neo4j.properties");
			add("graph_configs/orientdb.properties");
			add("graph_configs/rexster.properties");
			add("graph_configs/tinker.properties");
		}};

		// Add the executors for each of the algorithms and workloads
		databaseExecutors.addAll(this.createExecutors(executors, configurations, dataSets));
		
		for (DatabaseExecutor executor : databaseExecutors) {
			// Repeat for each of the executors
			List<Long> executionTimes = new ArrayList<>();

			// Open the database executor
			executor.open();
			
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
	
	@SuppressWarnings("rawtypes")
	public List<DatabaseExecutor> createExecutors (List<Class<? extends DatabaseExecutor>> executorClasses, List<String> configurations, List<DataSet> dataSets) {
		
		// A list of executors to collect
		List<DatabaseExecutor> executors = new ArrayList<>();
		
		executorClasses.forEach((executorClass) -> {
			// Iterate through each of the executor types
			configurations.forEach((configuration) -> {
				// Iterate through the configurations
				dataSets.forEach((dataSet) -> {
					// Iterate through each of the data sets
					
					// Ensure that the databases paths are unique when needed
					Map configurationMap = this.alterConfigurationDatabasePath(configuration);
					
					try {
						// Create an executor for each configuration and data set
						executors.add(executorClass.getConstructor(Map.class, DataSet.class).newInstance(configurationMap, dataSet));
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				});
			});
		});
		
		return executors;
	}
	
	@SuppressWarnings("rawtypes")
	private Map alterConfigurationDatabasePath (String configurationFilePath) {
		
		Properties properties = new Properties();
		InputStream in;
		try {
			in = new FileInputStream(configurationFilePath);
			properties.load(in);
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if (properties.getProperty("blueprints.neo4j.directory") != null) {
			// The properties contains an entry for the Neo4j directory
			String directory = properties.getProperty("blueprints.neo4j.directory");
			
			// Split the directory by name and extension
			String[] splitDirectory = directory.split("\\.");
			
			// Add the iteration number to the directory name and join it
			properties.setProperty("blueprints.neo4j.directory", splitDirectory[0] + "-" + UUID.randomUUID() + splitDirectory[1]);
		}
		
		return properties;
	}

}
