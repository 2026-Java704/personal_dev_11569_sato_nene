package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Medicine;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;

@Controller
public class MedicineController {

	private final MedicineRepository medicineRepository; //medicineテーブル操作用
	private final Account account;

	public MedicineController(MedicineRepository medicineRepository, Account account) {
		this.medicineRepository = medicineRepository;
		this.account = account;
	}

	//	 薬一覧表示
	@GetMapping("/medicine")
	public String index(Model model) {

		//		User user = new User(1, "", "");

		//				 全薬の一覧を取得 
		List<Medicine> medicineList = medicineRepository.findByUserId(account.getId());
		model.addAttribute("medicineList", medicineList);
		return "medicine";
	}

	//	薬登録画面の表示
	@GetMapping("/medicine/add")
	public String create() {
		return "addMedicine";

	}

	//
	//薬登録処理
	@PostMapping("/medicine/add")
	public String store(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note) {

		Medicine medicine = new Medicine();
		return note;

	}
}
