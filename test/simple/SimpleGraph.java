package test.simple;

import src.IGraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that models a simple graph with simple edges and simple vertices.
 * You can use these Simple classes to test your BFS and Dijkstra's, but you will not be graded on
 * any tests you write using these classes.
 */
public class SimpleGraph implements IGraph<SimpleVertex, SimpleEdge> {
    public Set<SimpleVertex> vertices = new HashSet<>();

    @Override
    public void addVertex(SimpleVertex vertex) {
        this.vertices.add(vertex);
    }

    @Override
    public void addEdge(SimpleVertex origin, SimpleEdge edge) {
        origin.addOut(edge);
    }

    @Override
    public Set<SimpleVertex> getVertices() {
        return this.vertices;
    }

    @Override
    public SimpleVertex getEdgeSource(SimpleEdge edge) {
        return edge.source;
    }

    @Override
    public SimpleVertex getEdgeTarget(SimpleEdge edge) {
        return edge.target;
    }

    @Override
    public Set<SimpleEdge> getOutgoingEdges(SimpleVertex fromVertex) {
        return fromVertex.outgoingEdges;
    }

    /**
     * Returns the total edge weight of all edges in a path.
     * @param path the path
     * @return the total edge weight of all edges in that path
     */
    public static double getTotalEdgeWeight(List<SimpleEdge> path) {
        double total = 0;
        for (SimpleEdge segment : path) {
            total += segment.getWeight();
        }
        return total;
    }
}
