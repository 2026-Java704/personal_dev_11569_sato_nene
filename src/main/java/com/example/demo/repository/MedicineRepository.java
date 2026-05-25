package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
	List<Medicine> findByUser_IdAndCheckedFalseOrderByDateAscIdAsc(Integer userId);

	List<Medicine> findByUser_IdAndCheckedFalseAndNameContainingOrderByDateAscIdAsc(Integer userId, String name);

	List<Medicine> findByUser_IdAndCheckedTrueOrderByTimeDescIdDesc(Integer userId);
}
