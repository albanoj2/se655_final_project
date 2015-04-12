package edu.erau.se655.finalproj.sp2015.teama.group.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
	
	private static final int ITERATION_COUNT = 30;

	public static void main(String[] args) {
		
		// Create a list of database executors to test
		@SuppressWarnings("serial")
		List<DatabaseExecutor> executors = new ArrayList<DatabaseExecutor>() {{
			add(new GraphQueryNeo4jRecommendationDatabaseExecutor("recommendation.json", "tmp/neo4j-" + UUID.randomUUID() + ".db"));
			add(new SqlQueryNeo4jRecommendationDatabaseExecutor("recommendation.json", "tmp/neo4j-" + UUID.randomUUID() + ".db"));
			add(new NoSqlQueryNeo4jRecommendationDatabaseExecutor("recommendation.json", "tmp/neo4j-" + UUID.randomUUID() + ".db"));
		}};

		executors.forEach((executor) -> {
			// Execute each of the executors
			System.out.println("Analyzing <" + executor.getName() + ">...");
			
			// A list of executor times for the executor
			List<Long> executionTimes = new ArrayList<>();
			
			// Populate the database
			System.out.print("Populating...");
			executor.populate();
			System.out.println("done.");
			
			// Print execution header
			System.out.println("Executing...");
			
			for (int i = 0; i < ITERATION_COUNT; i++) {
				// Execute for the configuration number of times
				
				try {
					// Execute the algorithm
					executor.tick().execute().tock();
				}
				catch (Exception e) {
					System.out.println(e);
				}
				
				// Record the execution time
				executionTimes.add(executor.getEllapsedTime());
				
				if (i != 0 && i % 5 == 0) {
					// Report the completion of every 5th iteration 
					System.out.println("\t+ Completed iteration [" + i + "/" + ITERATION_COUNT + "]");
				}
			}
			
			// Display the results for the number of executed iterations
			System.out.println("done.");
			System.out.println("+ Mean: " + executionTimes.stream().mapToLong(Long::longValue).average().getAsDouble() + " ms");
			System.out.println("+ Sample times: " + executionTimes + " in ms");
			System.out.println("");
			
			// Shutdown the executor
			executor.shutdown();
		});
	}


}
