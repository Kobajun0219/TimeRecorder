package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.UserListForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserListController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;



	/** 最初の画面 を 表示 */

	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form, 
			Model model, HttpServletRequest httpServletRequest, Principal principal) {

		// 出勤者一覧を取得
		List<WorkUser> workerList = userService.getworkers();
		model.addAttribute("workerList", workerList);
		
	
		// ユーザーIDをmodelに登録
		String username = httpServletRequest.getRemoteUser();
		MUser loginUser = userService.getUserOne(username);//IDでの値の取得がひつよう
		loginUser.setPassword(null);
		model.addAttribute("loginUserId", loginUser.getId());

		// 出勤判定 0==退勤済みor未出勤 1==出勤中
		int i = userService.checkRecord(loginUser.getId());

		model.addAttribute("checkRecord", i);
		System.out.println(i);
		// ユーザー 一覧 画面 を 表示
		return "user/list";
	}

	/** ユーザー出勤処理 */
	@GetMapping("/sRecord/{userId:.+}")
	public String start(@PathVariable("userId") int uId) {

		userService.startRecord(uId);
		// ユーザー 一覧 画面 を 表示
		return "redirect:/user/list";
	}

	/** ユーザー退勤 */
	@GetMapping("/fRecord/{userId:.+}")
	public String finish(@PathVariable("userId") int uId) {

		userService.finishRecord(uId);
		// ユーザー 一覧 画面 を 表示
		return "redirect:/user/list";
	}


//	
//	/** zipファイルダウンロード処理 */
//	@PostMapping("/list/download/zip")
//	public void downloadZip(@ModelAttribute UserListForm form,
//			HttpServletResponse response) throws IOException {
//		
//		// formをMUserクラスに変換
//		MUser user= modelMapper.map(form, MUser.class);
//		
//		// ユーザー検索
//		List<MUser> userList= userService.getUsers(user);
//		List<String> fileNameList = new ArrayList<>();
//		
//		// ユーザーCSVファイル保存
//		String userFileName="user.csv";
//		appService.saveUserCsv(userList,userFileName);
//		fileNameList.add(userFileName);
//
//		// 部署CSVファイル保存
//		String departmentFileName="department.csv";
//		appService.saveDepartmentCsv(userList,departmentFileName);
//		fileNameList.add(departmentFileName);
//		
//		// ヘッダー設定
//		String zipFileName="sample.zip";
//		response.setHeader(HttpHeaders.CONTENT_TYPE,
//				MediaType.APPLICATION_OCTET_STREAM_VALUE);
//		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
//				"attachment; filename="+zipFileName);
//		
//		// zipファイルダウンロード
//		try(ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
//				for(String fileName:fileNameList) {
//					try(InputStream is= appService.getInputStream(fileName)) {
//						// zipファイルに格納
//						zos.putNextEntry(new ZipEntry(fileName));
//						StreamUtils.copy(is,zos);
//					}
//				 }
//			}catch(Exception e) {
//				log.error(e.getMessage(),e);
//		}
//	}
}
