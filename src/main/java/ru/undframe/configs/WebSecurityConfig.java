package ru.undframe.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.undframe.authentication.CustomAuthenticationFailureHandler;
import ru.undframe.authentication.CustomWebAuthenticationDetailsSource;
import ru.undframe.captcha.CaptchaError;
import ru.undframe.errors.UserIsBanned;
import ru.undframe.mode.Role;
import ru.undframe.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProviderImpl authProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login", "/reg", "/activate", "/tokenInfo").anonymous()
                .antMatchers("/account").authenticated()
                .antMatchers("/form").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .authenticationDetailsSource(customAuthenticationFailureHandler())
                .failureHandler(new CustomAuthenticationFailureHandler((request, response, authException) -> {
                    String redirect ="/login?error";
                    if (authException instanceof CaptchaError) {
                        redirect = "/login?captcha";
                    }
                    if (authException instanceof UserIsBanned) {
                        redirect = "/login?ban";
                    }
                    response.sendRedirect(redirect);
                }))
                .usernameParameter("userId")
                .loginPage("/login")
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        //auth.userDetailsService(userDetailsService);
    }


    @Bean
    public CustomWebAuthenticationDetailsSource customAuthenticationFailureHandler() {
        return new CustomWebAuthenticationDetailsSource();
    }
}
