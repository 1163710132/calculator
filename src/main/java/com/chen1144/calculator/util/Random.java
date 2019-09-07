package com.chen1144.calculator.util;

public abstract class Random {
    public static String randomString(){
        java.util.Random random = new java.util.Random();
        StringBuilder builder = new StringBuilder();
        while (true){
            char ch = (char)random.nextInt();
            if(Character.isAlphabetic(ch)){
                builder.append(ch);
                if(random.nextInt(builder.length()) != 0){
                    break;
                }
            }
        }
        return builder.toString();
    }

    public static int randomInt(){
        java.util.Random random = new java.util.Random();
        return random.nextInt();
    }

    public static byte[] randomBytes(int length){
        byte[] bytes = new byte[length];
        java.util.Random random = new java.util.Random();
        random.nextBytes(bytes);
        return bytes;
    }

    public static int[] randomInts(int length){
        java.util.Random random = new java.util.Random();
        int[] ints = new int[length];
        for(int i = 0;i < length;i++){
            ints[i] = random.nextInt();
        }
        return ints;
    }
}
