package com.ChatApp.utils;

import java.util.Random;

public class OtpUtil {
    public static String generateOtp() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for(int i=1;i<=6;i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
