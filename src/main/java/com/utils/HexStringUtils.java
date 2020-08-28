package com.utils;




public class HexStringUtils {

    public static byte[] stringToHexString(String string) {
        if (string == null || string.equals("")) {
            return null;
        }
        byte[] bytes = string.getBytes();
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(Integer.toHexString(b).toUpperCase()).append(" ");
//          result.append(b);
        }
        return bytes;
    }


    public static String hexStringToString(String hexString) {
        if (hexString== null || hexString.equals("") || hexString.trim().equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String[] strings = hexString.split(" ");
        for (String string : strings) {
            sb.append((char) Integer.parseInt(string, 16));
//            sb.append(string+" ");
        }
        return sb.toString();
    }

}
