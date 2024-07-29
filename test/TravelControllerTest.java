package test;

import org.junit.Test;
import sol.City;
import sol.TravelController;
import sol.Transport;
import src.TransportType;

import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TravelControllerTest {

    //todo use load to put in csv file
    //todo run getCheapest, getFastest tests on loaded file
private TravelController tc;

    private void createLoad() {
        this.tc = new TravelController();
        tc.load("/Users/ayshaallahverdi/Desktop/CS200/pr02-aallahve-ychen235/data/cities1.csv", "/Users/ayshaallahverdi/Desktop/CS200/pr02-aallahve-ychen235/data/transport1.csv");
    }

    @Test
    public void testmostDirect(){

        List<Transport> list = new LinkedList<Transport>();

        City providence = new City("Providence");
        City boston = new City("Boston");
        City nyc = new City("New York City");

        Transport pvdBosTrain = new Transport(providence, nyc, TransportType.TRAIN, 13, 80);
        Transport pvdBosBus = new Transport(providence, nyc, TransportType.BUS, 7, 150);
        Transport bosNYC = new Transport(boston, nyc, TransportType.PLANE, 267, 50);

        list.add(pvdBosTrain);
        list.add(pvdBosBus);
        list.add(bosNYC);

        List<Transport> BFSRoute = tc.mostDirectRoute("Providence", "New York City");

        assertEquals(list, BFSRoute);
    }

}
