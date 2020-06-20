package ru.undframe.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.undframe.mode.Role;
import ru.undframe.mode.User;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class WebController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "redirect:/";
    }

    @GetMapping("/")
    public String main(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user =authentication.getPrincipal() instanceof User ?  (User)authentication.getPrincipal() : null;
        model.addAttribute("auth", user !=null);
        model.addAttribute("user", user);
        return "index";
    }


    @GetMapping("/form")
    public String showForm() {
        return "form";
    }

    @PostMapping("/form")
    public String checkPersonInfo(@ModelAttribute @Valid User personForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "results";
    }

    @GetMapping("/account")
    public String account(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user =authentication.getPrincipal() instanceof User ?  (User)authentication.getPrincipal() : null;
        model.addAttribute("auth", user !=null);
        model.addAttribute("user", user);
        model.addAttribute("roles", user != null ? user.getRoles().toArray(new Role[]{}) : Collections.emptyList());
        return "user";
    }


}
