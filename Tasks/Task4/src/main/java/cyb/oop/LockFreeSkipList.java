package cyb.oop;

import java.util.Random;

public class LockFreeSkipList<T> {
    private static final int MAX_HEIGHT = 3;
    private static final Random random = new Random();
    private final Utils<T> helper = new Utils<>();

    private final Node<T> head = new Node<>(Integer.MIN_VALUE);

    static int getMaxHeight() {
        return MAX_HEIGHT;
    }

    public LockFreeSkipList() {
        for (int i = 0; i < head.getNext().length; i++) {
            head.getNext()[i].set(new Node<>(Integer.MAX_VALUE), false);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean add(T value) {
        int topLevelIndex = getRandomHeight();
        int bottomLevelIndex = 0;
        Node<T>[] predecessors = new Node[MAX_HEIGHT + 1];
        Node<T>[] successors = new Node[MAX_HEIGHT + 1];
        while (true) {
            if (helper.findAllPredecessorsAndSuccessors(head, value, predecessors, successors)) {
                return false;
            } else {
                Node<T> nodeToInsert = new Node<>(value, topLevelIndex);
                helper.setNextToInsertNode(nodeToInsert, bottomLevelIndex, topLevelIndex, successors);

                Node<T> predecessor = predecessors[bottomLevelIndex];
                Node<T> successor = successors[bottomLevelIndex];
                if (!predecessor.getNext()[bottomLevelIndex].compareAndSet(successor, nodeToInsert,
                        false, false)) {
                    continue;
                }
                helper.insertEveryLevel(bottomLevelIndex, topLevelIndex, nodeToInsert, value,
                        predecessors, successors, head);
                return true;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public boolean remove(T value) {
        int bottomLevel = 0;
        Node<T>[] predecessors = new Node[MAX_HEIGHT + 1];
        Node<T>[] successors = new Node[MAX_HEIGHT + 1];

        if (!helper.findAllPredecessorsAndSuccessors(head, value, predecessors, successors)) {
            return false;
        } else {
            Node<T> nodeToRemove = successors[bottomLevel];
            helper.markingNodeToRemove(nodeToRemove, bottomLevel);

            return helper.markingNextInRemovedNode(nodeToRemove, bottomLevel, value, successors, predecessors, head);
        }
    }

    public boolean contains(T value) {
        int valueHash = value.hashCode();
        boolean[] marked = {false};
        Node<T> pred = head, curr = null, successor;
        for (int level = MAX_HEIGHT; level >= 0; level--) {
            curr = pred.getNext()[level].getReference();
            while (true) {
                successor = curr.getNext()[level].get(marked);
                while (marked[0]) {
                    curr = pred.getNext()[level].getReference();
                    successor = curr.getNext()[level].get(marked);
                }
                if (curr.getKey() < valueHash){
                    pred = curr;
                    curr = successor;
                } else {
                    break;
                }
            }
        }
        return (curr.getKey() == valueHash);
    }

    private int getRandomHeight() {
        for (int i = 0; i < MAX_HEIGHT; i++) {
            if (random.nextBoolean()) {
                return i;
            }
        }
        return MAX_HEIGHT - 1;
    }
}
