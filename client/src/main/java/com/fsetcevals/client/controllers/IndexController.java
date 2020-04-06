package com.fsetcevals.client.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping("/")
    public ModelAndView index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken || auth==null) {
            throw new AccessDeniedException("Unauthenticated User");
        }
        User user = (User) auth.getPrincipal();
        String role = user.getAuthorities().iterator().next().toString();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("asurite", user.getUsername());
        switch(role) {
            case "ADMIN":
                modelAndView.setViewName("admin");
                return modelAndView;
            case "LEAD":
                modelAndView.setViewName("lead");
                return modelAndView;
            case "TUTOR":
                modelAndView.setViewName("evaluations");
                return modelAndView;
            default:
                throw new AccessDeniedException("User: " + ((User) auth.getPrincipal()).getUsername());
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }
}
