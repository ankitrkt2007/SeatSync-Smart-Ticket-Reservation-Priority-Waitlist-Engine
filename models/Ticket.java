package model;

public class Ticket {
    public enum Status {
        CONFIRMED,
        WAITLISTED,
        CANCELLED
    }

    private final String pnr;
    private final Passenger passenger;
    private Integer seatNumber;
    private Status status;

    public Ticket(String pnr, Passenger passenger, Integer seatNumber, Status status) {
        this.pnr = pnr;
        this.passenger = passenger;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public String getPnr() {
        return pnr;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String seatInfo = (seatNumber != null) ? "Seat #" + seatNumber : "No Seat";
        return String.format("[%s] %s | %s | %s", pnr, passenger, seatInfo, status);
    }
}
