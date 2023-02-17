import java.util.Iterator;
/**
 * A common interface for the Graph ADT, encompassing graphs both unweighted and
 * weighted, undirected and directed.  Note that an object of Graph type can't
 * add edges to itself; the sub-interfaces UnweightedGraph and WeightedGraph
 * contain the necessary methods for adding edges.
 *   Note that this particular interface explicitly refers to vertices by a
 * consecutive set of integral vertex IDs, from 0 to N-1 (where N is the number
 * of vertices), and conceives of edges only in terms of their endpoints.
 * This is the canonical conception of a graph for standard graph algorithms,
 * but lacks common features for modeling, e.g., a road network.
 * The user must represent metadata like vertex labels in their own data structures,
 * outside of the graph class itself.
 * 
 * Any method that takes one or more vertex IDs as arguments may throw an
 * IndexOutOfBoundsException if any input ID is out of bounds.
 * 
 * @author Jadrian Miles
 * @author Anna Rafferty
 */
public interface Graph {
    /** Adds a new vertex.
     * @return the ID of the added vertex.
     */
    public int addVertex();
    
    /** Checks whether an edge exists between two vertices.
     * In an undirected graph, this returns the same as hasEdge(end, begin).
     * @return true if there is an edge from begin to end.
     */
    public boolean hasEdge(int begin, int end);
    
    /** Returns the out-degree of the specified vertex. */
    public int getDegree(int v);
    
    /** Returns the in-degree of the specified vertex. */
    public int getInDegree(int v);
    
    /** Returns an iterable object that allows iteration over the neighbors of
     * the specified vertex.  In particular, the vertex u is included in the
     * sequence if and only if there is an edge from v to u in the graph.
     */
    public Iterable<Integer> getNeighbors(int v);
    
    /** Returns the number of vertices in the graph. */
    public int numVerts();
    
    /** Returns the number of edges in the graph.
     * The result does *not* double-count edges in undirected graphs.
     */
    public int numEdges();
    
    /** Returns true if the graph is directed. */
    public boolean isDirected();
    
    /** Returns true if there are no vertices in the graph. */
    public boolean isEmpty();
    
    /** Removes all vertices and edges from the graph. */
    public void clear();
}
