package edu.earu.se655.finalproj.sp2015.teama.tinkerpop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.GraphFactory;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataEntry;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

@SuppressWarnings("rawtypes")
public abstract class TinkerpopDatabaseExecutor extends DatabaseExecutor {
	
	protected Map graphConfigurationMap;
	protected Graph graph;

	public TinkerpopDatabaseExecutor (Map graphConfigurationMap, DataSet dataSet) {
		super(dataSet);

		// Store a reference to the graph configuration
		this.graphConfigurationMap = graphConfigurationMap;
	}
	
	@Override
	public DatabaseExecutor open () {
		this.graph = GraphFactory.open(this.graphConfigurationMap);
		return this;
	}

	@Override
	public DatabaseExecutor populate () {
		
		// The list of nodes created
		List<Vertex> vertices = new ArrayList<>();

		for (@SuppressWarnings("unused") DataEntry entry : this.dataSet.getDataEntries()) {
			// Create a node for each of entries
			Vertex vertex = this.graph.addVertex(null);
			
			// Set the name of the vertex to a random UUID
			vertex.setProperty(VertexProperties.NAME, UUID.randomUUID().toString());
			
			// Add the vertex to the list of vertices
			vertices.add(vertex);
		}
		
		for (DataEntry entry : this.dataSet.getDataEntries()) {
			// Obtain the ID of the starting node
			int startNodeId = entry.getId();
			
			for (int endNodeId : entry.getRelationshipIds()) {
				// Create the relationship to each of the 
				this.graph.addEdge(null, vertices.get(startNodeId), vertices.get(endNodeId), Relationships.KNOWS);
			}
		}
		
		return this;
	}

	@Override
	public DatabaseExecutor shutdown() {
		if (this.graph instanceof Neo4jGraph) {
			// This is a temporary fix so that Neo4j databases are closed, but 
			// OrientDB databases are not
			this.graph.shutdown();
		}
		return this;
	}

	@Override
	public String toString() {
		return this.graph.getClass().getSimpleName().toString();
	}
}
