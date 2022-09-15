package com.example.demo.domain.user.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.repository.UserMapper;


@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired 
	private PasswordEncoder encoder;
	
	/** ユーザー 登録 */
	@Override
	public void signup(MUser user){
		user.setDepartmentId(1); // 部署s
		user.setRole("ROLE_GENERAL"); // ロール
		
		// パスワード 暗号化
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		mapper.insertOne(user);
	}
	
	/** ユーザー 取得 */
	@Override
	public List<MUser>getUsers(MUser user){
		return mapper.findMany(user);
	}
	
	/** ユーザー 取得( 1 件) */
	@Override 
	public MUser getUserOne(String userMail) {
		return mapper.findOne(userMail);
	}
	
	/** ユーザー 更新( 1 件) */
	@Transactional
	@Override
	public void updateUserOne(String userMail,
		String password,
		String userName){
		
		// パスワード 暗号化
		String encryptPassword = encoder.encode(password);
		mapper.updateOne(userMail,encryptPassword,userName);
		
//		 例外 を 発生 さ せる
//		int i = 1/0;	
	}
	
	/** ユーザー 削除( 1 件) */
	@Override
	public void deleteUserOne(String userId) {
		int count = mapper.deleteOne(userId);
	}
	
	/** ログイン ユーザー 情報 取得 */
	@Override
	public MUser getLoginUser(String userId) {
		return mapper.findLoginUser(userId);
	}
	
	/** 出勤時間を記録 */
	@Override
	public void startRecord(Integer uId) {
		//出勤しているかをチェック
		int check = checkRecord(uId);
		if(check == 0) {
			LocalDateTime nowDateTime = LocalDateTime.now();
			mapper.startRecord(uId,nowDateTime);
		}
	}
	
	/** 出勤者を取得 */
	@Override
	public List <WorkUser>getworkers(){
		return mapper.getworkers();
	}
	
	/** 退勤時間を記録 */
	@Override
	public void finishRecord(Integer uId) {
		LocalDateTime nowDateTime = LocalDateTime.now();
		mapper.finishRecord(uId,nowDateTime);
	}
	
	/** 個人の出勤記録を取得 */
	@Override
	public List <WorkUser>getRecord(Integer id){
		return mapper.getRecord(id);
	}
		
	/** 勤怠記録を取得 */
	@Override
	public List <WorkUser>allRecord(){
		return mapper.allRecord();
	}
	
	/** 出勤判定 　0==退勤済みor未出勤　1==出勤中*/
	@Override
	public int checkRecord(Integer id){
		return mapper.checkRecord(id);
	}
}
