package edu.earu.se655.finalproj.sp2015.teama.tinkerpop;

import com.tinkerpop.blueprints.Graph;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

public class TinkerpopShiftRelationshipDatabaseExecutor extends	TinkerpopDatabaseExecutor {

	public TinkerpopShiftRelationshipDatabaseExecutor (Graph graph, DataSet dataSet) {
		super(graph, dataSet);
	}

	@Override
	public DatabaseExecutor execute () {
		return this;
	}

}
