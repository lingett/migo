package multiThread

import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class Producer implements Runnable {
    private BlockingQueue queue
    private volatile boolean isRunning = true
    private static AtomicInteger count = new AtomicInteger()
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000

    public Producer(BlockingQueue queue) {
        this.queue = queue
    }

    public void stop() {
        isRunning = false
    }

    @Override
    void run() {
        println("Producer begin")

        String data
        Random r = new Random()

        try {
            while (isRunning) {
                println("Producing...")
                Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP))
                data = "Data:" + count.incrementAndGet()
                println("Push data into queue：" + data)
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    println("Push data into queue error：" + data)
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        } finally {
            println("Producer end")
        }
    }
}
