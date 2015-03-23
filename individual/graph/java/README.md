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

## Tinkerpop
 - &lt;https://github.com/tinkerpop/blueprints/wiki/Code-Examples&gt;<br /><br /><blockquote>Provides basic information for creating different Tinkerpop graphs and accessing the vertices and edges of the graph. This source also shows how to load a graph through a configuration file, rather than directly instantiating each of the graph databases. This approach is taken, since the configuration of each database can be changed through a configuration text file, rather than changing the configuration in the source code.</blockquote>
 - &lt;https://github.com/tinkerpop/blueprints/wiki/Graph-Transactions&gt;<br /><br /><blockquote>Provides information on transactions and Tinkerpop graphs. While this source is useful, `TransactionalGraph`s are not used directly in the source code for this project (only the `Graph` interface is used) and therefore, there are no transaction-specific methods that must be called.</blockquote>
  - "Blueprints: A Property Graph Model Interface 2.6.0 API." *Blueprints: A Property Graph Model Interface 2.6.0 API.* Tinkerpop, 2014. Web. 22 Mar. 2015. &lt;http://www.tinkerpop.com/docs/javadocs/blueprints/2.6.0/&gt;.<br /><br /><blockquote>Used for the API reference for Tinkerpop, as well as to find the supported Tinkerpop graph types.</blockquote>