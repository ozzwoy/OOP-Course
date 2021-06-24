package cyb.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.logging.LogManager;

public class SchedulerTest {
    static class MyTask implements Runnable {
        private final String name;
        public long lastTimeRun = 0;

        public MyTask(String name) {
            this.name = name;
        }

        long getLastTimeRun() {
            return lastTimeRun;
        }

        @Override
        public void run() {
            lastTimeRun = Calendar.getInstance().getTimeInMillis();
        }
    }

    @Test
    public void start() {
        Scheduler scheduler = new Scheduler();
        new Thread(() -> {
            try {
                scheduler.start();
            } catch (InterruptedException e) {
                LogManager.getLogManager().getLogger(SchedulerTest.class.getName()).severe(e.getMessage());
            }
        }).start();
        Assertions.assertTrue(scheduler.isRunning());
        scheduler.stop();
    }

    @Test
    public void add() throws InterruptedException {
        Scheduler scheduler = new Scheduler();
        new Thread(() -> {
            try {
                scheduler.start();
            } catch (InterruptedException e) {
                LogManager.getLogManager().getLogger(SchedulerTest.class.getName()).severe(e.getMessage());
            }
        }).start();
        MyTask task = new MyTask("task1");
        long currentTimeMs = Calendar.getInstance().getTimeInMillis();
        scheduler.add(task, 1000);
        Assertions.assertTrue(scheduler.contains(task));
        while (Calendar.getInstance().getTimeInMillis() < currentTimeMs + 1000) {
            Assertions.assertEquals(0, task.getLastTimeRun());
        }
        Thread.sleep(500);
        Assertions.assertNotEquals(0, task.getLastTimeRun());
        scheduler.stop();
    }

    @Test
    public void stop() {
        Scheduler scheduler = new Scheduler();
        new Thread(() -> {
            try {
                scheduler.start();
            } catch (InterruptedException e) {
                LogManager.getLogManager().getLogger(SchedulerTest.class.getName()).severe(e.getMessage());
            }
        }).start();
        scheduler.stop();
        Assertions.assertFalse(scheduler.isRunning());
    }
}
