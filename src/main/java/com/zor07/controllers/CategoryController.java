package com.zor07.controllers;

import com.zor07.domain.Category;
import com.zor07.domain.EntryType;
import com.zor07.domain.User;
import com.zor07.services.CategoryService;
import com.zor07.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/list")
    public String list(Model model){
        User currentUser = userService.getCurrentLoggedInUser();
        model.addAttribute("categories", categoryService.list(currentUser));
        return "categories/list";
    }

    @RequestMapping("/details/{id}")
    public String card(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryService.getById(id));
        return "categories/details";
    }

    @RequestMapping("/new")
    public String newCategory(Model model){
        model.addAttribute("types", EntryType.values());
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCategory(Category category){
        category.setUser(userService.getCurrentLoggedInUser());
        categoryService.save(category);
        return "redirect:/categories/list";
    }

    @RequestMapping("/edit/{id}")
    public String editCategory(@PathVariable Integer id, Model model){
        model.addAttribute("types", EntryType.values());
        model.addAttribute("category", categoryService.getById(id));
        return "categories/form";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteCategory(@PathVariable Integer id){
        categoryService.delete(id);
        return "redirect:/categories/list";
    }
}
