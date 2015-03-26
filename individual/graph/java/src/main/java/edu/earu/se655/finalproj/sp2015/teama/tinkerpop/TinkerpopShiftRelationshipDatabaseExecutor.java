package edu.earu.se655.finalproj.sp2015.teama.tinkerpop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;


public class TinkerpopShiftRelationshipDatabaseExecutor extends	TinkerpopDatabaseExecutor {
	
	private List<Vertex> vertices;

	@SuppressWarnings("rawtypes")
	public TinkerpopShiftRelationshipDatabaseExecutor (Map graphConfigurationMap, DataSet dataSet) {
		super(graphConfigurationMap, dataSet);
	}
	
	@Override
	public DatabaseExecutor open () {
		super.open();
		
		// Create a list of the nodes in the graph
		this.vertices = new ArrayList<>();
		
		for (Vertex vertex : this.graph.getVertices()) {
			// Add each of the vertices to the list of vertices
			this.vertices.add(vertex);
		}
		
		return this;
	}

	@Override
	public DatabaseExecutor execute () {
		
		this.vertices.forEach((vertex) -> {
			// Iterate through each of the relationships and then iterate 
			// through each of the edges of each vertex
			
			vertex.getEdges(Direction.OUT, Relationships.KNOWS).forEach((edge) -> {
				// Obtain the terminating vertex
				Vertex terminatingVertex = edge.getVertex(Direction.OUT);
				
				// Calculate the index of the new terminating vertex (the index
				// of the next vertex, modulo the number of vertices, ensuring 
				// that edges to the last vertex wraps to the first vertex)
				int newTerminatingVertexIndex = (this.vertices.indexOf(terminatingVertex) + 1) % this.vertices.size();
				
				// Remove the edge from the vertex
				this.graph.removeEdge(edge);
				
				// Add a new edge from the vertex to the new terminating vertex
				vertex.addEdge(Relationships.KNOWS, this.vertices.get(newTerminatingVertexIndex));
			});
		});
		
		return this;
	}

}
