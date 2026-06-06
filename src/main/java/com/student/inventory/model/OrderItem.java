package com.student.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Miktar en az 1 olmalidir")
    @Column(nullable = false)
    private int quantity;

    @Min(value = 0, message = "Birim fiyat negatif olamaz")
    @Column(nullable = false)
    private double unitPrice;

    @NotNull(message = "Siparis secilmelidir")
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder customerOrder;

    @NotNull(message = "Urun secilmelidir")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderItem() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
