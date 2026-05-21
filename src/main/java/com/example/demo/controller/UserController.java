package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	private final Account account;
	private final UserRepository userRepository;

	public UserController(UserRepository userRepository, Account account) {
		this.userRepository = userRepository;
		this.account = account;
	}

	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {

		// ログアウト時はアカウント情報を空にする
		account.setId(null);
		account.setName(null);

		return "login";
	}

	// ログインボタンクリック
	@PostMapping("/login")
	public String login(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			Model model) {

		// 前後の空白を除去
		name = name.trim();
		password = password.trim();

		// 名前とパスワードの両方が空の場合
		if (name.length() == 0 && password.length() == 0) {
			model.addAttribute("message", "名前とパスワードを入力してください");
			return "login";
		}

		// 名前が空の場合
		if (name.length() == 0) {
			model.addAttribute("message", "名前を入力してください");
			return "login";
		}

		// パスワードが空の場合
		if (password.length() == 0) {
			model.addAttribute("message", "パスワードを入力してください");
			return "login";
		}

		// DBから名前とパスワードが一致するユーザーを検索
		List<User> userList = userRepository.findByNameAndPassword(name, password);

		// 一致するユーザーがいない場合
		if (userList == null || userList.size() == 0) {
			model.addAttribute("message", "名前またはパスワードが一致しませんでした");
			return "login";
		}

		// ログイン成功
		User user = userList.get(0);

		// セッション管理されたAccountにログインユーザー情報を保存

		account.setName(user.getName());
		account.setId(user.getId());

		// 薬一覧画面へ移動
		return "redirect:/medicine";
	}

	// 会員登録画面の表示
	@GetMapping("/user/add")
	public String create() {
		return "accountForm";
	}

	// 登録処理
	@PostMapping("/user/add")
	public String store(
			@RequestParam String name,
			@RequestParam String password,
			Model model) {

		// 前後の空白を除去
		name = name.trim();
		password = password.trim();

		// 名前とパスワードの両方が空の場合
		if (name.length() == 0 && password.length() == 0) {
			model.addAttribute("message", "名前とパスワードを入力してください");
			return "login";
		}

		// 名前が空の場合
		if (name.length() == 0) {
			model.addAttribute("message", "名前を入力してください");
			return "accountForm";
		}

		// パスワードが空の場合
		if (password.length() == 0) {
			model.addAttribute("message", "パスワードを入力してください");
			return "accountForm";
		}

		User user = new User(name, password);
		userRepository.save(user);

		return "redirect:/login";

	}
}