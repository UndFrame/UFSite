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
import ru.undframe.mode.User;
import ru.undframe.services.SecurityService;
import ru.undframe.services.UserService;
import ru.undframe.validator.UserValidator;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    /*@Autowired
    private SecurityService securityService;*/
    @Autowired
    private UserValidator userValidator;

    @GetMapping("/reg")
    public String  registration(Model model){
        model.addAttribute("userForm",new User());
        return "reg";
    }

    @PostMapping("/reg")
    public String  registration(@ModelAttribute("userForm")User userForm, BindingResult bindingResult, Model model){
        userValidator.validate(userForm,bindingResult);
        if(bindingResult.hasErrors()){
            return "reg";
        }

        userService.save(userForm);
        //securityService.autoLogin(userForm.getUsername(),userForm.getConfirmPassword());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("_csrf","39d1c8f9-23ea-4869-a808-6da6ad50295d");
        return "login";
    }

}
