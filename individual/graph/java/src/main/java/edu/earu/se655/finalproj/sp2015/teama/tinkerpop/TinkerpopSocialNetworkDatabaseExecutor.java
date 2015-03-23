package edu.earu.se655.finalproj.sp2015.teama.tinkerpop;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

public class TinkerpopSocialNetworkDatabaseExecutor extends TinkerpopDatabaseExecutor {

	public TinkerpopSocialNetworkDatabaseExecutor (Graph graph, DataSet dataSet) {
		super(graph, dataSet);
	}

	@Override
	public DatabaseExecutor execute () {

		for (Vertex vertex : this.graph.getVertices()) {
		    // Obtain the outgoing edges from this vertex
			Iterable<Edge> outgoingEdges = vertex.getEdges(Direction.OUT, KNOWS_RELATIONSHIP);
			
			for (Edge edge : outgoingEdges) {
				// Iterate through each of the edges (perform some action on the
				// edge to ensure that it is extracted from the database)
				edge.getId();
			}
		}
		
		return this;
	}

	@Override
	public String toString() {
		return "Social network executor (" + this.graph + ")";
	}
}
