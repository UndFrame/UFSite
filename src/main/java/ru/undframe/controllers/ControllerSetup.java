package ru.undframe.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerSetup {

    @ModelAttribute
    public void initModel(HttpServletRequest request, Model model) {
        Object csrf = request.getAttribute("_csrf");
        System.out.println("CSRT: "+csrf);
        model.addAttribute("_csrf", csrf);
    }

}
