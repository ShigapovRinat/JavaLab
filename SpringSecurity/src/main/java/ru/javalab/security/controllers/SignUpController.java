package ru.javalab.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javalab.security.dto.SignUpDto;
import ru.javalab.security.services.SignUpService;

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private SignUpService service;

    @GetMapping
    public String openPage(Authentication authentication) {
        if(authentication == null) {
            return "sign_up";
        } else {
            return "redirect:/profile";
        }
    }

    @PostMapping
    public ModelAndView registration(SignUpDto dto) {
        try {
            service.signUp(dto);
            return new ModelAndView("sign_in");
        }catch (IllegalArgumentException e){
            return new ModelAndView("sign_up").addObject("exception", e);
        }
    }

}
