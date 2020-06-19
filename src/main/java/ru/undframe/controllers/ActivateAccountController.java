package ru.undframe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.undframe.services.UserService;

@Controller
public class ActivateAccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/activate")
    public String confirmationEmail(@RequestParam(value = "token")String token, Model model){
        System.out.println("TEST TEST TEST");
        if(token==null || token.isEmpty()){
            return "redirect:/reg";
        }
        boolean isActivated = userService.activateUser(token);
            model.addAttribute("activateToken",isActivated);

        return "login";
    }
}