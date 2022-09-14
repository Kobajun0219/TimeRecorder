package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.application.service.UserApplicationService;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.UserDetailForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserPageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserApplicationService userApplicationService;
	
	@GetMapping("/mypage")
	public String getUserList(UserDetailForm form, Model model, HttpServletRequest httpServletRequest) {

		// ユーザー情報を取得
		String username = httpServletRequest.getRemoteUser();
		MUser loginUser = userService.getUserOne(username);
		String userId = loginUser.getUserId();
		MUser user = userService.getUserOne(userId);
		user.setPassword(null);
		// MUser を form に 変換 
		form = modelMapper.map(user,UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());
		// Model に 登録
		model.addAttribute("userDetailForm", form);
		
		
		//個人の出勤記録を取得
		int id = loginUser.getId();
		List<WorkUser> workRecord = userService.getRecord(id);
		System.out.println(workRecord);
		
        //勤務時間と出勤日時を追加	
		for (int i = 0; i < workRecord.size(); i++) {   
			
			//勤務時間追加
			String duration = userApplicationService.getWorkDuration(workRecord.get(i));
			workRecord.get(i).setDuration(duration);
			
			//出勤日時を生成
			LocalDate startDate = userApplicationService.getStartDate(workRecord.get(i));
			workRecord.get(i).setStartDate(startDate);
			
		}
		 //Model に 登録
		model.addAttribute("workRecord", workRecord);
		System.out.println(model);
		// マイページ を 表示
		return "user/myPage";
	}

}
