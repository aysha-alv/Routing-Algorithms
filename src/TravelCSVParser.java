package src;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

/**
 * Class that parses the CSV files using the functions passed in as arguments
 * <p>
 * This class uses a Java CSVParser. The JavaDocs for this parser can be found here:
 * https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVParser.html
 */
public class TravelCSVParser {

    private static final CSVFormat FORMAT = CSVFormat.RFC4180.withDelimiter(',').withHeader();

    /**
     * Constructor for the CSV parser (empty)
     */
    public TravelCSVParser() {
    }

    /**
     * parses a location file using the function you will pass in as an argument and
     * creates vertices for each row in a cities CSV file
     *
     * @param locationFile path to csv file containing location (ex: data/cities1.csv)
     * @param handleLoc    function that takes a Map (with key "name"and a location as the value) and creates a 
     * vertex representing that location in your graph class(es).
     */
    public void parseLocations(String locationFile, Function<Map<String, String>, Void> handleLoc)
        throws IOException {
        CSVParser parser = new CSVParser(new FileReader(locationFile), FORMAT);

        // Applies the handleLoc function to each Map (represents one row) created by the parser
        for (CSVRecord record : parser) {
            handleLoc.apply(record.toMap()); //the function addVertex in TravelController applies this
        }
    }

    /**
     * parses a transportation file using the function you will pass in as an argument
     *
     * @param transportationFile path to csv file containing transportation information (ex: data/transport1.csv)
     * @param handleTransport    function that takes a map with keys "origin", "destination", "type", "price", and "cost"
     * and builds an edge in your graph accordingly.
     * 
     *  Note that Map values are always strings, even though the price and cost are numeric. 
     * If m refers to the Map created by the parser, you could get the price as a number by writing:
     * Double.parseDouble(m.get("price"))
     */
    public void parseTransportation(String transportationFile,
                                    Function<Map<String, String>, Void> handleTransport)
        throws IOException {
        CSVParser parser = new CSVParser(new FileReader(transportationFile), FORMAT);

        // Applies the handleTransport function to each Map (represents one row) created by the parser
        for (CSVRecord record : parser) {
            handleTransport.apply(record.toMap());
        }
    }
}
