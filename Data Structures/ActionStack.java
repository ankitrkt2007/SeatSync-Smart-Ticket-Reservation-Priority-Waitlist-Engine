package ds;

import model.ActionRecord;
import java.util.ArrayList;
import java.util.List;

public class ActionStack {
    private final List<ActionRecord> stack = new ArrayList<>();

    public void push(ActionRecord record) {
        stack.add(record);
    }

    public ActionRecord pop() {
        if (isEmpty()) {
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    public ActionRecord peek() {
        if (isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void printStack() {
        if (stack.isEmpty()) {
            System.out.println("  Action Stack is empty.");
            return;
        }
        System.out.println("  Action Stack (Top at end):");
        for (int i = stack.size() - 1; i >= 0; i--) {
            String marker = (i == stack.size() - 1) ? " <-- TOP" : "";
            System.out.println("    " + stack.get(i) + marker);
        }
    }
}
