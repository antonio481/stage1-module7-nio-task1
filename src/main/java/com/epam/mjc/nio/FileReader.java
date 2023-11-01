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

    public Profile getDataFromFile(File file) {
        try(RandomAccessFile aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel()) {
            byte[] buffer = new byte[10];
            StringBuilder sb = new StringBuilder();
            while (inChannel.read(ByteBuffer.wrap(buffer)) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            /*fileInputStream.close();*/
            String content = sb.toString();
            System.out.println(content);
            Map<String, String> map = new HashMap<>();
            String[] arr= content.split("\n");
            for (int i = 0; i < arr.length-1; i++) {
                String[] arr1= arr[i].split(": ");
                map.put(arr1[0],arr1[1]);
            }
            Profile profile=new Profile();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(Objects.equals(entry.getKey(), "Name"))
                    profile.setName(entry.getValue());
                if(Objects.equals(entry.getKey(), "Age")) {
                    int age = Integer.parseInt(entry.getValue());
                    profile.setAge(age);
                }
                if(Objects.equals(entry.getKey(), "Email"))
                    profile.setEmail(entry.getValue());
                if(Objects.equals(entry.getKey(), "Phone")) {
                    Long phone=Long.parseLong(entry.getValue());
                    profile.setPhone(phone);
                }
            }
            return profile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
