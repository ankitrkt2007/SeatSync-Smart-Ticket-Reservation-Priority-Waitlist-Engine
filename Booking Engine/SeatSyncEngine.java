package service;

import ds.*;
import model.*;

public class SeatSyncEngine {
    private final SeatBST availableSeats = new SeatBST();
    private final BookingQueue bookingQueue = new BookingQueue();
    private final WaitlistMinHeap waitlistHeap = new WaitlistMinHeap();
    private final PnrHashMap pnrMap = new PnrHashMap(17);
    private final ActionStack actionStack = new ActionStack();
    private int pnrCounter = 1001;

    public SeatSyncEngine(int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            availableSeats.insert(i);
        }
    }

    private String generatePnr() {
        return "PNR" + (pnrCounter++);
    }

    public Ticket requestBooking(String name, int age, int priority) {
        Passenger passenger = new Passenger(name, age, priority);
        String pnr = generatePnr();
        Ticket ticket = new Ticket(pnr, passenger, null, Ticket.Status.WAITLISTED);

        bookingQueue.enqueue(ticket);
        pnrMap.put(pnr, ticket);

        Ticket processed = bookingQueue.dequeue();
        if (!availableSeats.isEmpty()) {
            int assignedSeat = availableSeats.popSmallestSeat();
            processed.setSeatNumber(assignedSeat);
            processed.setStatus(Ticket.Status.CONFIRMED);
            actionStack.push(new ActionRecord(
                    ActionRecord.ActionType.BOOK_CONFIRMED,
                    processed,
                    null,
                    Ticket.Status.WAITLISTED
            ));
        } else {
            processed.setStatus(Ticket.Status.WAITLISTED);
            waitlistHeap.insert(processed);
            actionStack.push(new ActionRecord(
                    ActionRecord.ActionType.BOOK_WAITLISTED,
                    processed,
                    null,
                    Ticket.Status.WAITLISTED
            ));
        }
        return processed;
    }

    public Ticket lookupPnr(String pnr) {
        return pnrMap.get(pnr.trim().toUpperCase());
    }

    public String cancelTicket(String pnr) {
        Ticket ticket = lookupPnr(pnr);
        if (ticket == null) {
            return "PNR not found.";
        }
        if (ticket.getStatus() == Ticket.Status.CANCELLED) {
            return "Ticket is already cancelled.";
        }

        Integer prevSeat = ticket.getSeatNumber();
        Ticket.Status prevStatus = ticket.getStatus();

        if (prevStatus == Ticket.Status.CONFIRMED && prevSeat != null) {
            ticket.setStatus(Ticket.Status.CANCELLED);
            ticket.setSeatNumber(null);
            actionStack.push(new ActionRecord(
                    ActionRecord.ActionType.CANCEL_CONFIRMED,
                    ticket,
                    prevSeat,
                    Ticket.Status.CONFIRMED
            ));

            if (!waitlistHeap.isEmpty()) {
                Ticket promoted = waitlistHeap.extractMin();
                promoted.setSeatNumber(prevSeat);
                promoted.setStatus(Ticket.Status.CONFIRMED);
                return "Cancelled " + pnr + ". Freed Seat #" + prevSeat +
                       " promoted to highest-priority waitlist passenger: " + promoted;
            } else {
                availableSeats.insert(prevSeat);
                return "Cancelled " + pnr + ". Seat #" + prevSeat + " returned to Available Seats BST.";
            }
        } else {
            waitlistHeap.removeTicket(ticket);
            ticket.setStatus(Ticket.Status.CANCELLED);
            actionStack.push(new ActionRecord(
                    ActionRecord.ActionType.CANCEL_WAITLISTED,
                    ticket,
                    null,
                    Ticket.Status.WAITLISTED
            ));
            return "Cancelled waitlisted ticket " + pnr + ".";
        }
    }

    public String undoLastAction() {
        ActionRecord last = actionStack.pop();
        if (last == null) {
            return "Nothing to undo.";
        }

        Ticket t = last.getTicket();
        switch (last.getType()) {
            case BOOK_CONFIRMED -> {
                if (t.getSeatNumber() != null) {
                    availableSeats.insert(t.getSeatNumber());
                }
                t.setSeatNumber(null);
                t.setStatus(Ticket.Status.CANCELLED);
                return "Undid confirmed booking for " + t.getPnr() + ".";
            }
            case BOOK_WAITLISTED -> {
                waitlistHeap.removeTicket(t);
                t.setStatus(Ticket.Status.CANCELLED);
                return "Undid waitlisted booking for " + t.getPnr() + ".";
            }
            case CANCEL_CONFIRMED -> {
                Integer freedSeat = last.getPreviousSeat();
                if (freedSeat != null) {
                    availableSeats.delete(freedSeat);
                }
                t.setSeatNumber(freedSeat);
                t.setStatus(Ticket.Status.CONFIRMED);
                return "Undid cancellation for " + t.getPnr() + "; restored Seat #" + freedSeat + ".";
            }
            case CANCEL_WAITLISTED -> {
                t.setStatus(last.getPreviousStatus());
                waitlistHeap.insert(t);
                return "Undid waitlist cancellation for " + t.getPnr() + ".";
            }
        }
        return "Undo completed.";
    }

    public void inspectInternalStructures() {
        System.out.println("\n================ INTERNAL DATA STRUCTURES ================");
        availableSeats.printInOrder();
        bookingQueue.printQueue();
        waitlistHeap.printHeapArray();
        pnrMap.printBuckets();
        actionStack.printStack();
        System.out.println("==========================================================\n");
    }
}
