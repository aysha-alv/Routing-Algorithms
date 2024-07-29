package src;

import sol.City;
import sol.Transport;
import sol.TravelController;

/**
 * The Main class for the program.
 */
public class Main {
    /**
     * Main method that sets up the REPL and runs it.
     * @param args arguments passed into main
     */
    public static void main(String[] args) {
        REPL<City, Transport> repl = new REPL<>(new TravelController());
        repl.run();
    }
}
