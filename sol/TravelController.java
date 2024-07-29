package sol;

import src.ITravelController;
import src.TravelCSVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import src.TransportType;

/**
 * Implementation for TravelController
 */
public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;

    /**
     * Constructor for TravelController
     */
    public TravelController() {
    }

    @Override
    public String load(String citiesFile, String transportFile) {
        //initiate graph
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();
        /**
         *   a method in this class that takes the information from the cities csv
         *   to create a vertex/city and add that into a graph
         */
        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));//get city "names" column from the csv
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing the file: " + citiesFile;
        }

        /**
         *   a function that takes the information from the transportation csv
         *   to create an edge and uses that to build the edge on the graph.
         */
        //Map<String, String>  is a hashmap that relates the column name of the csv to the value in the column .
        Function<Map<String, String>, Void> addEdge = map -> {
            City originCity = this.graph.getCity(map.get("origin"));
            City destCity = this.graph.getCity(map.get("destination"));
            Transport edge = new Transport(originCity, destCity,
                    TransportType.fromString(map.get("type")), Double.valueOf((map.get("price"))),
                    Double.valueOf((map.get("duration"))));
            this.graph.addEdge(originCity, edge);
            return null; // need explicit return null to account for Void type
        };

        // call parseTransportation with the second Function variable as an argument and the right file
        try {
            // pass in string for CSV and function to create edge
            parser.parseTransportation(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing the file: " + transportFile;
        }

        // hint: note that parseLocations and parseTransportation can
        //       throw IOExceptions. How can you handle these calls cleanly?
        return "Successfully loaded cities and transportation files.";
    }

    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        //create set of edges using minutes
        Function<Transport, Double> edgeWeight = Transport::getMinutes;
        Dijkstra d = new Dijkstra();
        return d.getShortestPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination), edgeWeight);
    }

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Function<Transport, Double> edgeWeight = Transport::getPrice;
        Dijkstra d = new Dijkstra();
        return d.getShortestPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination), edgeWeight);
    }

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS b = new BFS();
        return b.getPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination));
    }

    /**
     * Returns the graph stored by the controller
     *
     * NOTE: You __should not__ be using this in your implementation, this is purely meant to be used for the visualizer
     *
     * @return The TravelGraph currently stored in the TravelController
     */
    public TravelGraph getGraph() {
        return this.graph;
    }
}
