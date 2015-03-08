package edu.erau.se655.sp15.teama.final_project.individual_neo4j;


/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		// Create the friends database runner
		FriendsOfFriendsRunner friendsRunner = new FriendsOfFriendsRunner(
				"friends_of_friends.db");
		friendsRunner.populate();
	}
}
