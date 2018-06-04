package com.zor07.controllers;

import com.zor07.domain.Account;
import com.zor07.domain.User;
import com.zor07.services.AccountService;
import com.zor07.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/accounts/")
public class AccountController {
    private AccountService service;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setService(AccountService service) {
        this.service = service;
    }

    @RequestMapping("/list")
    public String list(Model model){
        User currentUser = userService.getCurrentLoggedInUser();
        model.addAttribute("accounts", service.list(currentUser));
        return "accounts/list";
    }

    @RequestMapping("/details/{id}")
    public String card(@PathVariable Integer id, Model model){
        model.addAttribute("account", service.getById(id));
        return "accounts/details";
    }

    @RequestMapping("/new")
    public String newAccount(Model model){
        model.addAttribute("account", new Account());
        return "accounts/new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAccount(Account account){
        account.setUser(userService.getCurrentLoggedInUser());
        service.save(account);
        return "redirect:/accounts/list";
    }

    @RequestMapping("/edit/{id}")
    public String editAccount(@PathVariable Integer id, Model model){
        model.addAttribute("account", service.getById(id));
        return "accounts/new";
    }

    @RequestMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Integer id){
        service.delete(id);
        return "redirect:/accounts/list";
    }
}
