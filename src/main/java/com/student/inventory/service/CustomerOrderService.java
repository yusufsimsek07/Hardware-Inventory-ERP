package com.student.inventory.service;

import com.student.inventory.model.CustomerOrder;
import com.student.inventory.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    public CustomerOrder findById(Long id) {
        return customerOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Siparis bulunamadi: " + id));
    }

    public CustomerOrder save(CustomerOrder order) {
        return customerOrderRepository.save(order);
    }

    public void deleteById(Long id) {
        customerOrderRepository.deleteById(id);
    }
}
