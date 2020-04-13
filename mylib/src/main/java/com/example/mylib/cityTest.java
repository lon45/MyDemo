package com.example.mylib;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class cityTest {

    public static void main(String[] args) {
        System.out.println("22222222222");

        DecimalFormat df = new DecimalFormat("#0.##");
        float   f1   =   205.55f;
        float   f2   =   205.0f;
        System.out.println(df.format(f1));
        System.out.println(df.format(f2));

//        init(key.getBytes());

        LocalDate date = LocalDate.now();
        System.out.println(date);

        System.out.println(Math.pow(5,2));
    }


    static String  key = "U1RHQFlaVF9MT0FOX0FQUExZI1NURw==";
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    private static void init(byte[] key) {
        try {
            // 初始化keyGen
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key);

            System.out.println("" + secureRandom.nextInt());

            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            System.out.println("secretKey" + secretKey.getEncoded());
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            System.out.println("secretKeySpec " + secretKeySpec.getEncoded());
            // 初始化cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } catch (Exception e){

        }
    }
}
