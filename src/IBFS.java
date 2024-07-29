package src;

import java.util.List;

/**
 * Interface that implements breadth first search.
 *
 * @param <V> the type of the vertices
 * @param <E> the type of the edges
 */
public interface IBFS<V extends IVertex<E>, E extends IEdge<V>> {

    /**
     * Returns the path from start to end.
     *
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return a list of edges starting from the start to the end
     */
    public List<E> getPath(IGraph<V, E> graph, V start, V end);
}
