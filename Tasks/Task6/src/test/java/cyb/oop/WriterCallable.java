package cyb.oop;

import java.util.concurrent.Callable;

public class WriterCallable implements Callable<Void> {
    Counter counter;
    CustomReadWriteLock lock;

    public WriterCallable(Counter counter, CustomReadWriteLock lock) {
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public Void call() throws InterruptedException {
        lock.lockWrite();
        counter.increment();
        lock.unlockWrite();

        return null;
    }
}
