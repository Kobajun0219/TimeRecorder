package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.UserDetailForm;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserDetailController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/** ユーザー 詳細 画面 を 表示 */
	@GetMapping("/detail/{userId:.+}")
	public String getUser(UserDetailForm form, Model model,
			@PathVariable("userId") String userId) {
		
		// ユーザー を 1 件 取得
		MUser user = userService.getUserOne(userId);
		user.setPassword(null);
		
		// MUser を form に 変換 
		form = modelMapper.map(user,UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());
		
		// Model に 登録
		model.addAttribute("userDetailForm", form);
		
		// ユーザー 詳細 画面 を 表示
		return "user/detail";
		
	}
		
	/** ユーザー 更新 処理 */ 
	@PostMapping(value ="/detail", params ="update")
	public String updateUser(UserDetailForm form, Model model) {
		
		try {
		// ユーザー を 更新
		userService.updateUserOne(form. getUserId(),
				form. getPassword(),
				form. getUserName());
		}catch(Exception e) {
			log.error("ユーザー更新でエラー",e);
		}
		
		// ユーザー 一覧 画面 に リダイレクト
		return "redirect:/user/list";
	}
	
	/** ユーザー 削除 処理 */
	@PostMapping(value ="/detail", params ="delete")
	public String deleteUser(UserDetailForm form, Model model) {
		
		// ユーザー を 削除
		userService.deleteUserOne(form. getUserId());
		
		// ユーザー 一覧 画面 に リダイレクト
		return "redirect:/user/list";
	}
}
