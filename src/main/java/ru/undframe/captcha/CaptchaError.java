package ru.undframe.captcha;

import org.springframework.security.core.AuthenticationException;

public class CaptchaError extends AuthenticationException {
    public CaptchaError(String msg, Throwable t) {
        super(msg, t);
    }

    public CaptchaError(String msg) {
        super(msg);
    }
}
