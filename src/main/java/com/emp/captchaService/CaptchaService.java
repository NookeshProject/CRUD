package com.emp.captchaService;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

    public String generateCaptcha() {
        int num = 10000 + new SecureRandom().nextInt(90000);
        return String.valueOf(num);
    }
}
