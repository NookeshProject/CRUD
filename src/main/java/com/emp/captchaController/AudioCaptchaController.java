package com.emp.captchaController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emp.captchaService.AudioCaptchaService;
import com.emp.captchaService.CaptchaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        	String captcha = (String) request.getSession().getAttribute("AUDIO_CAPTCHA");
            
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
    @GetMapping("/refresh-captcha")
    @ResponseBody
    public String refreshCaptcha(HttpSession session) {
        String captcha = captchaService.generateCaptcha();
        session.setAttribute("AUDIO_CAPTCHA", captcha);
        return captcha;
    }
}

