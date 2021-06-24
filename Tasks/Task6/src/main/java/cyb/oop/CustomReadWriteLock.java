package cyb.oop;

public class CustomReadWriteLock {
    private int readers = 0;
    private int waitingToWrite = 0;
    private boolean isAnyoneWriting = false;

    public synchronized void lockRead() throws InterruptedException {
        while (isAnyoneWriting || waitingToWrite > 0) {
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        waitingToWrite++;
        while (readers > 0 || isAnyoneWriting) {
            wait();
        }
        waitingToWrite--;
        isAnyoneWriting = true;
    }

    public synchronized void unlockWrite() {
        isAnyoneWriting = false;
        notifyAll();
    }
}
