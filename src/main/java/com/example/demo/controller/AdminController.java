package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	/** アド ミン 権限 専用 画面 に 遷移 */
	@GetMapping("/admin")
	public String getAdmin() {
		
		return "admin/admin";
	}
}
