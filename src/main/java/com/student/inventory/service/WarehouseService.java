package com.student.inventory.service;

import com.student.inventory.model.Warehouse;
import com.student.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depo bulunamadi: " + id));
    }

    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteById(Long id) {
        warehouseRepository.deleteById(id);
    }
}
