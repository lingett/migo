package multiThread

import org.apache.commons.lang.StringUtils

import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

class Consumer implements Runnable {
    private BlockingQueue<String> queue
    private volatile boolean isRunning = true
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000

    Consumer(BlockingQueue<String> queue) {
        this.queue = queue
    }

    @Override
    void run() {
        println("Consumer begin")

        String data
        Random r = new Random()

        try {
            while (isRunning) {
                println("Consumer...")
                data = queue.poll(2, TimeUnit.SECONDS)
                if (StringUtils.isNotEmpty(data)) {
                    println("Pop data from queue：" + data)
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP))
                } else {
                    isRunning = false
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        } finally {
            println("Consumer end")
        }
    }
}
