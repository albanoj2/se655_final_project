package edu.earu.se655.finalproj.sp2015.teama.tinkerpop;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataEntry;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

public abstract class TinkerpopDatabaseExecutor extends DatabaseExecutor {
	
	protected static final String KNOWS_RELATIONSHIP = "KNOWS";
	protected Graph graph;

	public TinkerpopDatabaseExecutor (Graph graph, DataSet dataSet) {
		super(dataSet);

		// Store a reference to the graph
		this.graph = graph;
	}


	@Override
	public DatabaseExecutor populate () {
		
		// The list of nodes created
		List<Vertex> vertices = new ArrayList<>();

		for (@SuppressWarnings("unused") DataEntry entry : this.dataSet.getDataEntries()) {
			// Create a node for each of entries
			vertices.add(this.graph.addVertex(null));
		}
		
		for (DataEntry entry : this.dataSet.getDataEntries()) {
			// Obtain the ID of the starting node
			int startNodeId = entry.getId();
			
			for (int endNodeId : entry.getRelationshipIds()) {
				// Create the relationship to each of the 
				this.graph.addEdge(null, vertices.get(startNodeId), vertices.get(endNodeId), KNOWS_RELATIONSHIP);
			}
		}
		
		return this;
	}

	@Override
	public DatabaseExecutor shutdown() {
//		this.graph.shutdown();
		return this;
	}

}
