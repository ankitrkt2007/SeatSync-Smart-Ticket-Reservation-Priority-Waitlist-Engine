package model;

public class Passenger {
    private final String name;
    private final int age;
    // 1 = Emergency/Medical, 2 = Tatkal, 3 = General
    private final int priority;

    public Passenger(String name, int age, int priority) {
        this.name = name;
        this.age = age;
        this.priority = (priority < 1 || priority > 3) ? 3 : priority;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getPriority() {
        return priority;
    }

    public String getPriorityLabel() {
        return switch (priority) {
            case 1 -> "P1-Emergency";
            case 2 -> "P2-Tatkal";
            default -> "P3-General";
        };
    }

    @Override
    public String toString() {
        return name + " (Age: " + age + ", " + getPriorityLabel() + ")";
    }
}
