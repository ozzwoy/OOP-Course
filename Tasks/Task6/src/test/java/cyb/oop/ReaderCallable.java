package cyb.oop;

import java.util.concurrent.Callable;

public class ReaderCallable implements Callable<Integer> {
    Counter counter;
    CustomReadWriteLock lock;

    public ReaderCallable(Counter counter, CustomReadWriteLock lock) {
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public Integer call() throws InterruptedException {
        lock.lockRead();
        int value = counter.get();
        lock.unlockRead();

        return value;
    }
}
