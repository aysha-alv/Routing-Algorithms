package sol;

import src.IEdge;
import src.IVertex;

import java.util.HashSet;
import java.util.Set;

/**
 * A City class representing the vertex of a travel graph
 */
public class City implements IVertex<Transport> {
    private String name;
    private Set<Transport> outgoingEdges;

    /**
     * Constructor for a City
     * @param name The name of the city
     */
    public City(String name) {
        //e.g., thus we know it's "Boston"
        this.name = name;
        //thus we know the methods of transportation out of this city
        this.outgoingEdges = new HashSet<Transport>();
    }

    /**
     * Retrieve the set of transports this vertex has
     */
    @Override
    public Set<Transport> getOutgoing() {
        return this.outgoingEdges;
    }

    /**
     * Add a new edge/transport into the set
     */
    @Override
    public void addOut(Transport outEdge) {
        this.outgoingEdges.add(outEdge);
    }


    /**
     * Return the name info of the City
     */
    @Override
    public String toString() {
        return this.name;
    }
}
