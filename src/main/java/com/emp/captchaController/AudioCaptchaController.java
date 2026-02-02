package com.emp.captchaController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emp.captchaService.AudioCaptchaService;
import com.emp.captchaService.CaptchaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AudioCaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private AudioCaptchaService audioCaptchaService;

    @RequestMapping("/audio-captcha")
    public void audioCaptcha(HttpServletRequest request,
                             HttpServletResponse response) {

        try {
            String captcha = captchaService.generateCaptcha();
            
            response.setContentType("audio/wav");
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");

            request.getSession().setAttribute("AUDIO_CAPTCHA", captcha);

            audioCaptchaService.playCaptcha(
                    captcha,
                    response.getOutputStream()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

