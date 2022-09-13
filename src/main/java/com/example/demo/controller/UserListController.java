package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.application.service.UserApplicationService;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.RecordForm;
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

	@Autowired
	private UserApplicationService appService;

	/** ユーザー 一覧 画面 を 表示 */

	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form,Model model, HttpServletRequest httpServletRequest) {

		// form を MUser クラス に 変換
		MUser user = modelMapper.map(form, MUser.class);

		// ユーザー 検索
		List<MUser> userList = userService.getUsers(user);
		System.out.println("ユーザー："+userList);
		
		//出勤者一覧を取得
		List<WorkUser> workerList = userService.getworkers();
		System.out.println("出勤者："+workerList);
		model.addAttribute("workerList", workerList);	

		// ユーザーIDをmodelに登録
		String username = httpServletRequest.getRemoteUser();
		MUser loginUser = userService.getUserOne(username);
		loginUser.setPassword(null);
		model.addAttribute("loginUserId", loginUser.getId());	
		
		// Model に 登録
		model.addAttribute("userList", userList);
		

		// ユーザー 一覧 画面 を 表示
		return "user/list";
	}

	/** ユーザー検索処理 */
	@PostMapping("/list")
	public String postUserList(@ModelAttribute UserListForm form, Model model) {
		// form を MUser クラス に 変換
		MUser user = modelMapper.map(form, MUser.class);

		// ユーザー 検索
		List<MUser> userList = userService.getUsers(user);

		// Model に 登録
		model.addAttribute("userList", userList);

		// ユーザー 一覧 画面 を 表示
		return "user/list";
	}

	/** ユーザー出勤処理 */
	@GetMapping("/sRecord/{userId:.+}")
	public String start(@PathVariable("userId") String userId) {
		
		userService.startRecord(userId);
		// ユーザー 一覧 画面 を 表示
		return "redirect:/user/list";
	}
	
	/** ユーザー退勤 */
	@GetMapping("/fRecord/{userId:.+}")
	public String finish(@PathVariable("userId") String userId) {
		
		userService.finishRecord(userId);
		// ユーザー 一覧 画面 を 表示
		return "redirect:/user/list";
	}


//	/** ユーザー一覧ダウンロード処理 */
//	@PostMapping("/list/download")
//	public ResponseEntity<byte[]> downloadUserList(@ModelAttribute UserListForm form)
//		throws IOException {
//		
//		// formをMUserクラスに変換
//		MUser user=modelMapper.map(form, MUser.class);
//		
//		// ユーザー検索
//		List<MUser> userList=userService.getUsers(user);
//		
//		// CSVファイル保存
//		String fileName="user.csv";
//		appService.saveUserCsv(userList,fileName);
//		
//		// CSVファイル取得
//		byte[] bytes=appService.getCsv(fileName);
//		
//		HttpHeaders header= new HttpHeaders();
//		
//		// HTTPヘッダーの設定
//		header.add("Content-Type", MediaType.ALL_VALUE+"; charset=utf-8");
//		header.setContentDispositionFormData("filename",fileName);
//		
//		return new ResponseEntity<>(bytes,header, HttpStatus.OK);
//	}
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
