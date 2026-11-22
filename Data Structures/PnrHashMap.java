package ds;

import model.Ticket;

public class PnrHashMap {
    private static class Entry {
        final String key;
        Ticket value;
        Entry next;

        Entry(String key, Ticket value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private final Entry[] buckets;
    private final int capacity;
    private int size;

    public PnrHashMap(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    private int hash(String key) {
        int h = 0;
        for (int i = 0; i < key.length(); i++) {
            h = (31 * h + key.charAt(i)) % capacity;
        }
        return Math.abs(h);
    }

    public void put(String key, Ticket value) {
        int index = hash(key);
        Entry current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        buckets[index] = new Entry(key, value, buckets[index]);
        size++;
    }

    public Ticket get(String key) {
        int index = hash(key);
        Entry current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void printBuckets() {
        System.out.println("  HashMap Buckets (Capacity=" + capacity + ", Entries=" + size + "):");
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null) {
                System.out.print("    Bucket[" + i + "]: ");
                Entry curr = buckets[i];
                while (curr != null) {
                    System.out.print("(" + curr.key + " -> " + curr.value.getStatus() + ")");
                    if (curr.next != null) {
                        System.out.print(" -> ");
                    }
                    curr = curr.next;
                }
                System.out.println();
            }
        }
    }
}
