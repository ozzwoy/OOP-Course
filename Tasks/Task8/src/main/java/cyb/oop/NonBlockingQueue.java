package cyb.oop;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

class Node<E> {
    public E value;
    public volatile Node<E> next;
    protected long offset;
    protected Unsafe unsafe;

    Node(E value, Node<E> next) throws NoSuchFieldException, SecurityException, IllegalAccessException {
        this.value = value;
        this.next = next;
        unsafe = getUnsafe();
        offset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
    }

    private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }
}

public class NonBlockingQueue<E> {
    private final Node<E> dummy;
    private volatile Node<E> head;
    private volatile Node<E> tail;
    private final long offsetHead;
    private final long offsetTail;
    private final Unsafe unsafe;

    private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    public NonBlockingQueue() throws NoSuchFieldException, SecurityException, IllegalAccessException {
        dummy = new Node<E>(null, null);
        head = dummy;
        tail = dummy;
        unsafe = getUnsafe();
        offsetHead = unsafe.objectFieldOffset(NonBlockingQueue.class.getDeclaredField("head"));
        offsetTail = unsafe.objectFieldOffset(NonBlockingQueue.class.getDeclaredField("tail"));
    }

    public void enqueue(E value) throws NoSuchFieldException, SecurityException, IllegalAccessException {
        Node<E> new_node = new Node<E>(value, null);
        while (true) {
            Node<E> currentTail = tail;
            Node<E> tailNext = currentTail.next;
            if (currentTail == tail) {
                if (tailNext != null) {
                    unsafe.compareAndSwapObject(this, offsetTail, currentTail, tailNext);
                    //tail.compareAndSet(currentTail, tailNext);
                } else {
                    if (currentTail.unsafe.compareAndSwapObject(currentTail,currentTail.offset, null, new_node)){
                        unsafe.compareAndSwapObject(this, offsetTail, currentTail, new_node);
                        return;
                    }
                }
            }
        }
    }

    public E dequeue() {
        if(head == dummy) {
            unsafe.compareAndSwapObject(this, offsetHead,dummy,dummy.next);
        }
        while (true) {
            Node<E> first = head;
            Node<E> last = tail;
            Node<E> next = first.next;
            if (first == head) {
                if (first == last) {
                    if (next == null) {
                        return null;
                    }
                    unsafe.compareAndSwapObject(this, offsetTail, last, next);
                } else {
                    E value = first.value;
                    if (unsafe.compareAndSwapObject(this, offsetHead, first, next)) {
                        return value;
                    }
                }
            }
        }
    }
}
