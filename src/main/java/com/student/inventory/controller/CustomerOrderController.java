package com.student.inventory.controller;

import com.student.inventory.model.CustomerOrder;
import com.student.inventory.service.CustomerOrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", customerOrderService.findAll());
        return "order/order-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customerOrder", new CustomerOrder());
        return "order/order-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("customerOrder", customerOrderService.findById(id));
        return "order/order-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customerOrder") CustomerOrder customerOrder,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "order/order-form";
        }
        customerOrderService.save(customerOrder);
        redirectAttributes.addFlashAttribute("message", "Siparis basariyla kaydedildi!");
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerOrderService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Siparis silindi!");
        return "redirect:/orders";
    }
}
