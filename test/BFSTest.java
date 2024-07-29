package test;

import org.junit.Assert;
import org.junit.Test;
import sol.BFS;
import sol.TravelGraph;
import sol.City;
import sol.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 */
public class BFSTest {

    private static final double DELTA = 0.001;
    //Simple graph setup
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;
    //Our graph setup
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
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }


    /**
     * A sample test that tests BFS on a simple graph. Checks that running BFS gives us the path we expect.
     */
    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    /**
     * for testing bidirectional, two transports on the same edge, and the case where
     * a node cannot go to another under the Simple
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
        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.dc, TransportType.PLANE, 30.0, 80));
        //setting up bidirectional case
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.seattle, TransportType.PLANE, 100.0, 30));
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.seattle, TransportType.PLANE, 30.0, 80));
        //and no path between a-c or c-a
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.nyc, TransportType.PLANE, 150.0, 120));
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
        //and path from b-c
        this.travelGraph.addEdge(this.dc, new Transport(this.dc, this.nyc, TransportType.PLANE, 150.0, 120));
    }


    @Test
    public void testLinearPath(){
        this.makeLineGraph();
        BFS bfs = new BFS<>();
        List<Transport> path = bfs.getPath(this.travelGraph, this.seattle, this.nyc);
        //it chose one of the Transports arbitrarily
        assertEquals(this.travelGraph.getTotalPrice(path), 250.0, DELTA);
        assertEquals(path.size(), 2);
    }


    @Test
    public void testBidirectional(){
        this.makeSolGraph();
        BFS bfs = new BFS<>();
        List<Transport> path = bfs.getPath(this.travelGraph, this.dc, this.seattle);
        //it chose one of the Transports arbitrarily
        assertEquals(path.size(), 1);
    }

    @Test
    public void testIsolation(){
        this.makeSolGraph();
        BFS bfs = new BFS<>();
        //from a can get to c
        List<Transport> path = bfs.getPath(this.travelGraph, this.seattle, this.nyc);
        assertEquals(this.travelGraph.getTotalPrice(path), 180.0, DELTA);
        assertEquals(path.size(), 2);
        //from c cannot get to a
        List<Transport> pathReverse = bfs.getPath(this.travelGraph, this.nyc, this.seattle);
        assertEquals(this.travelGraph.getTotalPrice(pathReverse), 0.0, DELTA);
        assertEquals(pathReverse.size(), 0);
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
        this.travelGraph.addEdge(this.nyc, new Transport(this.nyc, this.dc, TransportType.PLANE, 150.0, 100));

        this.travelGraph.addEdge(this.seattle, new Transport(this.seattle, this.nyc, TransportType.PLANE, 200.0, 50));
        this.travelGraph.addEdge(this.nyc, new Transport(this.nyc, this.seattle, TransportType.PLANE, 220.0, 60));

    }

    @Test
    public void testManyPaths(){
        this.makeSolGraph();
        BFS bfs = new BFS<>();
        List<Transport> path = bfs.getPath(this.travelGraph, this.seattle, this.nyc);
        //it chose one of the Transports arbitrarily
        assertEquals(path.size(), 2);

        List<Transport> path2 = bfs.getPath(this.travelGraph, this.seattle, this.seattle);
        assertEquals(path2.size(), 0);

    }

    @Test
    public void testOriginDestSame(){
        //test the case when the origin node and the destination node are the same
        this.makeSolGraph();
        BFS bfs = new BFS<>();
        List<Transport> path = bfs.getPath(this.travelGraph, this.seattle, this.seattle);
        assertEquals(this.travelGraph.getTotalPrice(path), 0.0, DELTA);
        assertEquals(path.size(), 0);
    }

    @Test
    public void testTwoTransports(){
        this.makeSolGraph();
        BFS bfs = new BFS<>();
        List<Transport> path = bfs.getPath(this.travelGraph, this.seattle, this.dc);
        //it chose one of the Transports arbitrarily
        assertEquals(path.size(), 1);
    }
}
