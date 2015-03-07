package concurrent;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Data
public class CustomThreadFactory implements ThreadFactory {
    private String name;
    private int counter = 1;
    private List<String> stats = Lists.newArrayList();

    private static final String THREAD_NAME = "%s-Thread_%s";
    private static final String CREATE_LOG = "Created thread %s with name [%s] on %s\n";

    public CustomThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread result = new Thread(runnable, String.format(THREAD_NAME, name, counter));
        counter++;
        stats.add(String.format(CREATE_LOG, result.getId(), result.getName(), new Date()));
        return result;
    }

    public String getStats() {
        StringBuffer result = new StringBuffer();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()) {
            result.append(it.next());
        }
        return result.toString();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Present.getInstance().v);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Present {
        public int v = (new Random()).nextInt(100);
        private static ThreadLocal<Present> threadLocal = new ThreadLocal<Present>();

        public static Present getInstance() {
            Present instance = threadLocal.get();
            if (instance == null) {
                instance = new Present();
                threadLocal.set(instance);
            }
            return instance;
        }
    }

    public static void main(String... args) {
        CustomThreadFactory factory = new CustomThreadFactory("CustomThreadFactory");
        Task task = new Task();
        Thread thread;
        System.out.println("Starting the threads\n\n");
        for (int i = 1; i <= 10; i++) {
            thread = factory.newThread(task);
            thread.start();
        }
        System.out.println("Stats:\n\n" + factory.getStats());
    }
}
