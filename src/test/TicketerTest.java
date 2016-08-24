import org.junit.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TicketerTest {

    @Test
    public void shouldSellAllTickets() throws Exception {
        Ticketer ticketer = new Ticketer(50);

        assertThat(ticketer.buy(50).size(), is(50));
        assertThat(ticketer.buy(1).size(), is(0));
    }

    @Test
    public void shouldSellToMultipleUsers() throws Exception {
        Ticketer ticketer = new Ticketer(2000);

        Set<String> user1Purchases = new ConcurrentSkipListSet<>();
        Set<String> user2Purchases = new ConcurrentSkipListSet<>();

        // simulate two users buying tickets at different rates
        Thread user1 = new Thread(() -> {
            int lastPurchaseResult = 1;
            while (lastPurchaseResult > 0) {
                Set<String> tickets = ticketer.buy(2);
                lastPurchaseResult = tickets.size();
                user1Purchases.addAll(tickets);

            }
        });

        Thread user2 = new Thread(() -> {
            int lastPurchaseResult = 1;
            while (lastPurchaseResult > 0) {
                Set<String> tickets = ticketer.buy(3);
                lastPurchaseResult = tickets.size();
                user2Purchases.addAll(tickets);

            }
        });

        // start both threads
        user1.start();
        user2.start();

        // wait for both threads to finish
        user1.join(5000);
        user2.join(5000);

        // the total number of acquired tickets must match the number of available tickets
        int total = user1Purchases.size() + user2Purchases.size();
        assertThat(total, is(2000));
    }
}