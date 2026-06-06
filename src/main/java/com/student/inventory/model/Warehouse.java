package com.student.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Depo adi bos birakilamaz")
    @Column(nullable = false)
    private String name;

    @Min(value = 1, message = "Kapasite en az 1 olmalidir")
    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "warehouse")
    private List<Product> products = new ArrayList<>();

    public Warehouse() {
    }

    public Warehouse(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
