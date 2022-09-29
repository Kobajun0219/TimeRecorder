package com.example.demo.application.service;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.model.WorkUser;

//import com.example.demo.domain.user.model.MUser;

import lombok.extern.slf4j.Slf4j;


@Service
public class UserApplicationService {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired 
	private ResourceLoader resourceLoader;
	
	
	/** ファイル保存先 */
	private String filePath="C:\\work";
	
	/** パス区切り文字 */
	private static final String SEPARATOR= File.separator;
	
	/** 性別 の Map を 生成 する */ 
	public Map < String, Integer > getGenderMap(Locale locale) {
		Map <String, Integer> genderMap = new LinkedHashMap <>();
		String male = messageSource.getMessage("male", null, locale);
		String female = messageSource.getMessage("female",null, locale);
		
		genderMap. put( male, 1); 
		genderMap. put( female, 2);
		
		return genderMap;
	}
	
	/** 出勤退勤 の Map を 生成 する */ 
	public Map < Integer,String > getWorkMap() {
		HashMap<Integer,String> workMap = new HashMap<>();
		workMap.put(0,"退勤済");
		workMap.put(1,"出勤中"); 
		
		return workMap;
	}
	
	/** 勤務時間の計算 */ 
	public String getWorkDuration(WorkUser workUser){
		
		if(workUser.getWorkFlag() !=1) {
			LocalDateTime  finishTime = workUser.getFinishTime();
			LocalDateTime  startTime =  workUser.getStartTime();
			Duration duration = Duration.between(startTime, finishTime);
			long seconds = duration.getSeconds();
			int hour =  (int) seconds / 3600;
			int min =  (int) (seconds%3600) / 60;
			int sec =  (int) seconds % 60;		
			String duraiton = (hour+"時間"+min+"分"+sec+"秒");
			
			return duraiton;
		}
		
		String duraiton = "勤務中";
		return duraiton;
	}
	
	/** 出勤日時を生成 */ 
	public LocalDate getStartDate(WorkUser workUser){
		LocalDateTime startTime =  workUser.getStartTime();
		LocalDate startDate = LocalDate.from(startTime);		
		return startDate;
	}
	
	/** 時間を所得 */ 
	public LocalTime[] getTime(WorkUser workUser){
		LocalTime timing[] = new LocalTime[2];
		LocalDateTime startTime =  workUser.getStartTime();
		timing[0] = LocalTime.from(startTime);	
		if(workUser.getWorkFlag() !=1) {
			LocalDateTime finishTime =  workUser.getFinishTime();
			timing[1] = LocalTime.from(finishTime);	
		}else {
			timing[1] = null;	
		}
		return timing;
	}
	
	
	/** ユーザーリストをCSVに保存する */
	public void saveUserCsv(List<MUser>userList, String fileName)throws IOException {
		// CSV文字列作成
		StringBuilder sb= new StringBuilder();
		for(MUser user : userList) {
			sb.append(user.toCsv());}
		// ファイル保存先パス作成
		Path path= Paths.get(filePath+SEPARATOR+fileName);
		
		// byte配列作成
		byte[]bytes=sb.toString().getBytes();
		
		// ファイル書込
		Files.write(path,bytes);
	}
	
	/** 出勤記録をCSVに保存する */
	public void saveRecordCsv(List<WorkUser>workUser, String fileName)throws IOException {
		// CSV文字列作成
		StringBuilder sb= new StringBuilder();
		for(WorkUser workRecord : workUser) {
			sb.append(workRecord.toCsv());}
		// ファイル保存先パス作成
		Path path= Paths.get(filePath+SEPARATOR+fileName);
		
		// byte配列作成
		byte[]bytes=sb.toString().getBytes();
		
		// ファイル書込
		Files.write(path,bytes);
	}
	
	/** CSVファイル取得. */
	public byte[] getCsv(String fileName)throws IOException {
		// パス 
		String path="file:" + filePath + SEPARATOR+fileName;
		// ファイル取得
		Resource resource=resourceLoader.getResource(path);
		File file=resource.getFile();
		
		// byte配列取得
		return Files.readAllBytes(file.toPath());
	}
//	
//	/** 部署リストのCSVを作成する */
//	public void saveDepartmentCsv(List<MUser> userList,String fileName)
//		throws IOException {
//		// CSV文字列作成
//		StringBuilder sb = new StringBuilder();
//		for(MUser user:userList) {
//			sb.append(user.getDepartment().toCsv());
//		}
//		
//		// ファイル保存先パス作成
//		Path path= Paths.get(filePath+SEPARATOR+fileName);
//		
//		// byte配列作成
//		byte[]bytes=sb.toString().getBytes();
//		
//		// ファイル書込
//		Files.write(path,bytes);
//	}
//	/** InputStream取得 */
//	public InputStream getInputStream(String fileName) throws IOException {
//		// パス
//		String path="file:"+filePath+SEPARATOR+fileName;
//		// Resource取得
//		Resource resource=resourceLoader.getResource(path);
//		// InputStream取得
//		return resource.getInputStream();
//	}
}