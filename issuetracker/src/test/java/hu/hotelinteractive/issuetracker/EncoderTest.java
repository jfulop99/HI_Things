package hu.hotelinteractive.issuetracker;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncoderTest {

    @Test
    void testEncode() {
        System.out.println(new BCryptPasswordEncoder().encode("user3"));
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("user3"));

    }
}
