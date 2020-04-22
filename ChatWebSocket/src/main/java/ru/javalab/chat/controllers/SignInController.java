package ru.javalab.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javalab.chat.dto.SignInDto;
import ru.javalab.chat.dto.TokenDto;
import ru.javalab.chat.services.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {

    @Autowired
    private SignInService signInService;

    @GetMapping("/signIn")
    public String openPage(@CookieValue(value = "auth", required = false) Cookie auth) {
        if (auth == null) {
            return "sign_in";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/signIn")
    public String signIn(SignInDto signInDto, HttpServletResponse response, Model model) {
        try {
            TokenDto dto = signInService.signIn(signInDto);
            Cookie cookie = new Cookie("auth", dto.getToken());
            response.addCookie(cookie);
            return "redirect:/main";
        } catch (IllegalArgumentException e) {
            model.addAttribute("exception", e);
            return "sign_in";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie: request.getCookies() ) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/signIn";
    }
}
