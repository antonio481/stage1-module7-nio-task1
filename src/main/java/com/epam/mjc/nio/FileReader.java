package com.epam.mjc.nio;

import java.io.File;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class FileReader {
    public Profile getDataFromFile(File file)  {
        try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = aFile.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder sb = new StringBuilder();
            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    sb.append((char) buffer.get());
                }
                buffer.clear(); // do something with the data and clear/compact it.
            }

            String content = sb.toString();
            Map<String, String> map = new HashMap<>();
            String[] arr = content.split("\n");
            for (String s : arr) {
                String[] arr1 = s.split(": ");
                map.put(arr1[0], arr1[1]);
            }
            Profile profile = new Profile();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (Objects.equals(entry.getKey(), "Name"))
                    profile.setName(entry.getValue());
                if (Objects.equals(entry.getKey(), "Age")) {
                    int age = Integer.parseInt(entry.getValue());
                    profile.setAge(age);
                }
                if (Objects.equals(entry.getKey(), "Email"))
                    profile.setEmail(entry.getValue());
                if (Objects.equals(entry.getKey(), "Phone")) {
                    long phone = Long.parseLong(entry.getValue());
                    profile.setPhone(phone);
                }
            }
            return profile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}