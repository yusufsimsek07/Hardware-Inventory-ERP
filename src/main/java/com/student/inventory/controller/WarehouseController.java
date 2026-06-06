package com.student.inventory.controller;

import com.student.inventory.model.Warehouse;
import com.student.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("warehouses", warehouseService.findAll());
        return "warehouse/warehouse-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        return "warehouse/warehouse-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("warehouse", warehouseService.findById(id));
        return "warehouse/warehouse-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("warehouse") Warehouse warehouse,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "warehouse/warehouse-form";
        }
        warehouseService.save(warehouse);
        redirectAttributes.addFlashAttribute("message", "Depo basariyla kaydedildi!");
        return "redirect:/warehouses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        warehouseService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Depo silindi!");
        return "redirect:/warehouses";
    }
}
