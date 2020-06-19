package ru.undframe.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Value("${RECAPTCHA_SITE_KEY}")
    private String captchaSiteKey;
    @Value("${RECAPTCHA_SERVER_KEY}")
    private String captchaServerKey;

    @RequestMapping(value = "/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "captcha", required = false) String captcha,
            @RequestParam(value = "logout", required = false) String logout
    ) {
        boolean captchaError = captcha != null;
        ModelAndView model = new ModelAndView();
        model.addObject("RECAPTCHA_SITE_KEY", captchaSiteKey);
        if (captchaError) {
            model.addObject("captchaError", true);
        }
        if (error != null) {
            model.addObject("error", "Invalid username or password.");
        }
        if (logout != null) {
            model.addObject("msg", "Successfully logged out.");
        }
        return model;
    }

}
