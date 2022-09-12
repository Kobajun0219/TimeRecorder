package com.example.demo.domain.user.service;

import java.util.List;
import com.example.demo.domain.user.model.MUser;

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
	
//	/** ログイン ユーザー 情報 取得 */
//	public MUser getLoginUser(String userId);
}
