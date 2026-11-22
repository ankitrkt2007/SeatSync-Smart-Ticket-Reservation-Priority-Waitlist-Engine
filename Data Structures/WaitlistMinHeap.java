package ds;

import model.Ticket;
import java.util.ArrayList;
import java.util.List;

public class WaitlistMinHeap {
    private final List<Ticket> heap = new ArrayList<>();

    public void insert(Ticket ticket) {
        heap.add(ticket);
        heapifyUp(heap.size() - 1);
    }

    public Ticket extractMin() {
        if (heap.isEmpty()) {
            return null;
        }
        Ticket min = heap.get(0);
        Ticket last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    public Ticket peekMin() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public boolean removeTicket(Ticket target) {
        int idx = heap.indexOf(target);
        if (idx == -1) {
            return false;
        }
        Ticket last = heap.remove(heap.size() - 1);
        if (idx < heap.size()) {
            heap.set(idx, last);
            heapifyUp(idx);
            heapifyDown(idx);
        }
        return true;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(index).getPassenger().getPriority() < heap.get(parent).getPassenger().getPriority()) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap.get(left).getPassenger().getPriority() < heap.get(smallest).getPassenger().getPriority()) {
                smallest = left;
            }
            if (right < size && heap.get(right).getPassenger().getPriority() < heap.get(smallest).getPassenger().getPriority()) {
                smallest = right;
            }
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Ticket temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void printHeapArray() {
        if (heap.isEmpty()) {
            System.out.println("  Waitlist Min-Heap is empty.");
            return;
        }
        System.out.print("  Heap Array: ");
        for (int i = 0; i < heap.size(); i++) {
            Ticket t = heap.get(i);
            System.out.print("[idx=" + i + " P" + t.getPassenger().getPriority() + ":" + t.getPnr() + "] ");
        }
        System.out.println();
    }
}
