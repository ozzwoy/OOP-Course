package cyb.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomReadWriteLockTest {

    @Test
    public void testOnSharedVariable() throws ExecutionException, InterruptedException {
        CustomReadWriteLock lock = new CustomReadWriteLock();
        Counter counter = new Counter(0);
        int readersNum = 5;
        int writersNum = 10;
        List<Future<Integer>> futures = new ArrayList<>(readersNum);
        List<Integer> result = new ArrayList<>(readersNum);

        ExecutorService executorService = Executors.newFixedThreadPool(readersNum + writersNum);
        for (int i = 0; i < writersNum; i++) {
            executorService.submit(new WriterCallable(counter, lock));
        }
        for (int i = 0; i < readersNum; i++) {
            futures.add(executorService.submit(new ReaderCallable(counter, lock)));
        }
        for (Future<Integer> future : futures) {
            result.add(future.get());
        }

        Assertions.assertEquals(writersNum, counter.get());
        Assertions.assertEquals(Stream.generate(() -> writersNum).limit(readersNum).collect(Collectors.toList()),
                result);
    }
}
