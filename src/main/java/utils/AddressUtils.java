package utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class AddressUtils {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long addressOf(Object o) {
        Object[] objectArray = new Object[]{o};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(objectArray, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(objectArray, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }

        return (objectAddress);
    }

    public static void main(String[] args) throws Exception {
//        Object mine = "Hello world".toCharArray();
//        long address = addressOf(mine);
//        System.out.println("Address: " + address);
//
        StringBuffer s = new StringBuffer("Hello");
        System.out.println(AddressUtils.addressOf(s));
        s.append(" world");
        System.out.println(AddressUtils.addressOf(s));
//
//        String str = "hello";
//        System.out.println(Address.addressOf(str));
//        str = "world";
//        System.out.println(Address.addressOf(str));

        String string = "hello world";
        System.out.println(AddressUtils.addressOf(string));
        String subString = string.substring(0, 5);
        System.out.println(AddressUtils.addressOf(subString));
    }
}