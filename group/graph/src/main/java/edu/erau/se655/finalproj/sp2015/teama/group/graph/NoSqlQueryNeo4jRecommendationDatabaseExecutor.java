package edu.erau.se655.finalproj.sp2015.teama.group.graph;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class NoSqlQueryNeo4jRecommendationDatabaseExecutor extends Neo4jRecommendationDatabaseExecutor {

	public NoSqlQueryNeo4jRecommendationDatabaseExecutor(String dataSetPath, String databasePath) {
		super(dataSetPath, databasePath);
	}

	@Override
	public String getName() {
		return "Recommendation: NoSQL query";
	}

	@Override
	public DatabaseExecutor execute() {

		try (Transaction tx = this.graphDatabaseService.beginTx()) {
			
			for (Long personNodeId : this.personIdList) {
				// Obtain the starting person for this iteration
				Node startingPerson = this.graphDatabaseService.getNodeById(personNodeId);
				
				// List of interests for the selected person
				List<String> interests = new ArrayList<>();
				
				for (Relationship interestRelationship : startingPerson.getRelationships(Direction.OUTGOING, Relationships.INTERESTED_IN)) {
					// Obtain the end-point (interest node) for each relationship
					Node interest = interestRelationship.getEndNode();
					
					// Add the name of the interest to the list of interests
					interests.add(interest.getProperty(NAME_PROPERTY_KEY).toString());
				}
			}
			
			// Complete transaction
			tx.success();
		}
		
		return this;
	}

}
