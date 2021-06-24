package cyb.oop;

import java.util.concurrent.atomic.AtomicMarkableReference;

class Node<T> {
    private int key;
    private T value;
    private int levelHeight;

    private AtomicMarkableReference<Node<T>>[] next;

    @SuppressWarnings("unchecked")
    public Node(int key) {
        this.key = key;
        this.value = null;
        this.next = new AtomicMarkableReference[LockFreeSkipList.getMaxHeight() + 1];
        this.levelHeight = LockFreeSkipList.getMaxHeight();
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<>(null, false);
        }
    }

    @SuppressWarnings("unchecked")
    public Node(T value, int height) {
        this.value = value;
        this.key = value.hashCode();
        this.levelHeight = height;
        this.next = new AtomicMarkableReference[levelHeight + 1];
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<>(null,false);
        }
    }

    public Node(int key, T value, int levelHeight, AtomicMarkableReference<Node<T>>[] next) {
        this.key = key;
        this.value = value;
        this.levelHeight = levelHeight;
        this.next = next;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }

    public AtomicMarkableReference<Node<T>>[]  getNext() {
        return next;
    }

    public void setNext(AtomicMarkableReference<Node<T>>[] next) {
        this.next = next;
    }
}
