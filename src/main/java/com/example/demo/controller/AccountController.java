package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Account;

@Controller
public class AccountController {

	private final HttpSession session;
	private final Account account;

	public AccountController(HttpSession session, Account account) {
		this.session = session;
		this.account = account;
	}

	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		// セッション情報を全てクリアする
		session.invalidate();

		return "login";
	}

}
