package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	@Autowired
	private UserApplicationService appService;
	
   @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 未入力のStringをnullに設定する
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
		
	/** アド ミン 権限 専用 画面 に 遷移 */
	@GetMapping("/admin")
	public String getAdmin(@ModelAttribute UserListForm form,Model model) {
		
		System.out.println(form);
		
		// form を MUser クラス に 変換
		MUser user = modelMapper.map(form, MUser.class);
		user.setPassword(null);
		
		System.out.println(user);
		// ユーザー 検索
		List<MUser> userList = userService.getUsers(user);
		
	
		// Model に 登録
		model.addAttribute("userList", userList);
		
		//出退勤レコードを取得
		List<WorkUser> allRecords = userService.allRecord();
		
	      //勤務時間と出勤日時を追加	
			for (WorkUser allRecord : allRecords) {   
				
				//勤務時間追加
				String duration = userApplicationService.getWorkDuration(allRecord);
				allRecord.setDuration(duration);
				
				//出勤日時を生成
				LocalDate startDate = userApplicationService.getStartDate(allRecord);
				allRecord.setStartDate(startDate);
				
				//時間だけを所得する
				LocalTime[] Timing = userApplicationService.getTime(allRecord);
				allRecord.setStartTiming(Timing[0]);
				allRecord.setFinishTiming(Timing[1]);
			}
		
		// Model に 登録
		System.out.println(allRecords);
		model.addAttribute("allRecord", allRecords);
		
		Map<Integer,String>workMap = userApplicationService.getWorkMap();
		
		model.addAttribute("workMap", workMap);
		
		return "admin/admin";
	}
	
	
	/** ユーザー検索処理 */
	@PostMapping("/admin")
	public String postUserList(@ModelAttribute UserListForm form, Model model) {
		
		// form を MUser クラス に 変換
		MUser user = modelMapper.map(form, MUser.class);

		// ユーザー 検索
		List<MUser> userLists = userService.getUsers(user);
		// Model に 登録
		model.addAttribute("userList", userLists);
		
		//検索したユーザーの出退勤レコードを取得
		List<WorkUser> allRecords =new ArrayList<WorkUser>();
				
		for(MUser userList:userLists) {
			allRecords = userService.getRecord(userList.getId());
		}
		
		
      //勤務時間と出勤日時を追加	
		for (WorkUser allRecord : allRecords) {   
			
			//勤務時間追加
			String duration = userApplicationService.getWorkDuration(allRecord);
			allRecord.setDuration(duration);
			
			//出勤日時を生成
			LocalDate startDate = userApplicationService.getStartDate(allRecord);
			allRecord.setStartDate(startDate);
			
			//時間だけを所得する
			LocalTime[] Timing = userApplicationService.getTime(allRecord);
			allRecord.setStartTiming(Timing[0]);
			allRecord.setFinishTiming(Timing[1]);
		}
		
		// Model に 登録
		System.out.println(allRecords);
		model.addAttribute("allRecord", allRecords);
		
		Map<Integer,String>workMap = userApplicationService.getWorkMap();
		model.addAttribute("workMap", workMap);

		// ユーザー 一覧 画面 を 表示
		return "admin/admin";
	}
	
	
	/** ユーザー一覧ダウンロード処理 */
	@PostMapping("/admin/userList/download")
	public ResponseEntity<byte[]> downloadUserList(@ModelAttribute UserListForm form) throws IOException {
		System.out.println(form);
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		System.out.println(user);

		// ユーザー検索
		List<MUser> userList = userService.getUsers(user);

		// CSVファイル保存
		String fileName = "user.csv";
		appService.saveUserCsv(userList, fileName);

		// CSVファイル取得
		byte[] bytes = appService.getCsv(fileName);

		HttpHeaders header = new HttpHeaders();

		// HTTPヘッダーの設定
		header.add("Content-Type", MediaType.ALL_VALUE + "; charset=utf-8");
		header.setContentDispositionFormData("filename", fileName);

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}
	
	/** 勤務記録一覧ダウンロード処理 */
	@PostMapping("/admin/workRecord/download")
	public ResponseEntity<byte[]> downloadWorkRecordList(@ModelAttribute UserListForm form) throws IOException {

		//出退勤レコードを取得
		List<WorkUser> WorkUser = userService.allRecord();
		
		// CSVファイル保存
		String fileName = "userRecord.csv";
		appService.saveRecordCsv(WorkUser, fileName);

		// CSVファイル取得
		byte[] bytes = appService.getCsv(fileName);

		HttpHeaders header = new HttpHeaders();

		// HTTPヘッダーの設定
		header.add("Content-Type", MediaType.ALL_VALUE + "; charset=utf-8");
		header.setContentDispositionFormData("filename", fileName);

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}
}
