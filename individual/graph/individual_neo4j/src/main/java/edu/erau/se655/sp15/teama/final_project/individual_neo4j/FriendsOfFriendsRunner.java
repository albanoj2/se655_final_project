package edu.erau.se655.sp15.teama.final_project.individual_neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class FriendsOfFriendsRunner {

	private GraphDatabaseService graphDatabaseService;
	private static int FRIENDS_FIRST_DEPTH = 1000;
	private static int FRIENDS_SECOND_DEPTH = 1000;

	private static enum Relationships implements RelationshipType {
		FRIEND
	}

	public FriendsOfFriendsRunner(String path) {
		this.graphDatabaseService = new GraphDatabaseFactory()
				.newEmbeddedDatabase(path);
	}

	public void populate() {

		// Notify user of population
		System.out.print("Populating database...");

		try (Transaction tx = graphDatabaseService.beginTx()) {

			for (int i = 0; i < FRIENDS_FIRST_DEPTH; i++) {
				// Create each friend at the first depth
				Node firstDepthNode = this.graphDatabaseService.createNode();

				// Set some distinct name for the node
				firstDepthNode.setProperty("name", "depth_1::friend_" + i);

				for (int j = 0; j < FRIENDS_SECOND_DEPTH; j++) {
					// Create the friends of this friend at the second depth
					Node secondDepthFriend = this.graphDatabaseService
							.createNode();

					// Set the name for the second depth friend
					secondDepthFriend.setProperty("name", "depth_2::friend_"
							+ j);

					// Add the relationship from the first depth to the second
					// depth
					firstDepthNode.createRelationshipTo(secondDepthFriend,
							Relationships.FRIEND);

					// Notify user of creation
					System.out.println("Created node "
							+ firstDepthNode.getProperty("name") + " --> "
							+ secondDepthFriend.getProperty("name"));
				}
			}
		}

		// Notify population completion
		System.out.println("done.");
	}

	public void shutdown() {
		this.graphDatabaseService.shutdown();
	}
}
