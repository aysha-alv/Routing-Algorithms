package sol;

import src.IDijkstra;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation for Dijkstra's algorithm
 *
 * @param <V> the type of the vertices
 * @param <E> the type of the edges
 */
public class Dijkstra<V extends IVertex<E>, E extends IEdge<V>> implements IDijkstra<V, E> {

    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination, Function<E, Double> edgeWeight) {
        //1. go through the variables
        //2.make some necesssary name changes

        /**
         * initiations:
         * costs-- a hashmap of vertice and its weight
         * visitedVertices -- a hashmap of (neighbor, current). This is for later backtracking
         * toCheckPQ-- a Priority Queue of unvisited cities/vertices
         *
         */
        Map<V, Double> costs = new HashMap<>();
        Map<V, V> visitedVertices = new HashMap<>();

        Comparator<V> lowerCost = (v1, v2) -> {
            double d1 = costs.getOrDefault(v1, Double.POSITIVE_INFINITY);
            double d2 = costs.getOrDefault(v2, Double.POSITIVE_INFINITY);
            return Double.compare(d1, d2);
        };
        PriorityQueue<V> toCheckPQ = new PriorityQueue<V>(lowerCost);

        //add origin city in
        costs.put(source, 0.0);
        //insert the origin in according to the comparator
        toCheckPQ.add(source);

        while (!toCheckPQ.isEmpty()) {
            //pull out the first element of the PQ
            V current = toCheckPQ.poll();
            if (current == destination) {
                break;//skip to the block below
            }
            //for every edge in the current node's outgoing edges

            for (E edge : current.getOutgoing()) {
                V neighbor = edge.getTarget();
                //new Cost = distance from the current node to the origin + whatever weight is passed in of the edge
                double newCost = costs.get(current) + edgeWeight.apply(edge);
                if (neighbor != null && newCost < costs.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    costs.put(neighbor, newCost); //update the current cost
                    visitedVertices.put(neighbor, current); //mark the neighbor as visited
                    //first remove the neighbor from the toCheckPQ,
                    //then insert it in again with the updated cost, so its
                    //position in the toCheckPQ can be updated
                    toCheckPQ.remove(neighbor);
                    toCheckPQ.offer(neighbor);
                }
            }
        }


        LinkedList<E> path = new LinkedList<>();
        V current = destination;
        while (visitedVertices.get(current) != null) {
            //e.g., DC,                          Seattle
            V previousCity = visitedVertices.get(current);
            V nextCity = current;

            //make sure if we have multiple transports on the same path, we go with the one having lowest cost
            Comparator<E> lowerCostTransport = (e1, e2) -> {
                return Double.compare(edgeWeight.apply(e1), edgeWeight.apply(e2));
            };
            PriorityQueue<E> edgePQ= new PriorityQueue<E>(lowerCostTransport);

            Set<E> edgesGoingtoNext =
                    previousCity.getOutgoing().stream().filter(e -> e.getTarget() == nextCity).collect(Collectors.toSet());
            for(E edge : edgesGoingtoNext){
                if (edge != null) {
                    edgePQ.add(edge);
                }
                else {
                    return new LinkedList<>();
                }

            }


            //looking for the edge going from DC to seattle. It's arbitrarily choosing one of the edges
            //path.addFirst(previousCity.getOutgoing().stream().filter(e -> e.getTarget() == nextCity).findFirst().get());
            path.addFirst(edgePQ.poll());
            current = previousCity;
        }




        return path;
    }



    // TODO: feel free to add your own methods here!
}
