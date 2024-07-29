package test;

import org.junit.Test;
import sol.*;
import src.IDijkstra;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;
import sol.City;
import sol.Transport;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private City seattle;
    private City dc;
    private City nyc;
    private TravelGraph travelGraph;


    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    /**
     * A sample test that tests Dijkstra's on a simple graph. Checks that the shortest path using Dijkstra's is what we
     * expect.
     */
    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    /**
     * for testing bidirectional, two transports on the same edge, and the case where
     * a node cannot go to another
     */
    public void makeSolGraph() {
        this.travelGraph = new TravelGraph();
        this.seattle = new City("Seattle");
        this.dc = new City("DC");
        this.nyc = new City("NYC");

        this.travelGraph.addVertex(this.seattle);
        this.travelGraph.addVertex(this.dc);
        this.travelGraph.addVertex(this.nyc);
        /**
         * the map demonstrated below have general paths:
         * a->b, b->a, b->c
         */
        //setting up the two Transports One Edge case. BFS should choose on transport arbitrarily
        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.dc, TransportType.PLANE, 100.0, 30));
        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.dc, TransportType.TRAIN, 30.0, 80));
        //setting up bidirectional case
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.seattle, TransportType.PLANE, 100.0, 30));
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.seattle, TransportType.TRAIN, 30.0, 80));
        //and no path between a-c or c-a
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.nyc, TransportType.PLANE, 150.0, 120));
    }



    @Test
    public void testTwoTransports(){
        this.makeSolGraph();
        IDijkstra dijkstra = new Dijkstra<>();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getPrice();

        List<Transport> path =
                dijkstra.getShortestPath(this.travelGraph, this.seattle, this.dc, edgeWeightCalculation);
        assertEquals(30, this.travelGraph.getTotalPrice(path), DELTA);
        assertEquals(1, path.size());
    }

    @Test
    public void testBidirectional(){
        this.makeSolGraph();
        IDijkstra dijkstra = new Dijkstra<>();
        Function<Transport, Double> edgeWeightTime = e -> e.getMinutes();
        Function<Transport, Double> edgeWeightPrice = e -> e.getPrice();
        List<Transport> pathTime =
                dijkstra.getShortestPath(this.travelGraph, this.dc, this.seattle, edgeWeightTime);
        assertEquals(30, this.travelGraph.getTotalTime(pathTime), DELTA);
        List<Transport> pathPrice =
                dijkstra.getShortestPath(this.travelGraph, this.dc, this.seattle, edgeWeightPrice);
        assertEquals(30, this.travelGraph.getTotalPrice(pathPrice), DELTA);
        assertEquals(1, pathTime.size());
        assertEquals(1, pathPrice.size());
    }

    @Test
    public void testIsolation(){
        this.makeSolGraph();
        IDijkstra dijkstra = new Dijkstra<>();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getPrice();

        //a can get to c
        List<Transport> path =
                dijkstra.getShortestPath(this.travelGraph, this.seattle, this.nyc, edgeWeightCalculation);
        assertEquals(180, this.travelGraph.getTotalPrice(path), DELTA);
        assertEquals(2, path.size());

        //c cannot get to a
        List<Transport> pathReverse =
                dijkstra.getShortestPath(this.travelGraph, this.nyc, this.seattle, edgeWeightCalculation);
        assertEquals(0, this.travelGraph.getTotalPrice(pathReverse), DELTA); //terminal saying this should be 180
        assertEquals(0, pathReverse.size());
    }

    public void makeLineGraph() {
        this.travelGraph = new TravelGraph();
        this.seattle = new City("Seattle");
        this.dc = new City("DC");
        this.nyc = new City("NYC");

        this.travelGraph.addVertex(this.seattle);
        this.travelGraph.addVertex(this.dc);
        this.travelGraph.addVertex(this.nyc);

        /**
         * the map demonstrated below have general paths:
         * a->b, b->c
         */
        //setting up the two Transports One Edge case. BFS should choose on transport arbitrarily
        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.dc, TransportType.PLANE, 100.0, 30));
        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.dc, TransportType.BUS, 30.0, 80));

        //and path from b-c
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.nyc, TransportType.PLANE, 150.0, 120));
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.nyc, TransportType.BUS, 50.0, 190));

    }


    @Test
    public void testLinearPath(){
        this.makeLineGraph();
        Dijkstra dijkstra = new Dijkstra();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getPrice();
        //it chose one of the Transports arbitrarily
        List<Transport> path =
                dijkstra.getShortestPath(this.travelGraph, this.seattle, this.nyc, edgeWeightCalculation);
        assertEquals(this.travelGraph.getTotalPrice(path), 80.0, DELTA);
        assertEquals(path.size(), 2);
    }

    @Test
    public void testOriginDestSame(){
        //test the case when the origin node and the destination node are the same
        this.makeSolGraph();
        IDijkstra dijkstra = new Dijkstra<>();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getPrice();

        List<Transport> path =
                dijkstra.getShortestPath(this.travelGraph, this.seattle, this.seattle, edgeWeightCalculation);
        assertEquals(0, this.travelGraph.getTotalPrice(path), DELTA);
        assertEquals(0, path.size());
    }

    public void makeManyPathGraph() {
        this.travelGraph = new TravelGraph();
        this.seattle = new City("Seattle");
        this.dc = new City("DC");
        this.nyc = new City("NYC");

        this.travelGraph.addVertex(this.seattle);
        this.travelGraph.addVertex(this.dc);
        this.travelGraph.addVertex(this.nyc);

        /**
         * the map demonstrated below have general paths:
         * a->b, b->a, b->c
         */
        //setting up the two Transports One Edge case. BFS should choose on transport arbitrarily
        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.dc, TransportType.PLANE, 100.0, 30));
        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.dc, TransportType.PLANE, 30.0, 80));
        //setting up bidirectional case
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.seattle, TransportType.PLANE, 100.0, 30));
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.seattle, TransportType.PLANE, 30.0, 80));
        //and no path between a-c or c-a
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.nyc, TransportType.PLANE, 150.0, 120));
        this.travelGraph.addEdge(this.nyc, new Transport(this.nyc, this.dc, TransportType.BUS, 40.0, 200));

        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.nyc, TransportType.PLANE, 200.0, 50));
        this.travelGraph.addEdge(this.nyc, new Transport(this.nyc, this.seattle, TransportType.PLANE, 220.0, 60));

    }

    @Test
    public void testManyPaths(){
        this.makeManyPathGraph();
        Dijkstra dijkstra = new Dijkstra();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getPrice();
        //it chose one of the Transports arbitrarily
        List<Transport> path =
                dijkstra.getShortestPath(this.travelGraph, this.seattle, this.nyc, edgeWeightCalculation);
        assertEquals(this.travelGraph.getTotalPrice(path), 180.0, DELTA);
        assertEquals(path.size(), 2);

        List<Transport> path2 =
                dijkstra.getShortestPath(this.travelGraph, this.seattle, this.seattle, edgeWeightCalculation);
        assertEquals(this.travelGraph.getTotalPrice(path2), 0.0, DELTA);
        assertEquals(path2.size(), 0);
    }


}
