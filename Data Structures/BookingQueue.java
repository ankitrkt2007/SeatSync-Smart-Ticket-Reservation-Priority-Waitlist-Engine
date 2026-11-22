package ds;

import model.Ticket;
import java.util.LinkedList;

public class BookingQueue {
    private final LinkedList<Ticket> queue = new LinkedList<>();

    public void enqueue(Ticket ticket) {
        queue.addLast(ticket);
    }

    public Ticket dequeue() {
        if (isEmpty()) {
            return null;
        }
        return queue.removeFirst();
    }

    public Ticket peek() {
        return queue.peekFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public void printQueue() {
        if (queue.isEmpty()) {
            System.out.println("  Booking Queue is empty.");
            return;
        }
        System.out.print("  FRONT -> ");
        for (Ticket t : queue) {
            System.out.print("[" + t.getPnr() + ":" + t.getPassenger().getName() + "] -> ");
        }
        System.out.println("REAR");
    }
}
