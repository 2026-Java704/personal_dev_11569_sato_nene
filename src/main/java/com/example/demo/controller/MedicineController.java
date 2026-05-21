package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class MedicineController {

	private final MedicineRepository medicineRepository; //medicineテーブル操作用
	private final Account account;
	private final UserRepository userRepository;

	public MedicineController(MedicineRepository medicineRepository, Account account, UserRepository userRepository) {
		this.medicineRepository = medicineRepository;
		this.account = account;
		this.userRepository = userRepository;
	}

	//	 薬一覧表示
	@GetMapping("/medicine")
	public String index(Model model) {

		//		User user = new User(1, "", "");

		//	 全薬の一覧を取得 
		List<Medicine> medicineList = medicineRepository.findByUserIdOrderById(account.getId());
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

		Medicine medicine = new Medicine();//新しい Medicineオブジェクト薬データを入れる箱

		medicine.setName(name);//フォームから受け取った name を、Medicineオブジェク
		medicine.setCount(count);
		medicine.setNote(note);

		medicine.setM_check(false);

		//ログイン中のユーザーをセット
		User loginUser = userRepository.findById(account.getId()).get();
		medicine.setUser(loginUser);

		medicineRepository.save(medicine); // DB保存
		return "redirect:/medicine";

	}

	//	更新画面表示

	@GetMapping("/medicine/{id}/edit")
	public String edit(
			@PathVariable Integer id,
			Model model) {

		Medicine medicine = medicineRepository.findById(id).get();

		model.addAttribute("medicine", medicine);
		return "editMedicine";

	}

	//	更新処理
	@PostMapping("/medicine/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam String name,
			@RequestParam Integer count,
			@RequestParam String note) {

		Medicine medicine = medicineRepository.findById(id).get();

		medicine.setName(name);
		medicine.setCount(count);
		medicine.setNote(note);

		medicineRepository.save(medicine);

		return "redirect:/medicine";
	}

	//	削除処理
	@PostMapping("/medicine/{id}/delete")
	public String delete(@PathVariable Integer id) {

		medicineRepository.deleteById(id);//medicineテーブルから削除
		return "redirect:/medicine";

	}

	//	チェックボタン処理
	@PostMapping("/medicine/check")
	public String check(
			@RequestParam Integer id, //薬テーブルid
			@RequestParam(required = false) boolean mcheck) {
		{
			Medicine medicine = medicineRepository.findById(id).get();

			medicine.setM_check(mcheck);
			//
			medicineRepository.save(medicine);
			return "redirect:/medicine";
		}
		//
	}

}