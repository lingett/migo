package guava;

import com.google.common.util.concurrent.*;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Chapter5_1_ListenableFuture {
    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("call execute...");
            TimeUnit.SECONDS.sleep(1);
            return new Random().nextInt(100);
        }
    }

    static class CallBack implements FutureCallback<Integer> {
        @Override
        public void onSuccess(Integer result) {
            System.out.println("Success, result:" + result);
        }

        @Override
        public void onFailure(Throwable t) {
            System.out.println("Failure");
        }
    }

    public static void main(String... args) {
        int poolSize = 10;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executor);

        for (int i = 0; i < poolSize; i++) {
            ListenableFuture<Integer> explosion = service.submit(new Task());
            Futures.addCallback(explosion, new CallBack());
        }

        System.out.println("end");
    }
}
