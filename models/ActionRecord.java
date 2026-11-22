package model;

public class ActionRecord {
    public enum ActionType {
        BOOK_CONFIRMED,
        BOOK_WAITLISTED,
        CANCEL_CONFIRMED,
        CANCEL_WAITLISTED
    }

    private final ActionType type;
    private final Ticket ticket;
    private final Integer previousSeat;
    private final Ticket.Status previousStatus;

    public ActionRecord(ActionType type, Ticket ticket, Integer previousSeat, Ticket.Status previousStatus) {
        this.type = type;
        this.ticket = ticket;
        this.previousSeat = previousSeat;
        this.previousStatus = previousStatus;
    }

    public ActionType getType() {
        return type;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Integer getPreviousSeat() {
        return previousSeat;
    }

    public Ticket.Status getPreviousStatus() {
        return previousStatus;
    }

    @Override
    public String toString() {
        return type + " -> PNR: " + ticket.getPnr() + " (" + ticket.getPassenger().getName() + ")";
    }
}
