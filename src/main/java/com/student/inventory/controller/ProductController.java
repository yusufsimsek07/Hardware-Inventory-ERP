package com.student.inventory.controller;

import com.student.inventory.model.Product;
import com.student.inventory.service.CategoryService;
import com.student.inventory.service.ProductService;
import com.student.inventory.service.SupplierService;
import com.student.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final WarehouseService warehouseService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             SupplierService supplierService,
                             WarehouseService warehouseService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/product-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("warehouses", warehouseService.findAll());
        return "product/product-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("warehouses", warehouseService.findAll());
        return "product/product-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") Product product,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("suppliers", supplierService.findAll());
            model.addAttribute("warehouses", warehouseService.findAll());
            return "product/product-form";
        }
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Urun basariyla kaydedildi!");
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Urun silindi!");
        return "redirect:/products";
    }
}
