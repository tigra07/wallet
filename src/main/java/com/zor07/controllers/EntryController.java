package com.zor07.controllers;

import com.zor07.domain.*;
import com.zor07.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/entries")
public class EntryController {
    private EntryService entryService;
    private CategoryService categoryService;
    private AccountService accountService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }

    @RequestMapping("/list")
    public String list(Model model){
        User currentUser = userService.getCurrentLoggedInUser();
        model.addAttribute("noEntries", entryService.list(currentUser).isEmpty());

        /*Attributes for filtering entries*/
        model.addAttribute("accounts", accountService.list(currentUser));
        model.addAttribute("entryTypes", entryService.getEntryTypes());
        model.addAttribute("categories", categoryService.list(currentUser));
        return "entries/list";
    }

    @RequestMapping("/details/{id}")
    public String card(@PathVariable Integer id, Model model){
        model.addAttribute("entry", entryService.getById(id));
        return "entries/details";
    }

    @RequestMapping("/new_entry")
    public String newIncomeEntry(Model model){
        User currentUser = userService.getCurrentLoggedInUser();
        model.addAttribute("accounts", accountService.list(currentUser));
        model.addAttribute("oldCategory", new Category());
        model.addAttribute("categories", categoryService.list(currentUser));
        model.addAttribute("entry", new Entry());
        model.addAttribute("entryType", entryService.getEntryTypes());
        return "entries/new_entry";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEntry(Entry entry){
        entry.setUser(userService.getCurrentLoggedInUser());
        entryService.save(entry);
        return "redirect:/entries/list";
    }

    @RequestMapping("/edit/{id}")
    public String editEntry(@PathVariable Integer id, Model model){
        User currentUser = userService.getCurrentLoggedInUser();
        Entry entry = entryService.getById(id);
        model.addAttribute("entry", entry);
        if (entry.getCategory() != null){
            model.addAttribute("oldCategory", entry.getCategory());
        }
        model.addAttribute("entryType", EntryType.values());
        model.addAttribute("categories", categoryService.list(currentUser));
        model.addAttribute("accounts", accountService.list(currentUser));
        return "entries/new_entry";

    }

    @RequestMapping(value = "/delete/{id}")
    public String saveEntry(@PathVariable Integer id){
        entryService.delete(id);
        return "redirect:/entries/list";
    }

    @RequestMapping("/api/filter")
    public ResponseEntity<?> filter(@Valid @RequestBody SearchCriteria searchCriteria, Errors errors){
        AjaxResponseBody result = new AjaxResponseBody();
        if (errors.hasErrors()){
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }
        List<Entry> entries = entryService.findAllMatchingCriteria(searchCriteria);
        result.setEntries(entries);

        if (entries.isEmpty()) {
            result.setMsg("no user found!");
        } else {
            result.setMsg("success");
        }


        return ResponseEntity.ok(result);
    }
}
