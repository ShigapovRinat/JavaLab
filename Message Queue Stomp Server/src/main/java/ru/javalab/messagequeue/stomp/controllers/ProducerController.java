package ru.javalab.messagequeue.stomp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProducerController {

    @GetMapping("/produce")
    public ModelAndView getProducePage() {
        return new ModelAndView("producer");
    }
}