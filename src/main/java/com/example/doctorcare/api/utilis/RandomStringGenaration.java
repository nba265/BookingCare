package com.example.doctorcare.api.utilis;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomStringGenaration {

    public static String randomStringWithLength(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        return new Random().ints(length, 0, chars.length())
                .mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());
    }
}
