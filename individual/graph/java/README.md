# Selected Graph Databases
 - [Neo4j](http://neo4j.com/)
 - [OrientDB](http://www.orientechnologies.com/orientdb/)
 - [Sparksee (DEX)](http://www.sparsity-technologies.com/)

# Use Cases
The following use cases will executed on each of the selected graph databases:

 1. **Social network**: friends-of-friends traversal algorithm that focuses on executing speed of traversals in an exponentially growing data set (friends-of-friends is exponentially larger than simply friends); note that when each friend-of-friend is pulled from the database, a simple operation is performed on it, such as retrieving the ID of the friend, to ensure that the node is actually pulled from the database (in case the database implements lazy instantiation of the node)
 2. **Get property**: read a single property from every node in the graph; this algorithm focuses on the ability for the graph database to quickly retrieve data stored at each nodes, as well as the ability of the graph database engine to iterate through each node in the graph
 3. **Shift relationships**: remove the relationships between the nodes in the social network graph and update them with a new relationship, terminating at node i + 1 % count(nodes) (shift the relationship to terminate at the next consecutive node)

# Workloads
Note that in all workloads executed, all outliers are included in the samples (are not discarded) as the outliers are usually the first execution of the algorithm, where the cache is not warmed up (and therefore, the subsequent execution times will be much lower once the cache has warmed up).

| Category | Nodes  | Maximum Connections |
|----------|--------|---------------------|
| small    | 1000   | 50                  |
| medium   | 10000  | 50                  |
| large    | 100000 | 50                  |
| very large    | 1000000 | 50                  |

# References
The following are the references used to create the implementations for each database:

## Neo4j
 - "How to Create Unique Nodes." *The Neo4j Manual V2.1.7.* Neo4j, n.d. Web. 21 Mar. 2015. &lt;http://neo4j.com/docs/stable/tutorials-java-embedded-unique-nodes.html&gt;.<br /><br /><blockquote>Used to create new nodes and create the graph used in the populate method for the Neo4j social network executor.</blockquote>
 - "Basic friend finding based on social neighborhood." *The Neo4j Manual V2.1.7.* Neo4j, n.d. Web. 21 Mar. 2015. &lt;http://neo4j.com/docs/stable/cypher-cookbook-friend-finding.html&gt;..<br /><br /><blockquote>Used an official basis on the algorithms and the process used to create and query a graph based on an example social network. This page was used as a basis for implementing the "suggested" method for implementing a friends-of-friends algorithm.</blockquote>
 - "Find people based on mutual friends and groups." *The Neo4j Manual V2.1.7.* Neo4j, n.d. Web. 21 Mar. 2015. &lt;http://neo4j.com/docs/stable/cypher-cookbook-mutual-friends-and-groups.html&gt;..<br /><br /><blockquote>Used as supplement to the above reference to create an officially suggested implementation of the friends-of-friends algorithm</blockquote>
 
## OrientDB
  - "Graph API." *OrientDB Manual - Version 2.0.* Orient Technologies, n.d. Web. 22 Mar. 2015. &lt;http://www.orientechnologies.com/docs/last/Graph-Database-Tinkerpop.html&gt;. <br /><br /><blockquote>This website was used to create the graph, as well as retrieve the nodes and edges from the same graph. There are three types of API supplied by OrientDB: (1) graph, (2) document, and (3) object. The graph API was selected because it relates directly to the task of creating and altering the graph. The graph API is essentially the Tinkerpop API for OrientDB. While there are Tinkerpop APIs for Neo4j and Sparksee, these are not used in order to provide native Neo4j and Sparksee implementations, using the techniques suggested by the documentation supplied for each database (the database was used in the manner suggested). Since OrientDB suggests that the Tinkerpop API be used for the graph API, Tinkerpop is used.</blockquote>
  - "Blueprints: A Property Graph Model Interface 2.6.0 API." *Blueprints: A Property Graph Model Interface 2.6.0 API.* Tinkerpop, 2014. Web. 22 Mar. 2015. &lt;http://www.tinkerpop.com/docs/javadocs/blueprints/2.6.0/&gt;.<br /><br /><blockquote>Used as the API reference for Tinkerpop Blueprint. Note that although the Tinkerpop API documentation is correct for the version used in this project, I cannot find the OrientDB Blueprint implementation documentation within the Tinkerpop 2.6 JavaDocs.
