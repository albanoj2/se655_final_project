package edu.erau.se655.finalproj.sp2015.teama.group.graph;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public abstract class Neo4jRecommendationDatabaseExecutor extends DatabaseExecutor {
	
	private static String CURRENT_DIR = System.getProperty("user.dir");
	protected static String NAME_PROPERTY_KEY = "name";
	protected static String PERSON_TYPE = "Person";
	protected static String INTEREST_TYPE = "Interest";
	protected static String PLACE_OF_WORK_TYPE = "PlaceOfWork";
	protected JSONObject dataSet;
	protected GraphDatabaseService graphDatabaseService;
	protected List<Long> personIdList;
	
	protected static enum Relationships implements RelationshipType {
		WORKED_FOR, INTERESTED_IN
	}

	public Neo4jRecommendationDatabaseExecutor (String dataSetPath, String databasePath) {
		// Create the database service
		this.graphDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(databasePath);
		
		// Create the list of names
		this.personIdList = new ArrayList<>();
		
		// Create the parser to consume the JSON file
		JSONParser parser = new JSONParser();

		try {
			// Parse the JSON file into a JSON object
			this.dataSet = (JSONObject) parser.parse(new FileReader(CURRENT_DIR + "/data_sets/" + dataSetPath));
		} 
		catch (Exception e) {
			// An error occurred while parsing the JSON file
			e.printStackTrace();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public DatabaseExecutor populate () {
		
		// Obtain the list of people
		JSONArray people = (JSONArray) this.dataSet.get("people");

		// Extract the number of interests and places of work
		Long interestsCount = (Long) this.dataSet.get("interests_count");
		Long placesOfWorkCount = (Long) this.dataSet.get("plcs_wrk_count");
		
		// Lists to store the interests and places of work
		List<Node> interests = new ArrayList<>();
		List<Node> placesOfWork = new ArrayList<>();
		
		try (Transaction tx = graphDatabaseService.beginTx()) {
			
			for (int i = 0; i < interestsCount; i++) {
				// Add the 'create interest' to the builder
				interests.add(this.graphDatabaseService.createNode());
				interests.get(i).setProperty(NAME_PROPERTY_KEY, UUID.randomUUID().toString());
			}
			
			for (int i = 0; i < placesOfWorkCount; i++) {
				// Add the 'create place of work' to the builder
				placesOfWork.add(this.graphDatabaseService.createNode());
				placesOfWork.get(i).setProperty(NAME_PROPERTY_KEY, UUID.randomUUID().toString());
			}
			
			people.forEach((person) -> {
				// Obtain the places of work and interest IDs
				JSONArray placesOfWorkIds = (JSONArray) ((JSONObject) person).get("plcs_wrk");
				JSONArray interestsIds = (JSONArray) ((JSONObject) person).get("interests");
				
				// Add 'create person' query to builder
				Node personNode = this.graphDatabaseService.createNode();
				personNode.setProperty(NAME_PROPERTY_KEY, UUID.randomUUID().toString());
				
				// Store the ID of the person just created
				this.personIdList.add(personNode.getId());
				
				placesOfWorkIds.stream().mapToInt((id) -> Integer.parseInt(id.toString())).forEach((id) -> {
					// Create a relationship from the person to the places of work
					personNode.createRelationshipTo(placesOfWork.get(id), Relationships.WORKED_FOR);
				});
				
				interestsIds.stream().mapToInt((id) -> Integer.parseInt(id.toString())).forEach((id) -> {
					// Create a relationship from the person to the interest
					personNode.createRelationshipTo(interests.get(id), Relationships.INTERESTED_IN);
				});
			});
			
			// Complete the transaction
			tx.success();
		}
		
		return this;
	}
	
	@Override
	public DatabaseExecutor shutdown() {
		this.graphDatabaseService.shutdown();
		return this;
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public DatabaseExecutor populate () {
//		
	// Obtain the list of people
//			JSONArray people = (JSONArray) this.dataSet.get("people");
//
//			// Extract the number of interests and places of work
//			Long interestsCount = (Long) this.dataSet.get("interests_count");
//			Long placesOfWorkCount = (Long) this.dataSet.get("plcs_wrk_count");
//
//			// Create lists to contain the nodes for interests and places of work
//			// (this list is used to retrieve the vertices that act as the end
//			// points in the relationships created later)
//			List<Node> placesOfWorkVertices = new ArrayList<>();
//			List<Node> interestsVertices = new ArrayList<>();
//			
//			try (Transaction tx = graphDatabaseService.beginTx()) {
//				
//				for (int i = 0; i < interestsCount; i++) {
//					Node interest = this.graphDatabaseService.createNode(new PersonLabel());
//					interest.setProperty(NAME_PROPERTY_KEY, UUID.randomUUID().toString());
//					
//					// Add the vertex to the list of interests
//					interestsVertices.add(interest);
//					
//					// Add the name to the name list
//					this.idList.add(interest.getId());
//				}
//				
//				for (int i = 0; i < placesOfWorkCount; i++) {
//					// Add a vertex for each place of work
//					placesOfWorkVertices.add(this.graphDatabaseService.createNode());
//				}
//				
//				people.forEach((person) -> {
//					// Obtain the places of work and interest IDs
//					JSONArray placesOfWorkIds = (JSONArray) ((JSONObject) person).get("plcs_wrk");
//					JSONArray interestsIds = (JSONArray) ((JSONObject) person).get("interests");
//					
//					// Create the vertex for each person
//					Node personNode = this.graphDatabaseService.createNode();
//					personNode.setProperty(NAME_PROPERTY_KEY, UUID.randomUUID().toString());
//					
//					placesOfWorkIds.forEach((placeOfWorkId) -> {
//						// Create a relationship from the person to the places of work
//						personNode.createRelationshipTo(placesOfWorkVertices.get(Integer.parseInt(placeOfWorkId.toString())), Relationships.WORKED_FOR);
//					});
//					
//					interestsIds.forEach((interestId) -> {
//						// Create a relationship from the person to the interest
//						personNode.createRelationshipTo(interestsVertices.get(Integer.parseInt(interestId.toString())), Relationships.INTERESTED_IN);
//					});
//				});
//				
//				// Complete the transaction
//				tx.success();
//			}
//			
//			return this;
//	}
	
	public class PersonLabel implements Label {

	@Override
	public String name() {
		return "PERSON";
	}
		
	}

}
