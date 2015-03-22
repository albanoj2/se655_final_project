package edu.earu.se655.finalproj.sp2015.teama.neo4j;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataEntry;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

public class Neo4jSocialNetworkDatabaseExecutor extends DatabaseExecutor {

	private GraphDatabaseService graphDatabaseService;

	private static enum Relationships implements RelationshipType { KNOWS }

	public Neo4jSocialNetworkDatabaseExecutor(String databasePath, DataSet dataSet) {
		super(dataSet);
		this.graphDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(databasePath);
	}

	@Override
	public DatabaseExecutor populate() {

		try (Transaction tx = graphDatabaseService.beginTx()) {
			
			// Create a list of the created nodes
			List<Node> nodes = new ArrayList<>();

			for (@SuppressWarnings("unused") DataEntry entry : this.dataSet.getDataEntries()) {
				// Create the friend node
				nodes.add(this.graphDatabaseService.createNode());
			}

			for (DataEntry entry : this.dataSet.getDataEntries()) {
				// Obtain the node at the current ID
				Node startNode = nodes.get(entry.getId());

				for (Integer relationshipId : entry.getRelationshipIds()) {
					// Extract the node to terminate the relationship at
					Node endNode = nodes.get(relationshipId);
					
					// Create the relationship
					startNode.createRelationshipTo(endNode, Relationships.KNOWS);
				}
			}
			
			// Complete the transaction
			tx.success();
		}

		return this;
	}

	@Override
	public DatabaseExecutor execute() {

		// Create the execution engine for the graph database
		ExecutionEngine engine = new ExecutionEngine(this.graphDatabaseService);

		try (Transaction tx = this.graphDatabaseService.beginTx()) {
			
			for (int i = 0; i < this.dataSet.getDataEntries().size(); i += 100) {
				// Repeat for every 100th entry
				DataEntry entry = this.dataSet.getDataEntries().get(i);
				
				// Execute the search query for each of the nodes in the graph
				engine.execute("MATCH (user)-[:" + Relationships.KNOWS + "*2]->(fof) "
						+ "WHERE id(user) = " + entry.getId() + " AND NOT (user)-[:" + Relationships.KNOWS + "]->(fof) "
						+ "RETURN COUNT(fof)");
			}
		
			// Complete the transaction
			tx.success();
		}
		
		return this;
	}

}
