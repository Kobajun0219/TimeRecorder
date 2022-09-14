'use strict'
{
  function sample(){
    console.log(form);
  }
}


/** 画面 ロード 時 の 処理. */

jQuery(function($){
	
	/** 更新 ボタン を 押し た とき の 処理. */
	$('#btn-update').click(function(event){
		// ユーザー 更新
		updateUser();
	});
	
	/** 削除 ボタン を 押し た とき の 処理. */
	$('#btn-delete').click(function(event){
		// ユーザー 削除
		deleteUser();
	});
});


/** 勤退チェックの処理. */
	function checkrecord(){
		// フォーム の 値 を 取得
		var formData = $('#user-detail-form').serializeArray();
		// ajax 通信
		$.ajax({
			type:"PUT",
			cache:false,
			url:'/user/update',
			data:formData,dataType:'json',
		}).done(function(data){
		// ajax 成功 時 の 処理
		alert('ユーザー を 更新 し まし た');
		// ユーザー 一覧 画面 に リダイレクト
		window.location.href ='/user/list'
		
		;}).fail(function(jqXHR,textStatus, errorThrown){
		// ajax 失敗 時 の 処理
		alert('ユーザー 更新 に 失敗 し まし た');
		}).always( function(){
		 // 常に 実行 する 処理
		});
}


/** ユーザー 更新 処理. */
	function updateUser(){
		// フォーム の 値 を 取得
		var formData = $('#user-detail-form').serializeArray();
		// ajax 通信
		$.ajax({
			type:"PUT",
			cache:false,
			url:'/user/update',
			data:formData,dataType:'json',
		}).done(function(data){
		// ajax 成功 時 の 処理
		alert('ユーザー を 更新 し まし た');
		// ユーザー 一覧 画面 に リダイレクト
		window.location.href ='/user/list'
		
		;}).fail(function(jqXHR,textStatus, errorThrown){
		// ajax 失敗 時 の 処理
		alert('ユーザー 更新 に 失敗 し まし た');
		}).always( function(){
		 // 常に 実行 する 処理
		});
}

/** ユーザー 削除 処理. */
function deleteUser() {
	
	// フォーム の 値 を 取得
	var formData = $('#user-detail-form').serializeArray();
	
	// ajax 通信
	$.ajax({
		type:"DELETE",
		cache :false,
		url:'/user/delete',
		data:formData,
		dataType:'json',
	}).done(function(data){
		// ajax 成功 時 の 処理
		alert('ユーザー を 削除 し まし た');
		// ユーザー 一覧 画面 に リダイレクト
		window.location.href = '/user/list';
		
	}).fail(function(jqXHR,textStatus,errorThrown){
		// ajax 失敗 時 の 処理
		alert('ユーザー 削除 に 失敗 し まし た');
		
		}).always( function() {
		// 常に 実行 する 処理
	});
}