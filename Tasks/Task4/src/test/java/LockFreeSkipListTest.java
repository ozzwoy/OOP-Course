import cyb.oop.LockFreeSkipList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LockFreeSkipListTest {
    private LockFreeSkipList<Integer> list;

    @BeforeEach
    void initializeList() {
        list = new LockFreeSkipList<>();
    }

    private Thread insertFromDifferentThread(int element) {
        Thread thread = new Thread(() -> list.add(element));
        thread.start();
        return thread;
    }

    private Thread removeInDifferentThread(int element) {
        Thread thread = new Thread(() -> list.remove(element));
        thread.start();
        return thread;
    }

    @Test
    void shouldInsertElementsFrom3Threads() throws InterruptedException {
        //Given
        //When
        List<Thread> threads = new ArrayList<>();
        threads.add(insertFromDifferentThread(3));
        threads.add(insertFromDifferentThread(5));
        threads.add(insertFromDifferentThread(7));
        for (Thread thread : threads) {
            thread.join();
        }
        //Then
        for (int i = 3; i < 8; i += 2) {
            Assertions.assertTrue(list.contains(i));
        }
    }

    @Test
    void shouldRemoveElementsFrom3Threads() throws InterruptedException {
        //Given
        List<Thread> threads = new ArrayList<>();
        threads.add(insertFromDifferentThread(3));
        threads.add(insertFromDifferentThread(5));
        threads.add(insertFromDifferentThread(7));
        for (Thread thread : threads) {
            thread.join();
        }
        //When
        threads.clear();
        threads.add(removeInDifferentThread(3));
        threads.add(removeInDifferentThread(4));
        for (Thread thread : threads) {
            thread.join();
        }
        //Then
        Assertions.assertTrue(list.contains(5));
        Assertions.assertTrue(list.contains(7));
        Assertions.assertFalse(list.contains(3));
    }

    @Test
    void shouldCorrectlyDeleteAndRemoveElementsInSameTime() throws InterruptedException {
        //Given
        List<Thread> threads = new ArrayList<>();
        threads.add(insertFromDifferentThread(3));
        for (Thread thread : threads) {
            thread.join();
        }
        //When
        threads.clear();
        threads.add(removeInDifferentThread(3));
        threads.add(insertFromDifferentThread(5));
        threads.add(insertFromDifferentThread(7));
        threads.add(removeInDifferentThread(4));
        for (Thread thread : threads) {
            thread.join();
        }
        //Then
        Assertions.assertTrue(list.contains(5));
        Assertions.assertTrue(list.contains(7));
        Assertions.assertFalse(list.contains(3));
    }
}
