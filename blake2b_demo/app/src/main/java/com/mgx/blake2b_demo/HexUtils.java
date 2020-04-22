package com.mgx.blake2b_demo;

import java.math.BigInteger;

/**
 * Utilities for going to and from ASCII-HEX representation.
 */
public class HexUtils {

    public static final String HEX_PREFIX = "0x";

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes the array of bytes to encode
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes) {
        return toHex(bytes, null);
    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toUpperCase().toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes     the array of bytes to encode
     * @param separator the separator to use between two bytes, can be null
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, String separator) {
        return toHex(bytes, 0, bytes.length, separator);
    }

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes  the array of bytes to encode
     * @param offset the start offset in the array of bytes
     * @param length the number of bytes to encode
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, int offset, int length) {
        return toHex(bytes, offset, length, null);
    }

    /**
     * Encodes a single byte to hex symbols.
     *
     * @param b the byte to encode
     * @return the resulting hex string
     */
    public static String toHex(byte b) {
        StringBuilder sb = new StringBuilder();
        appendByteAsHex(sb, b);
        return sb.toString();
    }


    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes     the array of bytes to encode
     * @param offset    the start offset in the array of bytes
     * @param length    the number of bytes to encode
     * @param separator the separator to use between two bytes, can be null
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, int offset, int length, String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int unsignedByte = bytes[i + offset] & 0xff;

            if (unsignedByte < 16) {
                result.append("0");
            }

            result.append(Integer.toHexString(unsignedByte));
            if (separator != null && i + 1 < length) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    /**
     * Get the byte representation of an ASCII-HEX string.
     *
     * @param hexString The string to convert to bytes
     * @return The byte representation of the ASCII-HEX string.
     */
    public static byte[] toBytes(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            throw new RuntimeException("Input string must contain an even number of characters");
        }
        char[] hex = hexString.toCharArray();
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            if (high < 0 || low < 0) {
                throw new RuntimeException("Invalid hex digit " + hex[i * 2] + hex[i * 2 + 1]);
            }
            int value = (high << 4) | low;
            if (value > 127)
                value -= 256;
            raw[i] = (byte) value;
        }
        return raw;
    }

    public static byte[] toBytesReversed(String hexString) {
        byte[] rawBytes = toBytes(hexString);

        for (int i = 0; i < rawBytes.length / 2; i++) {
            byte temp = rawBytes[rawBytes.length - i - 1];
            rawBytes[rawBytes.length - i - 1] = rawBytes[i];
            rawBytes[i] = temp;
        }

        return rawBytes;
    }

    public static void appendByteAsHex(StringBuilder sb, byte b) {
        int unsignedByte = b & 0xFF;
        if (unsignedByte < 16) {
            sb.append("0");
        }
        sb.append(Integer.toHexString(unsignedByte));
    }

    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            // 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static String toHexStringNoPrefix(BigInteger value) {
        return value.toString(16);
    }

    public static String cleanHexPrefix(String input) {
        if (containsHexPrefix(input)) {
            return input.substring(2);
        } else {
            return input;
        }
    }

    private static boolean containsHexPrefix(String input) {
        return input !=null && input.length() > 1 && input.charAt(0) == '0' && (input.charAt(1) == 'x' );
    }

    public static BigInteger toBigInt(String hexValue) {
        String cleanValue = cleanHexPrefix(hexValue);
        if(cleanValue==null || cleanValue.isEmpty())cleanValue = "0";
        return new BigInteger(cleanValue, 16);
    }

    public static String toHexStringWithPrefix(BigInteger value) {
        return HEX_PREFIX + value.toString(16);
    }

    public static String toHexString(byte[] input) {
        return toHexString(input, 0, input.length, true);
    }

    public static String toHexString(byte[] input, int offset, int length, boolean withPrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        if (withPrefix) {
            stringBuilder.append(HexUtils.HEX_PREFIX);
        }
        for (int i = offset; i < offset + length; i++) {
            stringBuilder.append(String.format("%02x", input[i] & 0xFF));
        }

        return stringBuilder.toString();
    }
}

