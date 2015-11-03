package com.gmr.boot;


import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderHelper {

    @Test
    public void encodePassword() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("59d14f01-38da-401c-8014-b6c0356271c8")); // boot_webapp password
        System.out.println(encoder.encode("admin")); // user admin password
    }

}
