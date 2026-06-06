package com.student.inventory.controller;

import com.student.inventory.model.Category;
import com.student.inventory.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/category-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/category-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "category/category-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "category/category-form";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Kategori basariyla kaydedildi!");
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Kategori silindi!");
        return "redirect:/categories";
    }
}
