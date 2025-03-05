package cn.itcast.gobang;

public class JNI {

    static {
        System.loadLibrary("Hello");
    }

    public native String sayHello();
}
