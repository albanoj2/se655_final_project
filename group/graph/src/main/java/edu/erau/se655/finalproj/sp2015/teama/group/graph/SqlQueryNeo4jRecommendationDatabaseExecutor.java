package edu.erau.se655.finalproj.sp2015.teama.group.graph;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class SqlQueryNeo4jRecommendationDatabaseExecutor extends Neo4jRecommendationDatabaseExecutor {

	public SqlQueryNeo4jRecommendationDatabaseExecutor(String dataSetPath, String databasePath) {
		super(dataSetPath, databasePath);
	}

	@Override
	public String getName() {
		return "Recommendation: SQL query";
	}

	@Override
	public DatabaseExecutor execute() {
		
		try (Transaction tx = this.graphDatabaseService.beginTx()) {
			
			for (Long personNodeId : this.personIdList) {
				// Obtain the starting person for this iteration
				Node startingPerson = this.graphDatabaseService.getNodeById(personNodeId);
				
				// Create a map to store the mappings from the place of work to 
				// the coworkers from that work place
				Map<String, String> placeOfWorkToCoworkerMap = new HashMap<>();
				
				for (Relationship workedForRelationship : startingPerson.getRelationships(Direction.OUTGOING, Relationships.WORKED_FOR)) {
					// Obtain the common workplace node
					Node commonWorkplace = workedForRelationship.getEndNode();
					
					for (Relationship coworkerRelationship : commonWorkplace.getRelationships(Direction.INCOMING, Relationships.WORKED_FOR)) {
						
						// Obtain the end-point node (coworker node)
						Node coworkerNode = coworkerRelationship.getStartNode();
						
						if (coworkerNode != startingPerson) {
							// The coworker is not the starting person
							placeOfWorkToCoworkerMap.put(
								commonWorkplace.getProperty(NAME_PROPERTY_KEY).toString(), 
								coworkerNode.getProperty(NAME_PROPERTY_KEY).toString()
							);
						}
					}	
				}
			}
			
			// Complete transaction
			tx.success();
		}
		
		return this;
	}

}
