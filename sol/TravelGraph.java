package sol;

import src.IGraph;
import test.simple.SimpleEdge;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Implementation for TravelGraph
 */
public class TravelGraph implements IGraph<City, Transport> {

    private Set<City> cities;

    public TravelGraph(){
        this.cities = new HashSet<City>();
    }

    @Override
    public void addVertex(City vertex) {
        this.cities.add(vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: check with TA. We might need to revise the method head according to hint
        //1.find the origin city in the set
        //2. use addOut() to add the edge
        for (City city : this.cities){
            if (city == origin){
                city.addOut(edge);
            }
        }
    }

    @Override
    public Set<City> getVertices() {
        return this.cities;
    }

    @Override
    public City getEdgeSource(Transport edge) {
        //1.loop through this.cities.
        //2. loop through each city's outgoing set
        for (City city : this.cities){
            for(Transport transport : city.getOutgoing()){
                if (transport == edge){
                    return transport.getSource();
                }
            }
        }
        return null;
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        for (City city : this.cities){
            for(Transport transport : city.getOutgoing()){
                if (transport == edge){
                    return transport.getTarget();
                }
            }
        }
        return null;
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        for (City city : this.cities){
            if (city == fromVertex){
                return city.getOutgoing();
            }
        }
        return null;
    }

    public City getCity(String cityName) {
        for (City city : this.cities) {
            if (city.toString().equals(cityName)) {
                return city;
            }
        } //exception later
        throw new RuntimeException();
    }

    public double getTotalPrice(List<Transport> paths){
        double total = 0;
        for (Transport edge : paths) {
            total += edge.getPrice();
        }
        return total;
    }

    public double getTotalTime(List<Transport> paths){
        double total = 0;
        for (Transport edge : paths) {
            total += edge.getMinutes();
        }
        return total;
    }


}



    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
