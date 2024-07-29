package src;

import sol.TravelGraph;

import java.util.List;

/**
 * An interface that describes the commands given to the app.
 *
 * @param <V> the type of the vertices
 * @param <E> the type of edge
 */
public interface ITravelController<V, E> {

    /**
     * Loads CSVs into the app.
     *
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return an informative message to be printed in the REPL
     */
    public String load(String citiesFile, String transportFile);

    /**
     * Finds the fastest route in between two cities
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path starting from the source to the destination,
     * or empty if there is none
     */
    public List<E> fastestRoute(String source, String destination);

    /**
     * Finds the cheapest route in between two cities
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path starting from the source to the destination,
     * or empty if there is none
     */
    public List<E> cheapestRoute(String source, String destination);

    /**
     * Finds the most direct route in between two cities
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path starting from the source to the destination,
     * or empty if there is none
     */
    public List<E> mostDirectRoute(String source, String destination);

    public TravelGraph getGraph();
}
