import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Ticketer {

    private Set<String> availableTickets;
    private static final Object LOCK = new Object();

    /**
     * Create a new ticketer by setting the initial amount of available tickets.
     * A ticket should consist of a unique string.
     *
     * @param initialAmountOfTickets Number of tickets this ticketer will contain initially
     */
    public Ticketer(int initialAmountOfTickets) {
        availableTickets = new HashSet<>();
        for (int i=0; i<initialAmountOfTickets; i++){
            availableTickets.add("ticket" + i);
        }
    }

    /**
     * Buys a certain number of tickets. If the number of available tickets is smaller than the
     * number of tickets to buy, then all the remaining tickets are sold.
     * <p>
     * This method should return empty if there are no tickets available.
     * <p>
     *
     * @param numberOfTickets The number of tickets to buy
     * @return The purchased tickets
     */


    public  Set<String> buy(int numberOfTickets) {
        Set<String> aux = new HashSet<>();
        synchronized (LOCK) {
            if (numberOfTickets > availableTickets.size()) {
                aux.addAll(availableTickets);
                availableTickets.clear();
            } else {
                System.out.println(Thread.currentThread().getName() + " running");
                aux = availableTickets.stream().limit(numberOfTickets).collect(Collectors.toSet());
                availableTickets.removeAll(aux);
                System.out.println(Thread.currentThread().getName() + " finished execution");
            }
        }
        return aux;
    }
}




