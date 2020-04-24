package ru.undframe.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.undframe.mode.User;
import ru.undframe.services.SecurityService;

import javax.validation.Valid;

@Controller
public class WebController implements WebMvcConfigurer {




    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @GetMapping("/index")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        model.addAttribute("user", authentication.getPrincipal());
        return "form";
    }


    @GetMapping("/")
    public String main(Model model) {
        return "index";
    }


    @GetMapping("/form")
    public String showForm(User personForm) {
        return "form";
    }

    @PostMapping("/form")
    public String checkPersonInfo(@ModelAttribute @Valid User personForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "results";
    }
}
