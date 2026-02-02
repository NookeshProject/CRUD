package com.emp.captchaController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CaptchaValidationController {

    @RequestMapping("/validate-captcha")
    public String validate(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String userInput = request.getParameter("captcha");

        String actualCaptcha =
                (String) session.getAttribute("AUDIO_CAPTCHA");

        // One-time use
        session.removeAttribute("AUDIO_CAPTCHA");

        if (actualCaptcha != null && actualCaptcha.equals(userInput)) {
            return "success";
        }
        return "captcha-error";
    }
}

