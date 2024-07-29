package src;

import java.util.Set;

/**
 * An interface to represent a graph.
 *
 * @param <V> the type of the vertex
 * @param <E> the type of the edge
 */
public interface IGraph<V extends IVertex<E>, E extends IEdge<V>> {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex
     */
    public void addVertex(V vertex);

    /**
     * Adds an edge to the graph.
     *
     * @param origin the origin of the edge.
     * @param edge
     */
    public void addEdge(V origin, E edge);

    /**
     * Gets a set of vertices in the graph.
     *
     * @return the set of vertices
     */
    public Set<V> getVertices();

    /**
     * Gets the source of an edge.
     *
     * @param edge the edge
     * @return the source of the edge
     */
    public V getEdgeSource(E edge);

    /**
     * Gets the target of an edge.
     *
     * @param edge the edge
     * @return the target of the edge
     */
    public V getEdgeTarget(E edge);

    /**
     * Gets the outgoing edges of a vertex.
     *
     * @param fromVertex the vertex
     * @return the outgoing edges from that vertex
     */
    public Set<E> getOutgoingEdges(V fromVertex);
}
