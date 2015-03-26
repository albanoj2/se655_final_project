package edu.earu.se655.finalproj.sp2015.teama.tinkerpop;

import java.util.Map;

import com.tinkerpop.blueprints.Vertex;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

public class TinkerpopGetPropertyDatabaseExecutor extends TinkerpopDatabaseExecutor {

	@SuppressWarnings("rawtypes")
	public TinkerpopGetPropertyDatabaseExecutor (Map graphConfigurationMap, DataSet dataSet) {
		super(graphConfigurationMap, dataSet);
	}

	/**
	 * Simply obtain a single property from each of the vertices in the graph.
	 */
	@Override
	public DatabaseExecutor execute () {
		
		for (Vertex vertex : this.graph.getVertices()) {
			// Iterate through each of the vertices in the graph and obtain a 
			// single property from each vertex
			vertex.getProperty(VertexProperties.NAME);
		}
		
		return this;
	}

}
