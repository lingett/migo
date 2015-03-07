package concurrent;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceExample {
    static void testWeekReference() {
        String str = new String("hello");
        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        WeakReference<String> weak = new WeakReference<String>(str, queue);
        str = null;

        int i = 0;
        while (weak.get() != null) {
            System.out.println(String.format("Get str from WeakReference: %s, count: %d", weak.get(), ++i));
            if (i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("str was cleared by JVM!");
    }

    static void testSoftReference() {
        String str = new String("world");
        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        SoftReference<String> soft = new SoftReference<String>(str, queue);
        str = null;

        int i = 0;
        while (soft.get() != null) {
            System.out.println(String.format("Get str from SoftReference: %s, count: %d", soft.get(), ++i));
            if (i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("str was cleared by JVM!");
    }

    public static void main(String... args) {
//        testWeekReference();
//        testSoftReference();
    }
}
