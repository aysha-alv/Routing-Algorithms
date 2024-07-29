package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * A class that provides the interactable interface used when running the program
 * DO NOT edit this class! We've written it for you, but you will implement the ITravelController methods called here
 *
 * @param <V> the type of the vertices
 * @param <E> the type of edge
 */
public class REPL<V, E> {

    private static final String REPL_REGEX = "\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?";

    private ITravelController<V, E> controller;

    /**
     * Constructor for the REPL class.
     * @param controller the TravelController to use
     */
    public REPL(ITravelController<V, E> controller) {
        this.controller = controller;
    }

    /**
     * Removes single/double quotes from a string if necessary
     *
     * @param str string to be stripped
     * @return the string without quotes
     */
    private String stripQuote(String str) {
        int last = str.length() - 1;
        if (last == -1) {
            return str;
        }
        if ((str.charAt(0) == '\'' || str.charAt(0) == '\"')
            && str.charAt(last) == '\'' || str.charAt(last) == '\"') {
            return str.substring(0, last);
        }
        return str;
    }

    /**
     * Runs the REPL which executes user-inputted commands
     * This method is called in sol/Main.java to executing the REPL
     */
    public void run() {
        System.out.print(">>> ");
        try (BufferedReader reader =
                 new BufferedReader(new InputStreamReader(System.in))) {
            String line = reader.readLine();
            // While loop continuously reads and executes user input during program execution
            while (line != null) {
                // Formats the user input before running commands
                String response = "";
                String[] args = line.split(REPL_REGEX);
                args = Arrays.stream(args).map(x -> this.stripQuote(x)).toArray(String[]::new);
                if (args.length == 0) {
                    continue;
                }
                String command = args[0];
                // Based on the user-inputted commands, calls the corresponding ITravelController method
                switch (command) {
                    case "load":
                        if (args.length == 3) {
                            try {
                                response = this.controller.load(args[1], args[2]);
                            } catch (Exception e) {
                                response = e.getMessage();
                            }
                        } else {
                            response = "Usage: load [cities_file] [transport_file]";
                        }
                        break;
                    case "fast":
                        if (args.length == 3) {
                            String origin = args[1];
                            String destination = args[2];
                            try {
                                List<E> path = this.controller.fastestRoute(origin, destination);
                                response = this.getPathString(origin, destination, path);
                            } catch (Exception e) {
                                response = e.getMessage();
                            }
                        } else {
                            response = "Usage: fast [origin] [destination]";
                        }
                        break;
                    case "cheap":
                        if (args.length == 3) {
                            String origin = args[1];
                            String destination = args[2];
                            try {
                                List<E> path = this.controller.cheapestRoute(origin, destination);
                                response = this.getPathString(origin, destination, path);
                            } catch (Exception e) {
                                response = e.getMessage();
                            }
                        } else {
                            response = "Usage: cheap [origin] [destination]";
                        }
                        break;
                    case "direct":
                        if (args.length == 3) {
                            String origin = args[1];
                            String destination = args[2];
                            try {
                                List<E> path = this.controller.mostDirectRoute(origin, destination);
                                response = this.getPathString(origin, destination, path);
                            } catch (Exception e) {
                                response = e.getMessage();
                            }
                        } else {
                            response = "Usage: direct [origin] [destination]";
                        }
                        break;
                    default:
                        response = "Invalid command. Available commands: load, cheap, fast, direct";
                }
                System.out.println(response);
                System.out.print(">>> ");
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("IOException occurred.");
        }
    }

    /**
     * Helper to create a line for formatting the REPL output
     *
     * @param character the character to be repeated
     * @return the character repeated 80 times to form a line
     */
    private String line(String character) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            s.append(character);
        }
        return s.toString();
    }

    /**
     * Formats the path returned by the ITravelController methods to be printed in the REPL
     *
     * @param origin      the path's origin location
     * @param destination the path's ending destination location
     * @param path        a list of the path of transports that connect the origin to the destination
     * @return the string to be printed in the REPL
     */
    private String getPathString(String origin, String destination, List<E> path) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.line("=")).append("\n");

        if (path.isEmpty()) {
            sb.append("No route found").append("\n");
        } else {
            sb.append("Origin: ").append(origin.toString());
            sb.append("\nDestination: ").append(destination.toString()).append("\n");
            sb.append(this.line("-")).append("\n");

            for (E leg : path) {
                sb.append(" -- ").append(leg.toString()).append("\n");
            }
        }
        sb.append(this.line("="));
        return sb.toString();
    }
}