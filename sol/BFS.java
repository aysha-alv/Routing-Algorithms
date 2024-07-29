package sol;

import src.IBFS;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;

/**
 * Implementation for BFS, implements IBFS interface
 * @param <V> The type of the vertices
 * @param <E> The type of the edges
 */
public class BFS<V extends IVertex<E>, E extends IEdge<V>> implements IBFS<V, E> {

    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        //create a que
        Queue<IVertex> q = new LinkedList<>();
        q.add(start); //add in the origin city from the end of the queue
        //initiate visited LinkedList
        LinkedList<IVertex> visited = new LinkedList<IVertex>();
        //because we are already at the origin, at origin into visited
        visited.add(start);
        //**initiate a hashmap with a city and the edge it comes from
        Map<IVertex, IEdge> traversed = new HashMap<IVertex, IEdge>();
        //as long as the queue is not empty
        while (!q.isEmpty()) {//so continue doing it
            //pop out the first thing in the queue
            IVertex current = q.poll();
            //if the first thing in the queue is already the destination we look for
            if (current == end) {
                //backtracking to find the path in helper function
                return this.findPath(start, end, traversed);
            }
            //Otherwise, get all the edges of the current node
            Set<E> edges = current.getOutgoing();
            //for each edge in the set
            for (IEdge edge : edges) {
                //if we haven't added the next city at the end of the edge
                if (!visited.contains(edge.getTarget())) {
                    traversed.put((IVertex) edge.getTarget(), edge);
                    visited.add((IVertex) edge.getTarget());
                    q.add((IVertex) edge.getTarget());
                    //where did the recursion happen?
                }
            }
        }
        return new LinkedList<>();
    }

    /**
     * This is a helper function
     * @param start -- the origin node
     * @param end -- the destination
     * @param traversed -- a hashmap of vertex and its paths to its neighbors
     * @return the list of paths
     */
    private List<E> findPath(V start, V end, Map<IVertex, IEdge> traversed) {
        //return a list of edges
        //if the current node is the destination already
        IVertex current = (IVertex) end;
        //initiate the path we are going to return
        List<E> path = new LinkedList<>();
        //as long as the current node is not the origin
        while (current != null && !current.equals(start)) {
            //with the traversed map, get the Edge of the current node
            IEdge currentEdge = traversed.get(current); //e.g., if the current is Prov, it gets Prov-> Bos
            path.add((E) currentEdge);
            //get current to the source node of the edge, then continue back trackinng
            current = (IVertex) currentEdge.getSource();
        }
        return path;
    }


}
    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name

