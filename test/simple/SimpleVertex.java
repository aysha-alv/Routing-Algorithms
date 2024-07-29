package test.simple;

import src.IVertex;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that models a simple vertex.
 */
public class SimpleVertex implements IVertex<SimpleEdge> {
    public String id;
    public Set<SimpleEdge> outgoingEdges = new HashSet<>();

    /**
     * Constructor for the SimpleVertex class.
     * @param id id of the vertex
     */
    public SimpleVertex(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public Set<SimpleEdge> getOutgoing() {
        return this.outgoingEdges;
    }

    @Override
    public void addOut(SimpleEdge outEdge) {
        this.outgoingEdges.add(outEdge);
    }
}
