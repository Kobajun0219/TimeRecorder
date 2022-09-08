package com.example.demo.controller;

import java.util.Locale;
import java.util.Map;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.application.service.UserApplicationService;
//import com.example.demo.domain.user.model.MUser;
//import com.example.demo.domain.user.service.UserService;
//import com.example.demo.form.GroupOrder;
//import com.example.demo.form.SignupForm;

//import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
//@Slf4j
public class SignupController {
	
	@Autowired
	private UserApplicationService userApplicationService;
	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private ModelMapper modelMapper;
	
	/** ユーザー 登録 画面 を 表示 */
	@GetMapping("/signup")
	public String getSignup(Model model, Locale locale
//			@ModelAttribute SignupForm form
			) {
		// 性別 を 取得
		Map<String, Integer>genderMap = userApplicationService.getGenderMap(locale);
		
		model.addAttribute("genderMap", genderMap);
		
		model.addAttribute("locale", locale);
		
		// ユーザー 登録 画面 に 遷移
		return "user/signup";
		
	}
		
	/** ユーザー 登録 処理 */	
	@PostMapping("/signup")
	public String postSignup
	( Model model, Locale locale
//	  @ModelAttribute @Validated(GroupOrder.class) SignupForm form, 
//	  BindingResult bindingResult
	  )
	{
		
//		// 入力 チェック 結果 
//		if (bindingResult.hasErrors()) {
//			// NG: ユーザー 登録 画面 に 戻り ます 
//			log.info(bindingResult.toString());
//			return getSignup( model, locale, form);
//		}
//		
//		log.info(form.toString());
//		
//		// form を MUser クラス に 変換
//		MUser user = modelMapper.map(form, MUser.class);
//		
//		// ユーザー 登録
//		userService.signup(user);
		
		// ログイン 画面 に リダイレクト
		return "redirect:/login";
	}
	
//	/** データベース 関連 の 例外処理 */
//	@ExceptionHandler(DataAccessException.class)
//	public String dataAccessExceptionHandler(DataAccessException e, Model model){
//		
//		// 空文字 を セット
//		model.addAttribute("error","");
//		
//		// メッセージ を Model に 登録
//		model.addAttribute("message","SignupControllerで例外が発生しました"); 
//		
//		// HTTP の エラーコード（ 500） を Model に 登録
//		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
//		
//		return "error";
//	}
//	
//	/** その他 の 例外処理 */
//	@ExceptionHandler(Exception.class)
//	public String exceptionHandler(Exception e, Model model) {
//		
//		// 空文字 を セット
//		model.addAttribute("error","");
//		
//		// メッセージ を Model に 登録
//		model.addAttribute("message","SignupControllerで例外が発生しました");
//		
//		// HTTP の エラーコード（ 500） を Model に 登録
//		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
//		
//		return "error";
//	}
}
