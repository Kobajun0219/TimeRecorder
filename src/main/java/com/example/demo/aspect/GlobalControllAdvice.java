package com.example.demo.aspect;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllAdvice {

	/** データベース 関連 の 例外処理 */
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {

		// 空文字 を セット
		model.addAttribute("error", "");

		// メッセージ を Model に 登録
		model.addAttribute("message", "DataAccessException が発生しました");

		// HTTP の エラーコード（ 500） を Model に 登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

	/** その他 の 例外処理 */
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {

		// 空文字 を セット
		model.addAttribute("error", "");

		// メッセージ を Model に 登録
		model.addAttribute("message", "Eceptionで例外が発生しました");

		// HTTP の エラーコード（ 500） を Model に 登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}
}
