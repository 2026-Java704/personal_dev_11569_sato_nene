package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	private final MedicineRepository medicineRepository;
	private final Account account;
	private final UserRepository userRepository;

	private final List<String> medicineMasterList = Arrays.asList(
			"アセトアミノフェン", "イブプロフェン", "ロキソプロフェン", "カロナール", "ロキソニン",
			"バファリン", "ノーシン", "セデス", "タイレノール", "ナロンエース",
			"プレコール", "パブロン", "ベンザブロック", "ルル", "エスタック",
			"トラネキサム酸", "ムコダイン", "ビソルボン", "カルボシステイン", "アンブロキソール",
			"クラリスロマイシン", "アモキシシリン", "セフカペン", "レボフロキサシン", "ジスロマック",
			"タミフル", "イナビル", "リレンザ", "ゾフルーザ", "アレグラ",
			"クラリチン", "ジルテック", "アレロック", "タリオン", "ザイザル",
			"モンテルカスト", "オノン", "ムコスタ", "ガスター", "タケキャブ",
			"ネキシウム", "パリエット", "ブスコパン", "ガスモチン", "プリンペラン",
			"ビオフェルミン", "ミヤBM", "ラックビー", "正露丸", "ストッパ",
			"ラキソベロン", "マグミット", "酸化マグネシウム", "センノシド", "コーラック",
			"メジコン", "アスベリン", "フスコデ", "リンデロン", "フルナーゼ",
			"オルパタジン", "ポララミン", "PL顆粒", "葛根湯", "麻黄湯",
			"五苓散", "補中益気湯", "六君子湯", "小青竜湯", "芍薬甘草湯",
			"ユベラ", "メチコバール", "チョコラBB", "ビタメジン", "ハイチオール",
			"リリカ", "トラムセット", "ボルタレン", "モーラステープ", "ロキソニンテープ",
			"ヒルドイド", "リンデロンVG", "ゲンタシン", "アズノール", "イソジン",
			"オロパタジン", "ラベプラゾール", "フェキソフェナジン", "カルボプラチン", "メトホルミン",
			"アムロジピン", "カンデサルタン", "メインテート", "フロセミド", "ワルファリン",
			"エリキュース", "ジャディアンス", "フォシーガ", "クレストール", "アトルバスタチン");

	public MedicineController(MedicineRepository medicineRepository, Account account, UserRepository userRepository) {
		this.medicineRepository = medicineRepository;
		this.account = account;
		this.userRepository = userRepository;
	}

	//管理画面
	@GetMapping("/medicine")
	public String index(@RequestParam(defaultValue = "") String keyword, Model model) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		//薬検索
		List<Medicine> medicineList;
		if (keyword.length() > 0) {
			medicineList = medicineRepository.findByUser_IdAndCheckedFalseAndNameContainingOrderByDateAscIdAsc(
					account.getId(),
					keyword);
		} else {
			medicineList = medicineRepository.findByUser_IdAndCheckedFalseOrderByDateAscIdAsc(account.getId());
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("medicineList", medicineList);
		return "medicine";
	}

	//薬追加画面
	@GetMapping("/medicine/add")
	public String create(Model model) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		setFormOptions(model);
		model.addAttribute("medicineNames", medicineMasterList);
		return "addMedicine";
	}

	@PostMapping("/medicine/add")
	public String store(
			@RequestParam(defaultValue = "") String selectedName,
			@RequestParam(defaultValue = "") String customName,
			@RequestParam(required = false) Integer count,
			@RequestParam(defaultValue = "") String medicineType,
			@RequestParam(defaultValue = "") String timing,
			@RequestParam(defaultValue = "") String mealTiming,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(required = false) LocalDate date,
			Model model) {

		if (account.getId() == null) {
			return "redirect:/login";
		}

		String name = selectedName;
		if (customName.trim().length() > 0) {
			// ここは手入力があったらそっち優先でええやろ、という素直な分岐です。
			name = customName.trim();
		}

		List<String> errorList = validateMedicine(name, count, medicineType, timing, mealTiming, date);
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			setFormOptions(model);
			model.addAttribute("medicineNames", medicineMasterList);
			model.addAttribute("selectedName", selectedName);
			model.addAttribute("customName", customName);
			model.addAttribute("count", count);
			model.addAttribute("medicineType", medicineType);
			model.addAttribute("timing", timing);
			model.addAttribute("mealTiming", mealTiming);
			model.addAttribute("note", note);
			model.addAttribute("date", date);
			return "addMedicine";
		}

		Optional<User> userData = userRepository.findById(account.getId());
		if (userData.isEmpty()) {
			return "redirect:/login";
		}

		Medicine medicine = new Medicine();
		medicine.setName(name);
		medicine.setCount(count);
		medicine.setMedicineType(medicineType);
		medicine.setTiming(timing);
		medicine.setMealTiming(mealTiming);
		medicine.setNote(note.trim());
		medicine.setDate(date);
		medicine.setTime(null);
		medicine.setChecked(false);
		medicine.setUser(userData.get());

		medicineRepository.save(medicine);
		return "redirect:/medicine";
	}

	//薬更新画面
	@GetMapping("/medicine/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		Optional<Medicine> medicineData = medicineRepository.findById(id);
		if (medicineData.isEmpty()) {
			return "redirect:/medicine";
		}

		Medicine medicine = medicineData.get();
		if (!medicine.getUser().getId().equals(account.getId())) {
			return "redirect:/medicine";
		}

		setFormOptions(model);
		model.addAttribute("medicineNames", medicineMasterList);
		model.addAttribute("medicine", medicine);
		return "editMedicine";
	}

	//薬更新処理
	@PostMapping("/medicine/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") String selectedName,
			@RequestParam(defaultValue = "") String customName,
			@RequestParam(required = false) Integer count,
			@RequestParam(defaultValue = "") String medicineType,
			@RequestParam(defaultValue = "") String timing,
			@RequestParam(defaultValue = "") String mealTiming,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(required = false) LocalDate date,
			Model model) {

		if (account.getId() == null) {
			return "redirect:/login";
		}

		Optional<Medicine> medicineData = medicineRepository.findById(id);
		if (medicineData.isEmpty()) {
			return "redirect:/medicine";
		}

		Medicine medicine = medicineData.get();
		if (!medicine.getUser().getId().equals(account.getId())) {
			return "redirect:/medicine";
		}

		String name = selectedName;
		if (customName.trim().length() > 0) {
			name = customName.trim();
		}

		List<String> errorList = validateMedicine(name, count, medicineType, timing, mealTiming, date);
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			setFormOptions(model);
			model.addAttribute("medicineNames", medicineMasterList);
			medicine.setName(name);
			medicine.setCount(count);
			medicine.setMedicineType(medicineType);
			medicine.setTiming(timing);
			medicine.setMealTiming(mealTiming);
			medicine.setNote(note.trim());
			medicine.setDate(date);
			model.addAttribute("medicine", medicine);
			return "editMedicine";
		}

		medicine.setName(name);
		medicine.setCount(count);
		medicine.setMedicineType(medicineType);
		medicine.setTiming(timing);
		medicine.setMealTiming(mealTiming);
		medicine.setNote(note.trim());
		medicine.setDate(date);

		medicineRepository.save(medicine);
		return "redirect:/medicine";
	}

	//薬削除処理
	@PostMapping("/medicine/{id}/delete")
	public String delete(@PathVariable Integer id) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		Optional<Medicine> medicineData = medicineRepository.findById(id);
		if (medicineData.isEmpty()) {
			return "redirect:/medicine";
		}

		Medicine medicine = medicineData.get();
		if (!medicine.getUser().getId().equals(account.getId())) {
			return "redirect:/medicine";
		}

		medicineRepository.deleteById(id);
		return "redirect:/medicine";
	}

	//チェックボタン処理	
	@PostMapping("/medicine/check")
	public String check(@RequestParam Integer id, @RequestParam(defaultValue = "false") boolean mcheck) {

		//未ログイン時
		if (account.getId() == null) {
			return "redirect:/login";
		}

		Optional<Medicine> medicineData = medicineRepository.findById(id);
		if (medicineData.isEmpty()) {
			return "redirect:/medicine";
		}

		Medicine medicine = medicineData.get();
		if (!medicine.getUser().getId().equals(account.getId())) {
			return "redirect:/medicine";
		}

		medicine.setChecked(mcheck);
		if (mcheck) {
			// 済にした瞬間の時間を残せば履歴で見やすいので、ここで保存します。
			medicine.setTime(LocalTime.now().withSecond(0).withNano(0));
		} else {
			medicine.setTime(null);
		}

		medicineRepository.save(medicine);

		if (mcheck) {
			return "redirect:/manage";
		}
		return "redirect:/medicine";
	}

	//服用履歴画面
	@GetMapping("/manage")
	public String manage(Model model) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		List<Medicine> medicineList = medicineRepository
				.findByUser_IdAndCheckedTrueOrderByTimeDescIdDesc(account.getId());
		model.addAttribute("medicineList", medicineList);
		return "manage";
	}

	private List<String> validateMedicine(String name, Integer count, String medicineType, String timing,
			String mealTiming, LocalDate date) {
		List<String> errorList = new ArrayList<>();

		if (name.trim().length() == 0) {
			errorList.add("薬名は選ぶか入力してください。");
		}
		if (count == null || count <= 0) {
			errorList.add("個数は1以上で入れてください。");
		}
		if (medicineType.length() == 0) {
			errorList.add("処方薬か市販薬かを選んでください。");
		}
		if (timing.length() == 0) {
			errorList.add("朝・昼・夜を選んでください。");
		}
		if (mealTiming.length() == 0) {
			errorList.add("食前か食後かを選んでください。");
		}
		if (date == null) {
			errorList.add("飲み始め日を入れてください。");
		}

		return errorList;
	}

	private void setFormOptions(Model model) {
		model.addAttribute("medicineTypes", Arrays.asList("処方薬", "市販薬"));
		model.addAttribute("timingOptions", Arrays.asList("朝", "昼", "夜"));
		model.addAttribute("mealTimingOptions", Arrays.asList("食前", "食後"));
	}
}