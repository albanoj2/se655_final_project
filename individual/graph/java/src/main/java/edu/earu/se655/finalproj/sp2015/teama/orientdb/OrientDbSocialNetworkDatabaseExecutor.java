package edu.earu.se655.finalproj.sp2015.teama.orientdb;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;



public class OrientDbSocialNetworkDatabaseExecutor extends DatabaseExecutor {
	
	private static final String KNOWS_RELATIONSHIP = "KNOWS";
	private OrientGraph graph;

	public OrientDbSocialNetworkDatabaseExecutor(String databasePath, DataSet dataSet) {
		super(dataSet);

		// Create the framework for the database
		this.graph = new OrientGraph("plocal:" + databasePath, "admin", "admin");
	}

	@Override
	public DatabaseExecutor populate() {
		
		// The list of nodes created
		List<OrientVertex> vertices = new ArrayList<>();

		try {
			this.dataSet.getDataEntries().forEach((entry) -> {
				// Create a node for each of entries
				vertices.add(this.graph.addVertex(null));
				
				// Commit the transaction
				this.graph.commit();
			});
			
			this.dataSet.getDataEntries().forEach((entry) -> {
				// Obtain the ID of the starting node
				int startNodeId = entry.getId();
				
				entry.getRelationshipIds().forEach((endNodeId) -> {
					// Create the relationship to each of the 
					this.graph.addVertex(null, vertices.get(startNodeId), vertices.get(endNodeId), KNOWS_RELATIONSHIP);
					
					// Commit the transaction
					this.graph.commit();
				});
			});
			
		}
		catch (Exception e) {
			// Roll-back the changes to the graph
			this.graph.rollback();
		}
		
		return this;
	}

	@Override
	public DatabaseExecutor execute () {

		graph.getVertices().forEach((vertex) -> {
		    // Obtain the outgoing edges from this vertex
			Iterable<Edge> outgoingEdges = vertex.getEdges(Direction.OUT, KNOWS_RELATIONSHIP);
			
			outgoingEdges.forEach((edge) -> {
				// Iterate through each of the edges (perform some action on the
				// edge to ensure that it is extracted from the database)
				edge.getId();
			});
		});
		
		return this;
	}

	@Override
	public DatabaseExecutor shutdown() {
		this.graph.shutdown();
		return this;
	}
}
