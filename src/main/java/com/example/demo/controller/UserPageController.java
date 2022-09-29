package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
		String userMail = loginUser.getUserMail();
		MUser user = userService.getUserOne(userMail);
		user.setPassword(null);
		// MUser を form に 変換 
		form = modelMapper.map(user,UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());
		// Model に 登録
		model.addAttribute("userDetailForm", form);
		
		
		//個人の出勤記録を取得
		int id = loginUser.getId();
		List<WorkUser> workRecords = userService.getRecord(id) ;
		System.out.println(workRecords);
		
//		//勤務時間追加
//		String duration = userApplicationService.getWorkDuration(workRecord);
//		workRecord.setDuration(duration);
//		
//		//出勤日時を生成
//		LocalDate startDate = userApplicationService.getStartDate(workRecord);
//		workRecord.setStartDate(startDate);
		
		
//        //勤務時間と出勤日時を追加	
		for (WorkUser workRecord: workRecords) {   
			System.out.println(workRecord);
			
			//勤務時間追加
			String duration = userApplicationService.getWorkDuration(workRecord);
			workRecord.setDuration(duration);
			
			//出勤日時を生成
			LocalDate startDate = userApplicationService.getStartDate(workRecord);
			workRecord.setStartDate(startDate);
			
			//時間だけを所得する
			LocalTime[] Timing = userApplicationService.getTime(workRecord);
			workRecord.setStartTiming(Timing[0]);
			workRecord.setFinishTiming(Timing[1]);
			
		}
		 //Model に 登録
		model.addAttribute("workRecord", workRecords);
		// マイページ を 表示
		return "user/myPage";
	}



}
