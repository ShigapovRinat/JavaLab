package ru.javalab.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {

    @GetMapping("/signIn")
    public ModelAndView getPage(){
        return new ModelAndView("sign_in");
    }
}
