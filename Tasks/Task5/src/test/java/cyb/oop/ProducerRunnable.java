package cyb.oop;

import java.util.List;

public class ProducerRunnable<E> implements Runnable {
    private final List<E> items;
    private final DisruptorBuffer<E> buffer;

    public ProducerRunnable(List<E> items, DisruptorBuffer<E> buffer) {
        this.items = items;
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < items.size();) {
            if (buffer.offer(items.get(i))) {
                i++;
            }
        }
    }
}
