package concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class FutureExample implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return new Random().nextInt(100);
    }

    public static void main(String[] args) throws InterruptedException {
        int poolSize = 10;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> callableList = new LinkedList<Callable<Integer>>();

        for (int i = 0; i < poolSize; i++) {
            callableList.add(new FutureExample());
        }

        List<Future<Integer>> futures = executor.invokeAll(callableList);
        for (Future<Integer> future : futures) {
            try {
                System.out.println(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }
}
