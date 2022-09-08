package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	/** ログイン 画面 を 表示 */
	@GetMapping("/login")
	public String getLogin() {
		return "login/login";
	}
	
	/** ユーザー 一覧 画面 に リダイレクト */
	@PostMapping("/login")
	public String postLogin() {
		return "redirect:/user/list";
	}
}