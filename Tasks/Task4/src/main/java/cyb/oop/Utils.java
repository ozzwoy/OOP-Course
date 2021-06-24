package cyb.oop;

class Utils<T> {

    private boolean findMarkedSuccessor(boolean[] marked, Node<T>[] previousNode, int level,
                                        Node<T>[] curr, Node<T>[] successor) {
        boolean snip;
        boolean backToFirstCycle = false;
        while (marked[0]) {
            snip = previousNode[0].getNext()[level].compareAndSet(curr[0], successor[0],
                    false, false);
            if (!snip) {
                backToFirstCycle = true;
                break;
            }
            curr[0] = previousNode[0].getNext()[level].getReference();
            successor[0] = curr[0].getNext()[level].get(marked);
        }
        return backToFirstCycle;
    }

    private boolean findNodesForNextIteration(boolean[] marked, Node<T>[] previousNode, int level,
                                              Node<T>[] curr, Node<T>[] successor, int key) {
        while (true) {
            successor[0] = curr[0].getNext()[level].get(marked);
            boolean backToFirstCycle = findMarkedSuccessor(marked, previousNode, level, curr, successor);
            if (backToFirstCycle) {
                return true;
            }
            if (curr[0].getKey() < key){
                previousNode[0] = curr[0];
                curr[0] = successor[0];
            } else {
                return false;
            }
        }
    }

    private boolean findPredecessorAndSuccessorForLevel(Node<T>[] predecessors, Node<T>[] successors,
                                                        boolean[] marked, Node<T>[] predecessor, int level,
                                                        Node<T>[] curr, Node<T>[] successor, int key) {
        curr[0] = predecessor[0].getNext()[level].getReference();
        if (findNodesForNextIteration(marked, predecessor, level, curr, successor, key)) {
            return true;
        }
        predecessors[level] = predecessor[0];
        successors[level] = curr[0];
        return false;
    }

    @SuppressWarnings("unchecked")
    boolean findAllPredecessorsAndSuccessors(Node<T> head, T value, Node<T>[] predecessors, Node<T>[] successors) {
        int bottomLevel = 0;
        int key = value.hashCode();
        boolean[] marked = {false};
        Node<T>[] predecessor = new Node[1], curr = new Node[1], successor = new Node[1];
        boolean backToFirstCycle;
        while (true) {
            backToFirstCycle = false;
            predecessor[0] = head;
            for (int level = LockFreeSkipList.getMaxHeight(); level >= bottomLevel; level--) {
                backToFirstCycle = findPredecessorAndSuccessorForLevel(predecessors, successors, marked, predecessor, level,
                        curr, successor, key);
            }
            if (backToFirstCycle) {
                continue;
            }
            return (curr[0].getKey() == key);
        }
    }


    void markingNodeToRemove(Node<T> nodeToRemove, int bottomLevel) {
        for (int level = nodeToRemove.getLevelHeight(); level >= bottomLevel + 1; level--) {
            boolean[] marked = {false};
            Node<T> successor = nodeToRemove.getNext()[level].get(marked);
            while (!marked[0]) {
                nodeToRemove.getNext()[level].attemptMark(successor, true);
                successor = nodeToRemove.getNext()[level].get(marked);
            }
        }
    }

    boolean markingNextInRemovedNode(Node<T> nodeToRemove, int bottomLevel, T value,
                                     Node<T>[] successors, Node<T>[] predecessors, Node<T> head) {
        boolean[] marked = {false};
        while (true) {
            Node<T> successor = successors[bottomLevel].getNext()[bottomLevel].get(marked);
            if (nodeToRemove.getNext()[bottomLevel].compareAndSet(successor, successor,
                    false, true)) {
                findAllPredecessorsAndSuccessors(head, value, predecessors, successors);
                return true;
            }
            else if (marked[0]) {
                return false;
            }
        }
    }

    void setNextToInsertNode(Node<T> nodeToInsert, int bottomLevelIndex, int topLevelIndex,
                             Node<T>[] successors) {
        for (int level = bottomLevelIndex; level <= topLevelIndex; level++) {
            Node<T> successor = successors[level];
            nodeToInsert.getNext()[level].set(successor, false);
        }
    }

    void insertEveryLevel(int bottomLevelIndex, int topLevelIndex, Node<T> nodeToInsert, T value,
                          Node<T>[] predecessors, Node<T>[] successors, Node<T> head) {
        for (int level = bottomLevelIndex + 1; level <= topLevelIndex; level++) {
            while (true) {
                Node<T> predecessor = predecessors[level];
                Node<T> successor = successors[level];
                if (predecessor.getNext()[level].compareAndSet(successor, nodeToInsert, false, false))
                    break;
                findAllPredecessorsAndSuccessors(head, value, predecessors, successors);
            }
        }
    }
}
