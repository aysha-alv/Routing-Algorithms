package src;

import sol.City;
import sol.Transport;
import sol.TravelController;
import sol.TravelGraph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

/**
 * Travel graph visualizer
 * @param <V> The type of the vertices
 * @param <E> The type of the edges
 */
public class Visualizer<V extends IVertex<E>, E extends ITransport & IEdge<V>> {

    /**
     * Generate a string to represent this graph in DOT
     * @param graph The graph
     * @param pathName Output file path
     */
    public void generateDOTString(IGraph<V, E> graph, String pathName) {
        try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(pathName))) {
            outputFile.write("digraph TravelPlanner {\n");
            outputFile.write("\tnode [shape=\"plaintext\" style=\"filled, rounded\" fontname=\"Lato\" margin=0.2]\n");
            outputFile.write("\tedge [fontname=\"Lato\" labeldistance=5]\n");

            Set<V> unvisited = graph.getVertices();

            for (V vertex : unvisited) {
                outputFile.write("\t\"" + vertex + "\"\n");
            }

            Stack<V> toExplore = new Stack<V>();

            while (!unvisited.isEmpty()) { // the graph can have multiple connected components
                toExplore.add(unvisited.stream().toList().get(0)); // pick a random vertex to start exploring from

                while (!toExplore.isEmpty()) { // explore this connected component
                    V currentVertex = toExplore.pop();
                    unvisited.remove(currentVertex);

                    for (E outgoingEdge : currentVertex.getOutgoing()) {
                        V neighbor = outgoingEdge.getTarget();

                        String edgeLabel = String.format("Type: %s\\nPrice: $%.2f\\nTime: %.2f minutes\n", outgoingEdge.getType(), outgoingEdge.getPrice(), outgoingEdge.getMinutes());

                        outputFile.write("\t\"" + currentVertex + "\" -> \"" + neighbor + "\" [label=\"" + edgeLabel + "\"]\n");

                        if (unvisited.contains(neighbor)) {
                            toExplore.push(neighbor);
                            unvisited.remove(neighbor);
                        }
                    }
                }
            }

            outputFile.write("}");
            outputFile.flush();
        } catch (IOException e) {
            System.out.println("Oh no! Got an error while trying to generate your dot string.\n" + e.getMessage());
        }
    }

    /**
     * Main method for using the visualizer
     * @param args Unused
     */
    public static void main(String[] args) {
        String citiesFilepath = "data/cities2.csv";
        String transportFilepath = "data/transport2.csv";
        String outputFilepath = "data/graphs/graph2.dot";

        TravelController controller = new TravelController();
        Visualizer<City, Transport> viz = new Visualizer<>();

        controller.load(citiesFilepath, transportFilepath);

        TravelGraph graph = controller.getGraph();
        viz.generateDOTString(graph, outputFilepath);
    }
}
