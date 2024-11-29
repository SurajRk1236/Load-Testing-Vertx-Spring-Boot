package com.vertx.custom.load.testing.utils;

import java.util.HashMap;
import java.util.Map;

public class CurlParser {

    public static Map<String, String> parseCurl(String curl) {
        Map<String, String> result = new HashMap<>();
        String[] parts = curl.split(" ");
        boolean isHeader = false;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("-H")) {
                isHeader = true;
                continue;
            }
            if (isHeader) {
                String[] header = parts[i].split(":");
                result.put(header[0], header[1]);
                isHeader = false;
            } else if (parts[i].startsWith("http")) {
                result.put("url", parts[i]);
            } else if (parts[i].equals("-X")) {
                result.put("method", parts[++i]);
            }
        }
        return result;
    }


    public static Map<String, String> parseHeaders(String headers) {
        Map<String, String> headerMap = new HashMap<>();
        String[] headerEntries = headers.split(";");
        for (String header : headerEntries) {
            String[] entry = header.split(":");
            headerMap.put(entry[0].trim(), entry[1].trim());
        }
        return headerMap;
    }



}
