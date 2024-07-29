package test;

import org.junit.Assert;
import org.junit.Test;
import sol.City;
import sol.Transport;
import sol.TravelGraph;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;
    private Transport seattleDCPlane;
    private Transport seattleDCTrain;
    private Transport DCSeattlePlane;
    private Transport DCSeattleTrain;
    private Transport DCNYCPlane;
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

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    /**
     * Sample test for the graph. Tests that the number of vertices and the vertices in the graph are what we expect.
     */
    @Test
    public void testGetVertices() {
        this.createSimpleGraph();
        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

    public void makeSolGraph() {
        this.travelGraph = new TravelGraph();
        this.seattle = new City("Seattle");
        this.dc = new City("DC");
        this.nyc = new City("NYC");

        this.travelGraph.addVertex(this.seattle);
        this.travelGraph.addVertex(this.dc);
        this.travelGraph.addVertex(this.nyc);

        this.seattleDCPlane = new Transport(this.seattle, this.dc, TransportType.PLANE, 100.0, 30);
        this.seattleDCTrain = new Transport(this.seattle, this.dc, TransportType.TRAIN, 30.0, 80);
        this.DCSeattlePlane = new Transport(this.dc, this.seattle, TransportType.PLANE, 100.0, 30);
        this.DCSeattleTrain = new Transport(this.dc, this.seattle, TransportType.TRAIN, 30.0, 80);
        this.DCNYCPlane = new Transport(this.dc, this.nyc, TransportType.PLANE, 150.0, 120);
        /**
         * the map demonstrated below have general paths:
         * a->b, b->a, b->c
         */
        //setting up the two Transports One Edge case. BFS should choose on transport arbitrarily
        this.travelGraph.addEdge(this.seattle, this.seattleDCPlane);
        this.travelGraph.addEdge(this.seattle, this.seattleDCTrain);
        //setting up bidirectional case
        this.travelGraph.addEdge(this.dc, this.DCSeattlePlane);
        this.travelGraph.addEdge(this.dc, this.DCSeattleTrain);
        //and no path between a-c or c-a
        this.travelGraph.addEdge(this.dc, this.DCNYCPlane);
    }
    /**
     * Test if the outgoingEdges in City is created correctly
     */
    @Test
    public void testAddEdge(){
        this.makeSolGraph();
        assertTrue(this.seattle.getOutgoing().contains(this.seattleDCTrain));
        assertTrue(this.seattle.getOutgoing().contains(this.seattleDCPlane));
        assertTrue(this.dc.getOutgoing().contains(this.DCSeattlePlane));
        assertTrue(this.dc.getOutgoing().contains(this.DCSeattleTrain));
        assertTrue(this.dc.getOutgoing().contains(this.DCNYCPlane));
    }

    @Test
    public void testGetEdgeSource(){
        this.makeSolGraph();
        assertEquals(this.seattle, this.travelGraph.getEdgeSource(this.seattleDCPlane));
        assertEquals(this.seattle, this.travelGraph.getEdgeSource(this.seattleDCTrain));
        assertEquals(this.dc, this.travelGraph.getEdgeSource(this.DCSeattlePlane));
        assertEquals(this.dc, this.travelGraph.getEdgeSource(this.DCSeattleTrain));
        assertEquals(this.dc, this.travelGraph.getEdgeSource(this.DCNYCPlane));
    }

    @Test
    public void testGetEdgeTarget(){
        this.makeSolGraph();
        assertEquals(this.dc, this.travelGraph.getEdgeTarget(this.seattleDCPlane));
        assertEquals(this.dc, this.travelGraph.getEdgeTarget(this.seattleDCTrain));
        assertEquals(this.seattle, this.travelGraph.getEdgeTarget(this.DCSeattlePlane));
        assertEquals(this.seattle, this.travelGraph.getEdgeTarget(this.DCSeattleTrain));
        assertEquals(this.nyc, this.travelGraph.getEdgeTarget(this.DCNYCPlane));
    }

    @Test
    public void testGetCity(){
        this.makeSolGraph();
        assertEquals(this.seattle, this.travelGraph.getCity("Seattle"));
        assertEquals(this.dc, this.travelGraph.getCity("DC"));
        assertEquals(this.nyc, this.travelGraph.getCity("NYC"));
    }

}
