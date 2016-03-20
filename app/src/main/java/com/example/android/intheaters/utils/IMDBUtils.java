package com.example.android.intheaters.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olgakuklina on 2016-03-19.
 */
public class IMDBUtils {
    private static final String TAG = IMDBUtils.class.getSimpleName();
    private static final Pattern REG_EX = Pattern.compile("<img[^>]*>");

    public static int calculateImdbImages(String uri) throws IOException {
        URL url = new URL(uri);
        String subString = "";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        Reader inputStreamReader = new InputStreamReader(connection.getInputStream());
        char[] buffer = new char[8192];
        int byteCounter = 0;
        int counter = 0;
        try {
            while ((byteCounter = inputStreamReader.read(buffer, 0, buffer.length)) != -1) {
                String bufferContent = subString + new String(buffer, 0, byteCounter);
                Matcher match = REG_EX.matcher(bufferContent);
                while (match.find()) {
                    counter++;
                }
                int lastIndex = bufferContent.lastIndexOf(">");
                if (lastIndex != -1) {
                    subString = bufferContent.substring(lastIndex + 1);
                } else {
                    subString = "";
                }
            }
        } finally {
            connection.disconnect();
            inputStreamReader.close();

        }
        return counter;
    }
}
