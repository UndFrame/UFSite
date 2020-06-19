package ru.undframe.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.undframe.authentication.CustomWebAuthenticationDetails;
import ru.undframe.captcha.CaptchaError;
import ru.undframe.captcha.ReCaptchaResponse;
import ru.undframe.mode.Role;
import ru.undframe.mode.User;
import ru.undframe.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Value("${RECAPTCHA_SITE_KEY}")
    private String captchaSiteKey;
    @Value("${RECAPTCHA_SERVER_KEY}")
    private String  captchaServerKey;
    private String captchaURL = "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomWebAuthenticationDetails webAuthenticationDetails = (CustomWebAuthenticationDetails)authentication.getDetails();
        String captchaParam = "?secret="+captchaServerKey+"&response="+ webAuthenticationDetails.getCaptchaToken();
        ReCaptchaResponse captchaResponse = restTemplate.exchange(captchaURL + captchaParam, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();
        if(captchaResponse==null || !captchaResponse.isSuccess()){
            throw new CaptchaError("captcha error");
        }
        User user = userService.findByUsername(authentication.getName());
        if(user == null){
            user = userService.findByEmail(authentication.getName());
        }
        if(user==null){
            throw new UsernameNotFoundException("user not found");
        }
        if(!user.isEnabled()){
            throw new LockedException("user not activate");
        }
        if(user.isBan()){
            throw new LockedException("user not activate");
        }
        if(!passwordEncoder.matches(authentication.getCredentials().toString(),user.getPassword())){
            throw new BadCredentialsException("password not equals");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new UsernamePasswordAuthenticationToken(user,null,grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
