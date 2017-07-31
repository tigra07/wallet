package com.zor07.controllers;

import com.zor07.domain.Source;
import com.zor07.domain.User;
import com.zor07.services.SourceService;
import com.zor07.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SourceController {
    private SourceService service;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setService(SourceService service) {
        this.service = service;
    }

    @RequestMapping("/sources/list")
    public String list(Model model){
        User currentUser = userService.getCurrentLoggedInUser();
        model.addAttribute("sources", service.list(currentUser));
        return "/sources/list";
    }

    @RequestMapping("/sources/details/{id}")
    public String card(@PathVariable Integer id, Model model){
        model.addAttribute("source", service.getById(id));
        return "sources/details";
    }

    @RequestMapping("sources/new")
    public String newSource(Model model){
        model.addAttribute("source", new Source());
        return "sources/new";
    }

    @RequestMapping(value = "/sources/save", method = RequestMethod.POST)
    public String saveSource(Source source){
        source.setUser(userService.getCurrentLoggedInUser());
        service.save(source);
        return "redirect:/sources/list";
    }

    @RequestMapping("/sources/edit/{id}")
    public String editSource(@PathVariable Integer id, Model model){
        model.addAttribute("source", service.getById(id));
        return "sources/new";
    }

    @RequestMapping("/sources/delete/{id}")
    public String deleteSource(@PathVariable Integer id){
        service.delete(id);
        return "redirect:/sources/list";
    }
}
