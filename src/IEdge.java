package src;

/**
 * An interface to represent an edge of a graph.
 *
 * @param <V> the type of the vertex that this edge connects to
 */
public interface IEdge<V> {
    /**
     * Gets the source vertex (the vertex that this edge is coming from)
     *
     * @return the source vertex
     */
    public V getSource();

    /**
     * Gets the target vertex (the vertex that this edge is going to)
     *
     * @return the target vertex
     */
    public V getTarget();

}
