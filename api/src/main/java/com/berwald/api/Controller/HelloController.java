package com.berwald.api.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String helloThymeleaf(Model model){
        model.addAttribute("name","Thymeleaf");
        return "helloPage";
    };


}
