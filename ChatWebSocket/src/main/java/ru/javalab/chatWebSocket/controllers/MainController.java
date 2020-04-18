package ru.javalab.chatWebSocket.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javalab.chatWebSocket.services.ChatService;
import ru.javalab.chatWebSocket.services.TokenService;

import javax.servlet.http.Cookie;

@Controller
public class MainController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/main")
    public String getChatsPage(@CookieValue(value = "auth", required = false) Cookie auth, Model model) {
        if (auth != null) {
            String token = auth.getValue();
            Claims claims = tokenService.encodeToken(token);
            model.addAttribute("chatList", chatService.getAllChats());
            model.addAttribute("selfChatList",
                    chatService.getChatsFotUser(Long.parseLong(claims.get("id", String.class))));
            return "main";
        } else return "redirect:/signIn";
    }

}
