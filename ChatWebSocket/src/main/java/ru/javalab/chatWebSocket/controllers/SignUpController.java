package ru.javalab.chatWebSocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.javalab.chatWebSocket.dto.SignUpDto;
import ru.javalab.chatWebSocket.services.SignUpService;

import javax.servlet.http.Cookie;

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private SignUpService service;

    @GetMapping
    public String openPage(@CookieValue(value = "auth", required = false) Cookie auth) {
        if(auth == null) {
            return "sign_up";
        } else {
            return "redirect:/chats";
        }
    }

    @PostMapping
    public ModelAndView registration(SignUpDto dto) {
        try {
            if (dto.getLogin().equals("") || dto.getPassword().equals(""))
                throw new IllegalArgumentException("Заполните все параметры");
            service.signUp(dto);
            return new ModelAndView("redirect:/signIn");
        }catch (IllegalArgumentException e){
            return new ModelAndView("sign_up", "exception", e);
        }
    }

}
