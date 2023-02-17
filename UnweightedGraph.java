/**
 * An interface for unweighted graphs.  For more discussion of the structure and
 * use of this interface, see the Graph interface.
 * @author Jadrian Miles
 */
public interface UnweightedGraph extends Graph {
    /** Adds an unweighted edge between two vertices.
     * In an undirected graph, this has the same effect as addEdge(end, begin).
     * @return false if the edge was already in the graph.
     * @throws IndexOutOfBoundsException if either vertex ID is out of bounds.
     */
    public boolean addEdge(int begin, int end);
}
