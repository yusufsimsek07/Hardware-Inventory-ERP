package com.student.inventory.service;

import com.student.inventory.model.OrderItem;
import com.student.inventory.model.CustomerOrder;
import com.student.inventory.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final CustomerOrderService customerOrderService;

    public OrderItemService(OrderItemRepository orderItemRepository,
                            CustomerOrderService customerOrderService) {
        this.orderItemRepository = orderItemRepository;
        this.customerOrderService = customerOrderService;
    }

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByCustomerOrderId(orderId);
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Siparis kalemi bulunamadi: " + id));
    }

    /**
     * Saves the order item and recalculates the parent order's total.
     */
    public OrderItem save(OrderItem orderItem) {
        OrderItem saved = orderItemRepository.save(orderItem);
        // Recalculate order total
        CustomerOrder order = saved.getCustomerOrder();
        List<OrderItem> items = orderItemRepository.findByCustomerOrderId(order.getId());
        order.setOrderItems(items);
        order.recalculateTotal();
        customerOrderService.save(order);
        return saved;
    }

    /**
     * Deletes the order item and recalculates the parent order's total.
     */
    public void deleteById(Long id) {
        OrderItem item = findById(id);
        Long orderId = item.getCustomerOrder().getId();
        orderItemRepository.deleteById(id);
        // Recalculate order total after deletion
        CustomerOrder order = customerOrderService.findById(orderId);
        List<OrderItem> items = orderItemRepository.findByCustomerOrderId(orderId);
        order.setOrderItems(items);
        order.recalculateTotal();
        customerOrderService.save(order);
    }
}
