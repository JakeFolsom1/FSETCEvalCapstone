package com.fsetcevals.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        int x = (int)(Math.random() * 3);
        if (x == 0) {
            return "admin";
        } else if (x == 1) {
            return "lead";
        } else {
            return "evaluations";
        }
    }
}
