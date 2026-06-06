package com.student.inventory.controller;

import com.student.inventory.model.Supplier;
import com.student.inventory.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        return "supplier/supplier-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/supplier-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.findById(id));
        return "supplier/supplier-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("supplier") Supplier supplier,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "supplier/supplier-form";
        }
        supplierService.save(supplier);
        redirectAttributes.addFlashAttribute("message", "Tedarikci basariyla kaydedildi!");
        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        supplierService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Tedarikci silindi!");
        return "redirect:/suppliers";
    }
}
