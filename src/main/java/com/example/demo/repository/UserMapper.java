package com.example.demo.repository;


import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;

@Mapper
public interface UserMapper {

	/** ユーザー 登録 */
	public int insertOne(MUser user);
	
	/** ユーザー 取得 */
	public List<MUser>findMany(MUser user);
	
	
	/** ユーザー 取得( 1 件) */ 
	public MUser findOne(String userMail);
	
	/** ユーザー 更新( 1 件) */ 
	public void updateOne(@Param("userMail") String userMail,
		@Param("password") String password,
		@Param("userName") String userName);
	
	
	/** ユーザー 削除( 1 件) */
	public int deleteOne(@Param("userId") String userId);
	
	/** ログイン ユーザー 取得 */
	public MUser findLoginUser(String userId);
	
	/** 出勤を記録 */
	public void startRecord(@Param("uId") Integer uId,
							@Param("nowDateTime") LocalDateTime nowDateTime);
	
	/** 出勤者を取得 */
	public List<WorkUser>getworkers();
	
	/** 出勤を記録 */
	public void finishRecord(@Param("uId") Integer uId,
							@Param("nowDateTime") LocalDateTime nowDateTime);
	
	/** 出勤チェック */
	public int checkRecord(Integer id);
	
	/** 個人の出勤記録を取得 */
	public List<WorkUser> getRecord(Integer id);
	
	/** 個人の出勤記録を取得 */
	public List<WorkUser> allRecord();
	
	/** 勤退時間を更新 */
	public void UpdateRecord(int id, LocalDateTime startTime, LocalDateTime finishTime, String message);
}

