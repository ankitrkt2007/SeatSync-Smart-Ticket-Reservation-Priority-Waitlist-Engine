package ds;

public class SeatBST {
    private static class Node {
        int seatNumber;
        Node left, right;

        Node(int seatNumber) {
            this.seatNumber = seatNumber;
        }
    }

    private Node root;

    public void insert(int seatNumber) {
        root = insertRec(root, seatNumber);
    }

    private Node insertRec(Node node, int seatNumber) {
        if (node == null) {
            return new Node(seatNumber);
        }
        if (seatNumber < node.seatNumber) {
            node.left = insertRec(node.left, seatNumber);
        } else if (seatNumber > node.seatNumber) {
            node.right = insertRec(node.right, seatNumber);
        }
        return node;
    }

    public Integer popSmallestSeat() {
        if (root == null) {
            return null;
        }
        int smallest = findMin(root);
        delete(smallest);
        return smallest;
    }

    private int findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.seatNumber;
    }

    public void delete(int seatNumber) {
        root = deleteRec(root, seatNumber);
    }

    private Node deleteRec(Node node, int seatNumber) {
        if (node == null) {
            return null;
        }
        if (seatNumber < node.seatNumber) {
            node.left = deleteRec(node.left, seatNumber);
        } else if (seatNumber > node.seatNumber) {
            node.right = deleteRec(node.right, seatNumber);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            node.seatNumber = findMin(node.right);
            node.right = deleteRec(node.right, node.seatNumber);
        }
        return node;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void printInOrder() {
        System.out.print("  Available Seats (BST In-Order): [ ");
        inOrderRec(root);
        System.out.println("]");
    }

    private void inOrderRec(Node node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.seatNumber + " ");
            inOrderRec(node.right);
        }
    }
}
