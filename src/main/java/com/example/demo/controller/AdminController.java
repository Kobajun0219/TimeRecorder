package com.example.demo.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.application.service.UserApplicationService;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.UserListForm;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserApplicationService userApplicationService;
	
	/** アド ミン 権限 専用 画面 に 遷移 */
	@GetMapping("/admin")
	public String getAdmin(@ModelAttribute UserListForm form,Model model) {
		
		// form を MUser クラス に 変換
		MUser user = modelMapper.map(form, MUser.class);
		user.setPassword(null);
		// ユーザー 検索
		List<MUser> userList = userService.getUsers(user);

		// Model に 登録
		model.addAttribute("userList", userList);
		
		//出退勤レコードを取得
		List<WorkUser> allRecord = userService.allRecord();
		
        //勤務時間と出勤日時を追加	
		for (int i = 0; i < allRecord.size(); i++) {   
			
			//勤務時間追加
			String duration = userApplicationService.getWorkDuration(allRecord.get(i));
			allRecord.get(i).setDuration(duration);
			
			//出勤日時を生成
			LocalDate startDate = userApplicationService.getStartDate(allRecord.get(i));
			allRecord.get(i).setStartDate(startDate);
		}
		
		// Model に 登録
		System.out.println(allRecord);
		model.addAttribute("allRecord", allRecord);
		
		Map<Integer,String>workMap = userApplicationService.getWorkMap();
		
		model.addAttribute("workMap", workMap);
		
		return "admin/admin";
	}
}
