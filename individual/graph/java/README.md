# Selected Graph Databases
 - [Neo4j](http://neo4j.com/)
 - [OrientDB](http://www.orientechnologies.com/orientdb/)
 - [Sparksee (DEX)](http://www.sparsity-technologies.com/)
 - [Apache Giraph](http://giraph.apache.org/)

# Use Cases
The following use cases will executed on each of the selected graph databases:

 1. **Social network**: friends-of-friends traversal algorithm that focuses on exeuting speed of traversals in an exponentially growing data set (friends-of-friends is exponently largers than simply friends)
 2. **Get property**: read a single property from every node in the graph; this algorithm focuses on the ability for the graph database to quickly retrieve data stored at each nodes, as well as the ability of the graph database engine to iterate through each node in the graph
 3. **Shift relationships**: remove the relationships between the nodes in the social network graph and update them with a new relationship, terminating at node i + 1 % count(nodes) (shift the relationship to terminate at the next consective node)

# Workloads

| Category | Nodes  | Maximum Connections |
|----------|--------|---------------------|
| small    | 1000   | 50                  |
| medium   | 10000  | 50                  |
| large    | 100000 | 50                  |
| very large    | 1000000 | 50                  |

# References
The following are the references used to create the implementations for each database:

## Neo4j
 - "How to Create Unique Nodes." *The Neo4j Manual V2.1.7.* Neo4j, n.d. Web. 21 Mar. 2015. &lt;http://neo4j.com/docs/stable/tutorials-java-embedded-unique-nodes.html&gt;. <blockquote>Used to create new nodes and create the graph used in the populate method for the Neo4j social network executor.</blockquote>
 - "Basic friend finding based on social neighborhood." *The Neo4j Manual V2.1.7.* Neo4j, n.d. Web. 21 Mar. 2015. &lt;http://neo4j.com/docs/stable/cypher-cookbook-friend-finding.html&gt;. <blockquote>Used an official basis on the algorithms and the process used to create and query a graph based on an example social network. This page was used as a basis for implementing the "suggested" method for implementing a friends-of-friends algorithm.</blockquote>
 - "Find people based on mutual friends and groups." *The Neo4j Manual V2.1.7.* Neo4j, n.d. Web. 21 Mar. 2015. &lt;http://neo4j.com/docs/stable/cypher-cookbook-mutual-friends-and-groups.html&gt;. <blockquote>Used as supplement to the above reference to create an officially suggested implementation of the friends-of-friends algorithm</blockquote>