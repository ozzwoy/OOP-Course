package cyb.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class NonBlockingQueueTest {

    @Test
    public void testOnSingleThread() throws IllegalAccessException, NoSuchFieldException {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();
        final int size = 5;
        int[] expected = IntStream.range(0, size).toArray();
        int[] result = new int[size];
        int times = 2;

        for (int k = 0; k < times; k++) {
            for (int i = 0; i < size; i++) {
                queue.enqueue(i);
            }
            for (int i = 0; i < size; i++) {
                result[i] = queue.dequeue();
            }

            Assertions.assertTrue(Arrays.equals(expected, result));
            Assertions.assertNull(queue.dequeue());
        }
    }

    @Test
    public void testOnIntegerArrays() throws IllegalAccessException, NoSuchFieldException, ExecutionException,
            InterruptedException {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        final int chunkSize = 10000;
        final int numOfThreads = 5;

        List<QueueTestCallable> callables = new ArrayList<>(numOfThreads);
        List<Future<List<Integer>>> futures = new ArrayList<>(numOfThreads);
        List<Integer> result = new ArrayList<>(chunkSize * numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            callables.add(new QueueTestCallable(queue, IntStream.range(i * chunkSize, (i + 1) * chunkSize).toArray()));
            Thread.sleep(1);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            futures.add(executorService.submit(callables.get(i)));
        }
        executorService.shutdown();

        for (Future<List<Integer>> future : futures) {
            result.addAll(future.get());
            result.sort(Integer::compareTo);
        }

        List<Integer> expected = new ArrayList<>(chunkSize * numOfThreads);
        for (int i = 0; i < chunkSize * numOfThreads; i++) {
            expected.add(i);
        }

        Assertions.assertEquals(expected, result);
        Assertions.assertNull(queue.dequeue());
    }

    @Test
    public void testOnIntegerArraysWithInterruption() throws InterruptedException, IllegalAccessException,
            NoSuchFieldException {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        final int chunkSize = 10000;
        final int numOfThreads = 3;

        List<QueueTestCallable> callables = new ArrayList<>(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            callables.add(new QueueTestCallable(queue, IntStream.range(i * chunkSize, (i + 1) * chunkSize).toArray()));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        ExecutorService subExecutorService = Executors.newSingleThreadExecutor();

        Future<List<Integer>> future = subExecutorService.submit(callables.get(numOfThreads - 1));

        executorService.shutdown();

        Thread.sleep(3);
        Assertions.assertFalse(future.isDone());
        future.cancel(true);
        Thread.sleep(50);
        Assertions.assertThrows(CancellationException.class, future::get);
    }
}
