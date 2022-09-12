package com.example.demo.repository;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.demo.domain.user.model.MUser;

@Mapper
public interface UserMapper {

	/** ユーザー 登録 */
	public int insertOne(MUser user);
	
	/** ユーザー 取得 */
	public List<MUser>findMany(MUser user);
	
	
	/** ユーザー 取得( 1 件) */ 
	public MUser findOne(String userId);
	
	/** ユーザー 更新( 1 件) */ 
	public void updateOne(@Param("userId") String userId,
		@Param("password") String password,
		@Param("userName") String userName);
	
	
	/** ユーザー 削除( 1 件) */
	public int deleteOne(@Param("userId") String userId);
	
	/** ログイン ユーザー 取得 */
	public MUser findLoginUser(String userId);
}

