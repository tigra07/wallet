package com.zor07.controllers;

import com.zor07.domain.*;
import com.zor07.services.CategoryService;
import com.zor07.services.EntryService;
import com.zor07.services.SourceService;
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
import java.util.stream.Collectors;

@Controller
public class EntryController {
    private EntryService entryService;
    private CategoryService categoryService;
    private SourceService sourceService;

    @Autowired
    public void setSourceService(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }

    @RequestMapping("/entries/list")
    public String list(Model model){
        model.addAttribute("entries", entryService.list());
        return "entries/list";
    }

    @RequestMapping("/entries/details/{id}")
    public String card(@PathVariable Integer id, Model model){
        model.addAttribute("entry", entryService.getById(id));
        return "entries/details";
    }

    @RequestMapping("/entries/new_entry")
    public String newIncomeEntry(Model model){
        model.addAttribute("sources", sourceService.list());
        model.addAttribute("oldCategory", new Category());
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("entry", new Entry());
        model.addAttribute("entryType", entryService.getEntryTypes());
        return "entries/new_entry";
    }

    @RequestMapping(value = "/entries/save", method = RequestMethod.POST)
    public String saveEntry(Entry entry){
        System.out.println(entry);
        entryService.save(entry);
        return "redirect:/entries/list";
    }

    @RequestMapping("/entries/edit/{id}")
    public String editEntry(@PathVariable Integer id, Model model){
        Entry entry = entryService.getById(id);
        model.addAttribute("entry", entry);
        if (entry.getCategory() != null){
            model.addAttribute("oldCategory", entry.getCategory());
        }
        model.addAttribute("entryType", EntryType.values());
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("sources", sourceService.list());
        return "entries/new_entry";

    }

    @RequestMapping(value = "/entries/delete/{id}")
    public String saveEntry(@PathVariable Integer id){
        entryService.delete(id);
        return "redirect:/entries/list";
    }

    @RequestMapping("/entries/api/filter")
    public ResponseEntity<?> filter(@Valid @RequestBody SearchCriteria searchCriteria, Errors errors){
        System.out.println((searchCriteria == null) + " " + (errors == null));
        AjaxResponseBody result = new AjaxResponseBody();
        if (errors.hasErrors()){
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }
        result.setMsg("test");
        return ResponseEntity.ok(result);
    }
}
