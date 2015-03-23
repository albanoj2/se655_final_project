package edu.earu.se655.finalproj.sp2015.teama;

import java.util.ArrayList;
import java.util.List;

import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSetLoader;
import edu.earu.se655.finalproj.sp2015.teama.neo4j.Neo4jSocialNetworkDatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.orientdb.OrientDbSocialNetworkDatabaseExecutor;

public class Main {
	
	private static int ITERATION_COUNT = 30;

	public static void main(String[] args) throws Exception {
		
		// A list containing all the database executors
		List<DatabaseExecutor> databaseExecutors = new ArrayList<>();

		// Add the executors for each of the algorithms and workloads
		databaseExecutors.addAll(Main.createSocialNetworkExecutors());
		
		for (DatabaseExecutor executor : databaseExecutors) {
			// Repeat for each of the executors
			List<Long> executionTimes = new ArrayList<>();
			
			// Header for execution
			System.out.println("Executing " + executor.getClass().getSimpleName() + "...");
			
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
	
	@SuppressWarnings("serial")
	public static List<DatabaseExecutor> createSocialNetworkExecutors () throws Exception {
		
		System.out.print("Loading social network data sets...");
		final DataSet smallDataSet = new DataSetLoader().load("social_network/small.json").getDataSet();
		final DataSet mediumDataSet = new DataSetLoader().load("social_network/medium.json").getDataSet();
		final DataSet largeDataSet = new DataSetLoader().load("social_network/large.json").getDataSet();
		final DataSet veryLargeDataSet = new DataSetLoader().load("social_network/very_large.json").getDataSet();
		System.out.println("done.");
		
		return new ArrayList<DatabaseExecutor>() {{
			// Neo4j executors
			add(new Neo4jSocialNetworkDatabaseExecutor("databases/neo4j/social_network/small.db", smallDataSet));
			add(new Neo4jSocialNetworkDatabaseExecutor("databases/neo4j/social_network/medium.db", mediumDataSet));
			add(new Neo4jSocialNetworkDatabaseExecutor("databases/neo4j/social_network/large.db", largeDataSet));
			add(new Neo4jSocialNetworkDatabaseExecutor("databases/neo4j/social_network/very_large.db", veryLargeDataSet));
			
			// OrientDB executors
			add(new OrientDbSocialNetworkDatabaseExecutor(System.getProperty("user.dir") + "/databases/orientdb/social_network/small.gdb", smallDataSet));
			add(new OrientDbSocialNetworkDatabaseExecutor(System.getProperty("user.dir") + "/databases/orientdb/social_network/medium.gdb", mediumDataSet));
			add(new OrientDbSocialNetworkDatabaseExecutor(System.getProperty("user.dir") + "/databases/orientdb/social_network/large.gdb", largeDataSet));
			add(new OrientDbSocialNetworkDatabaseExecutor(System.getProperty("user.dir") + "/databases/orientdb/social_network/very_large.gdb", veryLargeDataSet));
		
			// Sparksee executors
//			add(new SparkseeSocialNetworkDatabaseExecutor("Social_network_small", "databases/sparksee/social_network/small.db", smallDataSet));
		}};
	}

}
