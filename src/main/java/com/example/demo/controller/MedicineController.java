package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Medicine;
import com.example.demo.repository.MedicineRepository;

@Controller
public class MedicineController {

	private final MedicineRepository medicineRepository; //medicineテーブル操作用

	public MedicineController(MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}

	// 薬一覧表示
	@GetMapping("/")
	public String index(Model model) {

		// 全薬の一覧を取得
		List<Medicine> medicineList = medicineRepository.findAll();
		model.addAttribute("medicine", medicineList);
		return "medicine";
	}
}
