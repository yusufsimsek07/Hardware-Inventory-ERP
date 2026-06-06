package com.student.inventory.controller;

import com.student.inventory.model.OrderItem;
import com.student.inventory.service.CustomerOrderService;
import com.student.inventory.service.OrderItemService;
import com.student.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final CustomerOrderService customerOrderService;
    private final ProductService productService;

    public OrderItemController(OrderItemService orderItemService,
                               CustomerOrderService customerOrderService,
                               ProductService productService) {
        this.orderItemService = orderItemService;
        this.customerOrderService = customerOrderService;
        this.productService = productService;
    }

    @GetMapping("/order/{orderId}")
    public String listByOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", customerOrderService.findById(orderId));
        model.addAttribute("orderItems", orderItemService.findByOrderId(orderId));
        return "orderitem/orderitem-list";
    }

    @GetMapping("/new/{orderId}")
    public String showCreateForm(@PathVariable Long orderId, Model model) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCustomerOrder(customerOrderService.findById(orderId));
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orderId", orderId);
        return "orderitem/orderitem-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        OrderItem orderItem = orderItemService.findById(id);
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orderId", orderItem.getCustomerOrder().getId());
        return "orderitem/orderitem-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("orderItem") OrderItem orderItem,
                       BindingResult result,
                       @RequestParam("orderId") Long orderId,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("products", productService.findAll());
            model.addAttribute("orderId", orderId);
            return "orderitem/orderitem-form";
        }
        // Set the parent order
        orderItem.setCustomerOrder(customerOrderService.findById(orderId));
        // Set unit price from product if not manually entered
        if (orderItem.getUnitPrice() <= 0 && orderItem.getProduct() != null) {
            orderItem.setUnitPrice(orderItem.getProduct().getPrice());
        }
        orderItemService.save(orderItem);
        redirectAttributes.addFlashAttribute("message", "Siparis kalemi basariyla kaydedildi!");
        return "redirect:/order-items/order/" + orderId;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        OrderItem item = orderItemService.findById(id);
        Long orderId = item.getCustomerOrder().getId();
        orderItemService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Siparis kalemi silindi!");
        return "redirect:/order-items/order/" + orderId;
    }
}
