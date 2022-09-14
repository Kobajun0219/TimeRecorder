package com.example.demo.domain.user.service;

import java.util.List;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;

public interface UserService {
	
	/** ユーザー 登録 */
	public void signup(MUser user);
	
	/** ユーザー 取得 */
	public List <MUser>getUsers(MUser user);

	/** ユーザー 取得( 1 件) */ 
	public MUser getUserOne(String userId);
	
	/** ユーザー 更新( 1 件) */
	public void updateUserOne(String userId,
		String password,
		String userName);
	
	/** ユーザー 削除( 1 件) */
	public void deleteUserOne(String userId);
	
	/** ログイン ユーザー 情報 取得 */
	public MUser getLoginUser(String userId);
	
	/** 出勤時間を記録 */
	public void startRecord(String userId);
	
	/** 出勤者を取得 */
	public List <WorkUser>getworkers();
	
	/** 退勤時間を記録 */
	public void finishRecord(String userId);
	
	/** 個人の出勤記録を取得 */
	public List <WorkUser>getRecord(Integer id);
	
	/** 勤怠記録を取得 */
	public List <WorkUser>allRecord();
	
	/** 出勤判定 */
	public int checkRecord(String userId);
}
