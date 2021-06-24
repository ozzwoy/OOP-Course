package cyb.oop;

public class Counter {
    private int value;

    public Counter(int value) {
        this.value = value;
    }

    public void increment() {
        value++;
    }

    public int get() {
        return value;
    }
}
