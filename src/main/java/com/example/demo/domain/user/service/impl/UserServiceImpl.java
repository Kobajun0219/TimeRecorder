package com.example.demo.domain.user.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	private UserMapper mapper;
	
//	@Autowired 
//	private PasswordEncoder encoder;
	
	/** ユーザー 登録 */
	@Override
	public void signup(MUser user){
		user.setDepartmentId(1); // 部署s
		user.setRole("ROLE_GENERAL"); // ロール
		
//		// パスワード 暗号化
//		String rawPassword = user.getPassword();
//		user.setPassword(encoder.encode(rawPassword));
		mapper.insertOne(user);
	}
	
	/** ユーザー 取得 */
	@Override
	public List<MUser>getUsers(MUser user){
		return mapper.findMany(user);
	}
	
	/** ユーザー 取得( 1 件) */
	@Override 
	public MUser getUserOne(String userId) {
		return mapper.findOne(userId);
	}
	
	/** ユーザー 更新( 1 件) */
//	@Transactional
	@Override
	public void updateUserOne(String userId,
		String password,
		String userName){
		
		// パスワード 暗号化
//		String encryptPassword = encoder.encode(password);
		mapper.updateOne(userId,password,userName);
		
//		 例外 を 発生 さ せる
//		int i = 1/0;
	}
	
	/** ユーザー 削除( 1 件) */
	@Override
	public void deleteUserOne(String userId) {
		int count = mapper.deleteOne(userId);
	}
//	
//	/** ログイン ユーザー 情報 取得 */
//	@Override
//	public MUser getLoginUser(String userId) {
//		return mapper.findLoginUser(userId);
//	}
}
