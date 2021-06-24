package cyb.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DisruptorTest {

    @Test
    public void testOnProducerConsumer() throws ExecutionException, InterruptedException {
        DisruptorBuffer<Integer> buffer = new DisruptorBuffer<>(5);
        List<Integer> items = IntStream.rangeClosed(0, 7).boxed().collect(Collectors.toList());
        ExecutorService agent = Executors.newFixedThreadPool(2);

        Future<List<Integer>> future = agent.submit(new ConsumerCallable<>(buffer, items.size()));
        agent.execute(new ProducerRunnable<>(items, buffer));

        Assertions.assertEquals(items, future.get());
    }
}
