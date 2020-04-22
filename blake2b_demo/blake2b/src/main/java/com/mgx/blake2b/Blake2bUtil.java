package com.mgx.blake2b;

public class Blake2bUtil {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("blake2b-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native byte[] blake2b(byte[] bytes, int length);
}
