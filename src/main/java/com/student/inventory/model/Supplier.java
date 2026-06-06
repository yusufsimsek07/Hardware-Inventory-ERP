package com.student.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firma adi bos birakilamaz")
    @Column(nullable = false)
    private String companyName;

    @NotBlank(message = "Yetkili kisi adi bos birakilamaz")
    @Column(nullable = false)
    private String contactName;

    @NotBlank(message = "Telefon numarasi bos birakilamaz")
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(String companyName, String contactName, String phone) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.phone = phone;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
