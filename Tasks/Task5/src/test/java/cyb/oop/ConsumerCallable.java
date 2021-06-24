package cyb.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ConsumerCallable<E> implements Callable<List<E>> {
    private final DisruptorBuffer<E> buffer;
    private final int iterations;

    public ConsumerCallable(DisruptorBuffer<E> buffer, int iterations) {
        this.buffer = buffer;
        this.iterations = iterations;
    }

    @Override
    public List<E> call() {
        List<E> items = new ArrayList<>();

        for (int i = 0; i < iterations;) {
            E item = buffer.poll();

            if (item != null) {
                items.add(item);
                i++;
            }
        }

        return items;
    }
}
