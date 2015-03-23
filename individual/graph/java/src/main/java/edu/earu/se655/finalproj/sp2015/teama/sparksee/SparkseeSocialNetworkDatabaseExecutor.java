package edu.earu.se655.finalproj.sp2015.teama.sparksee;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.sparsity.sparksee.gdb.Database;
import com.sparsity.sparksee.gdb.Graph;
import com.sparsity.sparksee.gdb.Session;
import com.sparsity.sparksee.gdb.Sparksee;
import com.sparsity.sparksee.gdb.SparkseeConfig;

import edu.earu.se655.finalproj.sp2015.teama.DatabaseExecutor;
import edu.earu.se655.finalproj.sp2015.teama.data.DataEntry;
import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

public class SparkseeSocialNetworkDatabaseExecutor extends DatabaseExecutor {

	private Database database;

	public SparkseeSocialNetworkDatabaseExecutor(String databaseName,
			String databasePath, DataSet dataSet) throws FileNotFoundException {
		super(dataSet);

		// Configure the database
		SparkseeConfig cfg = new SparkseeConfig();
		Sparksee sparksee = new Sparksee(cfg);
		this.database = sparksee.create(databasePath, databaseName);

		// Close used resources
		sparksee.close();
	}

	@Override
	public DatabaseExecutor populate() {

		// Create a database session and obtain the graph
		Session session = this.database.newSession();
		Graph graph = session.getGraph();
		
		// Create the prototypes for the graph entities
		int personTypeId = graph.newNodeType("PERSON");
		int knowsTypeId = graph.newEdgeType("KNOWS", true, true);

		// Create a list of the created nodes
		List<Long> nodes = new ArrayList<>();

		this.dataSet.getDataEntries().forEach((entry) -> {
			// Create the friend node
			nodes.add(graph.newNode(personTypeId));
		});

		for (DataEntry entry : this.dataSet.getDataEntries()) {
			// Obtain the node at the current ID
			Long startNode = nodes.get(entry.getId());

			for (Integer relationshipId : entry.getRelationshipIds()) {
				// Extract the node to terminate the relationship at
				Long endNode = nodes.get(relationshipId);

				// Create the relationship
				graph.newEdge(knowsTypeId, startNode, endNode);
			}
		}
		
		// Close open resources
		session.close();

		return this;
	}

	@Override
	public DatabaseExecutor execute() {
		return this;
	}

	@Override
	public DatabaseExecutor shutdown() {
		this.database.close();
		return this;
	}
}
