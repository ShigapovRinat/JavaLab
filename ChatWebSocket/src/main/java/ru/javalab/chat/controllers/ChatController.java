package ru.javalab.chat.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javalab.chat.dto.CreateChatDto;
import ru.javalab.chat.dto.InviteChatDto;
import ru.javalab.chat.dto.ResponseInviteChatDto;
import ru.javalab.chat.services.ChatService;
import ru.javalab.chat.services.TokenService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Profile("mvc")
@Controller
public class ChatController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/chat/{chat-id}")
    public String getChatPage(@CookieValue(value = "auth", required = false) Cookie auth,
                              @CookieValue(value = "chatPassword", required = false) Cookie cookiePassword,
                              @PathVariable(value = "chat-id") String stringChatId,
                              HttpServletResponse response,
                              Model model) {
        try {
            if (auth != null || cookiePassword != null) {
                Long chatId = Long.parseLong(stringChatId);
                String chatPassword = cookiePassword.getValue();
                cookiePassword.setMaxAge(0);
                response.addCookie(cookiePassword);
                ResponseInviteChatDto responseDto = chatService.inviteChat(InviteChatDto.builder()
                        .id(chatId)
                        .password(chatPassword)
                        .build());

                String token = auth.getValue();
                Claims claims = tokenService.encodeToken(token);
                model.addAttribute("pageId", claims.get("login", String.class));
                model.addAttribute("messages", responseDto.getMessages());
                model.addAttribute("chatName", responseDto.getName());
                model.addAttribute("chatId", chatId);
                model.addAttribute("chatPassword", chatPassword);
                model.addAttribute("userId", Long.parseLong(claims.get("id", String.class)));
                return "chat";
            } else return "redirect:/main";
        } catch (IllegalArgumentException e){
            return "redirect:/main";
        }
    }

    @PostMapping("/createChat")
    public String createChat(CreateChatDto chatDto, @CookieValue(value = "auth", required = false) Cookie auth) {
        String token = auth.getValue();
        Claims claims = tokenService.encodeToken(token);
        chatDto.setUserId(Long.parseLong(claims.get("id", String.class)));
        chatService.createChat(chatDto);
        return "redirect:/main";
    }

    @PostMapping("/inviteChat")
    public String inviteChat(InviteChatDto inviteChatDto, Model model, HttpServletResponse response) {
        try {
            response.addCookie(new Cookie("chatPassword", inviteChatDto.getPassword()));
            return "redirect:/chat/" + inviteChatDto.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("exception", e);
            return "redirect:/main";
        }
    }
}