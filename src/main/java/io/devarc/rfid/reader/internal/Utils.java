package io.devarc.rfid.reader.internal;

public class Utils {
    public static String getHexRepresentation(byte[] input) {
        StringBuilder result = new StringBuilder();
        for(byte b : input) {
            result.append(String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0'));
        }
        return result.toString();
    }
}
