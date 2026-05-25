package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
	List<Medicine> findByUser_IdAndMCheckFalseOrderByDateAscIdAsc(Integer userId);

	List<Medicine> findByUser_IdAndMCheckFalseAndNameContainingOrderByDateAscIdAsc(Integer userId, String name);

	List<Medicine> findByUser_IdAndMCheckTrueOrderByTimeDescIdDesc(Integer userId);
}
