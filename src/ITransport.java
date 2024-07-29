package src;

/**
 * An interface to represent a transport
 *
 */
public interface ITransport {
    /**
     * Gets the cost of this transport
     *
     * @return the price
     */
    public double getPrice();


    /**
     * Gets the travel time of this transport
     *
     * @return the time in minutes
     */
    public double getMinutes();

    /**
     * Gets the type of this transport, as a String ("Bus", "Plane", etc)
     *
     * @return the transport
     */
    public String getType();
}
