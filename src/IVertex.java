package src;

import java.util.Set;

/**
 * An interface to represent a vertex of a graph.
 *
 * @param <E> the type of the edge that this vertex connects to
 */
public interface IVertex<E> {
    /**
     * Gets outgoing edges from this vertex
     *
     * @return Set of outgoing edges
     */
    public Set<E> getOutgoing();

    /**
     * Adds an outgoing edge to this vertex
     *
     * @param outEdge the outgoing edge
     */
    public void addOut(E outEdge);
}
