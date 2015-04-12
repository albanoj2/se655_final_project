package edu.erau.se655.finalproj.sp2015.teama.group.graph;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;


public class GraphQueryNeo4jRecommendationDatabaseExecutor extends Neo4jRecommendationDatabaseExecutor {

	public GraphQueryNeo4jRecommendationDatabaseExecutor(String dataSetPath, String databasePath) {
		super(dataSetPath, databasePath);
	}

	@Override
	public String getName() {
		return "Recommendation: Graph query";
	}

	@Override
	public DatabaseExecutor execute() {
		
		try (Transaction tx = this.graphDatabaseService.beginTx()) {
			
			for (Long personNodeId : this.personIdList) {
				// Obtain the starting person for this iteration
				Node startingPerson = this.graphDatabaseService.getNodeById(personNodeId);
				
				// A list to store all of the found interests
				Set<String> interests = new HashSet<>();
				
				for (Relationship workedForRelationship : startingPerson.getRelationships(Direction.OUTGOING, Relationships.WORKED_FOR)) {
					// Obtain the common workplace node
					
					for (Relationship coworkerRelationship : workedForRelationship.getEndNode().getRelationships(Direction.INCOMING, Relationships.WORKED_FOR)) {
						
						// Obtain the end-point node (coworker node)
						Node coworkerNode = coworkerRelationship.getStartNode();
						
						if (coworkerNode != startingPerson) {
							// The coworker is not the starting person
						
							for (Relationship interestRelationship : coworkerNode.getRelationships(Relationships.INTERESTED_IN)) {
								// Store the property of each of the interests
								interests.add(interestRelationship.getEndNode().getProperty(NAME_PROPERTY_KEY).toString());
							}
						}
					}	
				}
			}
			
			tx.success();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
}
