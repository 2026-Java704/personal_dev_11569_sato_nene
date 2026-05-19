package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
	// SELECT * FROM items WHERE category_id = ?
	List<Medicine> findBymedicineId(Integer medicineId);
}
