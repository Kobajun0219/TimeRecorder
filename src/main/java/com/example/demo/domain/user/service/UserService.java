package com.example.demo.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;

public interface UserService {
	
	/** ユーザー 登録 */
	public void signup(MUser user);
	
	/** ユーザー 取得 */
	public List <MUser>getUsers(MUser user);

	/** ユーザー 取得( 1 件) */ 
	public MUser getUserOne(String userMail);
	
	/** ユーザー 更新( 1 件) */
	public void updateUserOne(String userMail,
		String password,
		String userName);
	
	/** ユーザー 削除( 1 件) */
	public void deleteUserOne(String userId);
	
	/** ログイン ユーザー 情報 取得 */
	public MUser getLoginUser(String userId);
	
	/** 出勤時間を記録 */
	public void startRecord(Integer uId);
	
	/** 退勤時間を記録 */
	public void finishRecord(Integer uId);
	
	/** 出勤者を取得 */
	public List <WorkUser>getworkers();
	
	/** 個人の出勤記録を取得 */
	public List <WorkUser>getRecord(Integer id);
	
	/** 勤怠記録を取得 */
	public List <WorkUser>allRecord();
	
	/** 出勤判定 */
	public int checkRecord(Integer id);
	
	/** 勤退時間を更新 */
	public void UpdateRecord(int id, LocalDateTime startTime, LocalDateTime finishTime, String message);
	
	
}
