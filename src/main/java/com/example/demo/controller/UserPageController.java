package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.application.service.UserApplicationService;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.RecordUpdateForm;
import com.example.demo.form.SignupForm;
import com.example.demo.form.UserDetailForm;
import com.example.demo.form.WorkUserForm;
import com.example.demo.form.WorkUserf;

import lombok.extern.slf4j.Slf4j;

@Validated
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
	public String getUserList(Model model, HttpServletRequest httpServletRequest,WorkUserForm WorkUserForm) {

		// ユーザー情報を取得
		String username = httpServletRequest.getRemoteUser();
		MUser loginUser = userService.getUserOne(username);
		String userMail = loginUser.getUserMail();
		MUser user = userService.getUserOne(userMail);
		user.setPassword(null);
		// MUser を form に 変換 
		UserDetailForm form = modelMapper.map(user,UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());
		// Model に 登録
		model.addAttribute("userDetailForm", form);
		
		
		//個人の出勤記録を取得
		int id = loginUser.getId();
		List<WorkUser> workRecords = userService.getRecord(id) ;
		
		List<WorkUserf> aa = new ArrayList<WorkUserf>();
		
		WorkUserf workUserf;
		
        //勤務時間と出勤日時を追加	
		for (WorkUser workRecord: workRecords) {   
			
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

			workUserf = modelMapper.map(workRecord,WorkUserf.class);
			aa.add(workUserf);
			
		
		}
//		workuserform.setuserworklist(workrecords);
		
		
		WorkUserForm.setUserworkList(aa);
		
		 //Model に 登録
		model.addAttribute("workUserForm", WorkUserForm);
		
//		 //Model に 登録
//		model.addAttribute("workRecord", workRecords);
		
		System.out.println(aa);
		// マイページ を 表示
		return "user/myPage";
	}
	
	

	@PostMapping("/mypage/changeRecord")
	public String changeRecord(@Validated WorkUserForm form, BindingResult bindingResult, Model model,HttpServletRequest httpServletRequest) {
		
		
		// 入力 チェック
		if (bindingResult.hasErrors()) {
			return getUserList(model, httpServletRequest, form);
		}
		
		
		for(WorkUserf workUser : form.getUserworkList()){
			if(workUser.getId() != 0) {
				try {
					userService.UpdateRecord(workUser.getId(), workUser.getStartTime(), workUser.getFinishTime() ,workUser.getMessage());
				}catch(Exception e) {
					log.error("勤務時間更新でエラー",e);
				}
				
			}
		}		
		System.out.print("入ってきた値"+form);
		// マイページ を 表示
		return "redirect:/mypage";
	}



}
