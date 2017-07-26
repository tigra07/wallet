package com.zor07.controllers;

import com.zor07.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({"/", "/home"})
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        String greeting = String.format("Welcome to your wallet, master %s", name);
        model.addAttribute("greeting", greeting);

        return "index";
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model){
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "auth/login";
    }

    @RequestMapping("/register")
    public String register(@RequestParam(value = "userexists", required = false) String userexists,
                           @RequestParam(value = "success", required = false) String success,
                           @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password,
                           Model model){
        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            if (userService.userExists(username)) {
                model.addAttribute("userexists", true);
            } else {
                userService.save(username, password);
                model.addAttribute("success", true);
                return "auth/login";
            }
        }
        return "auth/register";
    }
}
