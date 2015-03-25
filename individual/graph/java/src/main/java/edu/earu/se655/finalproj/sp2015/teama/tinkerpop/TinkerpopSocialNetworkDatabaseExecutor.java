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

	/**
	 * Finds the friends of friends for each of the vertices in the graph. 
	 * First, all the friend relationships are found for each of the vertices in
	 * the graph. The terminating vertex of this relationship is found (the 
	 * friend vertex), and then all of the friend relationships are found for 
	 * this friend. The terminating vertex of these relationships are then 
	 * resolved, resulting in the friend of friend vertices from the graph. This
	 * algorithm is graphically depicted below:
	 * 
	 * 																 +--------+
	 * 														+------->| F of F |
	 * 								  +--------+			|		 +--------+
	 * 						  +------>| Friend |----KNOWS---+
	 * 						  |		  +--------+			|		 +--------+
	 * 						  |								+------->| F of F |
	 * 	+--------+			  |										 +--------+
	 * 	| Person |----KNOWS---+										 
	 * 	+--------+			  |										 +--------+
	 * 						  |								+------->| F of F |
	 * 						  |		  +--------+			|	     +--------+
	 * 						  +------>| Friend |----KNOWS---+
	 * 								  +--------+			|		 +--------+
	 * 														+------->| F of F |
	 * 																 +--------+
	 * 
	 */
	@Override
	public DatabaseExecutor execute () {

		for (Vertex startVertex : this.graph.getVertices()) {
		    // Obtain the outgoing edges from this vertex (the "knows" 
			// relationship to obtain the friends)
			
			for (Edge edgeLeadingToFriend : startVertex.getEdges(Direction.OUT, KNOWS_RELATIONSHIP)) {
				// Iterate through each of the "knows" relationship and obtain 
				// the friend vertices (terminating vertex of relationship)
				Vertex friendVertex = edgeLeadingToFriend.getVertex(Direction.OUT);
				
				for (Edge edgeLeadingTofriendOfFriend : friendVertex.getEdges(Direction.OUT, KNOWS_RELATIONSHIP)) {
					// Obtain the edge representing the friend-of-friend 
					// relationship (terminating at the friend-of-friend vertex)
					edgeLeadingTofriendOfFriend.getVertex(Direction.OUT).getId();
				}
			}
		}
		
		return this;
	}

	@Override
	public String toString() {
		return "Social network executor (" + this.graph + ")";
	}
}
