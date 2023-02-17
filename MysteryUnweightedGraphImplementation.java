import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
/**
 * An implementation of the Unweighted Graph ADT.  This class can
 * represent both directed and undirected graphs, but the choice must be made
 * at construction time, and is final.
 *   Technically, this implementation supports self-loops; it makes no effort to
 * prevent these.
 *   Any method that takes one or more vertex IDs as arguments may throw an
 * IndexOutOfBoundsException if any input ID is out of bounds.
 * 
 * @author Jadrian Miles
 * @author Anna Rafferty
 */
public class MysteryUnweightedGraphImplementation implements UnweightedGraph {
    private List<List<Integer>> adj;
    private final boolean undirected;
    
    /** Default constructor: an empty directed graph. */
    public MysteryUnweightedGraphImplementation() {
        this(true, 0);
    }
    
    /** Constructs an empty graph with the specified directedness. */
    public MysteryUnweightedGraphImplementation(boolean directed) {
        this(directed, 0);
    }
    
    /** Constructs a graph with N vertices and the specified directedness. */
    public MysteryUnweightedGraphImplementation(boolean directed, int N) {
        adj = new ArrayList<List<Integer>>();
        undirected = !directed;
        for(int i = 0; i < N; i++) {
            adj.add(new ArrayList<Integer>());
        }
    }
    
    /** Adds a new vertex.
     * @return the ID of the added vertex.
     */
    public int addVertex() {
        adj.add(new ArrayList<Integer>());
        return adj.size() - 1;
    }
    
    // The return value, which we'll call i, indicates the presence or absence
    // of an edge in the list "edges" whose endpoint is "goal".
    //   If i >= 0, edges.get(i) is the desired edge.
    //   If i < 0, j = (-i - 1) is the index in edges at which the edge
    //     should be inserted in order to keep the edge list sorted.
    private static int findEdge(List<Integer> edges, int goal) {
        // Search using iterative binary search, essentially copied from the JDK
        // java.util.Arrays (specifically: http://googleresearch.blogspot.com/2006/06/extra-extra-read-all-about-it-nearly.html )
        int l = 0;
        int r = edges.size() - 1;
        while(l <= r) {
            int m = l + (r-l)/2;
            int v = edges.get(m);
            if(v < goal) {
                l = m + 1;
            } else if(v > goal) {
                r = m - 1;
            } else {
                return m;
            }
        }
        return -(l + 1);
    }
    
    /** Adds an edge between two vertices.
     * In an undirected graph, this has the same effect as addEdge(end, begin).
     * @return false if the edge was already in the graph.
     */
    public boolean addEdge(int begin, int end) {
        List<Integer> edges = adj.get(begin);
        if(edges == null || end < 0 || end >= adj.size()) {
            throw new IndexOutOfBoundsException();
        }
        int i = findEdge(edges, end);
        if(i >= 0) {
            // This edge is already in the graph.
            return false;
        }
        edges.add(-i-1, end);
        if(undirected && (begin != end)) {
            // We have to add the edge in the other direction too.
            //   The (begin == end) case is a self-loop; we there's no "other
            // direction in this case.
            //   Since (begin != end) and we assured above that (begin, end) was
            // not already in the graph, we know that (end, begin) is also not
            // already in the graph.  So we know i will be negative; we don't
            // have to check.
            i = findEdge(adj.get(end), begin);
            adj.get(end).add(-i-1, begin);
        }
        return true;
    }
    
    /** Checks whether an edge exists between two vertices.
     * In an undirected graph, this returns the same as hasEdge(end, begin).
     * @return true if there is an edge from begin to end.
     */
    public boolean hasEdge(int begin, int end) {
        List<Integer> edges = adj.get(begin);
        if(edges == null || end < 0 || end >= adj.size()) {
            throw new IndexOutOfBoundsException();
        }
        return (findEdge(edges, end) >= 0);
    }
    
    /** Returns the out-degree of the specified vertex. */
    public int getDegree(int v) {
        List<Integer> edges = adj.get(v);
        if(edges == null) {
            throw new IndexOutOfBoundsException();
        }
        return edges.size();
    }
    
    /** Returns the in-degree of the specified vertex. */
    public int getInDegree(int v) {
        if(v < 0 || v >= adj.size()) {
            throw new IndexOutOfBoundsException();
        }
        // To count the in-degree, we have to look for edges to v from *all*
        // vertices.  (Including v!  Self-loops are allowed.)
        int d = 0;
        for(List<Integer> edges : adj) {
            for(Integer e : edges) {
                if(e.equals(v)) {
                    d++;
                }
            }
        }
        return d;
    }
    
    // Wrapper class around List<Integer>, to provide a read-only iterator.
    private class NeighborCollection implements Iterable<Integer> {
        private List<Integer> neighbors;
        public NeighborCollection(List<Integer> edges) {
            neighbors = edges;
        }
        public Iterator<Integer> iterator() {
            return new NeighborIterator(neighbors);
        }
        
        // Read-only iterator.
        private class NeighborIterator implements Iterator<Integer> {
            private Iterator<Integer> it;
            public NeighborIterator(List<Integer> edges) {
                it = edges.iterator();
            }
            public boolean hasNext() {
                return it.hasNext();
            }
            public Integer next() {
                return it.next();
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
    
    /** Returns an iterator over the neighbors of the specified vertex.
     * In particular, the vertex u is included in the returned iterator's
     * sequence if and only if there is an edge from v to u in the graph.
     */
    public Iterable<Integer> getNeighbors(int v) {
        List<Integer> neighbors = adj.get(v);
        if(neighbors == null) {
            throw new IndexOutOfBoundsException();
        }
        return new NeighborCollection(neighbors);
    }
    
    /** Returns the number of vertices in the graph. */
    public int numVerts() {
        return adj.size();
    }
    
    /** Returns the number of edges in the graph.
     * The result does *not* double-count edges in undirected graphs.
     */
    public int numEdges() {
        int m = 0;
        for(List<Integer> edges : adj) {
            m += edges.size();
        }
        if(undirected) {
            return m/2;
        }
        return m;
    }
    
    /** Returns true if the graph is directed. */
    public boolean isDirected() {
        return !undirected;
    }
    
    /** Returns true if there are no vertices in the graph. */
    public boolean isEmpty() {
        return adj.isEmpty();
    }
    
    /** Removes all vertices and edges from the graph. */
    public void clear() {
        adj.clear();
    }
}
