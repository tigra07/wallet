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

    @RequestMapping("/categories/list")
    public String list(Model model){
        User currentUser = userService.getCurrentLoggedInUser();
        model.addAttribute("categories", categoryService.list(currentUser));
        return "categories/list";
    }

    @RequestMapping("/categories/details/{id}")
    public String card(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryService.getById(id));
        return "categories/details";
    }

    @RequestMapping("/categories/new")
    public String newCategory(Model model){
        model.addAttribute("types", EntryType.values());
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
    public String saveCategory(Category category){
        category.setUser(userService.getCurrentLoggedInUser());
        categoryService.save(category);
        return "redirect:/categories/list";
    }

    @RequestMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Integer id, Model model){
        model.addAttribute("types", EntryType.values());
        model.addAttribute("category", categoryService.getById(id));
        return "categories/form";
    }

    @RequestMapping(value = "/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id){
        categoryService.delete(id);
        return "redirect:/categories/list";
    }
}
