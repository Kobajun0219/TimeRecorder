package com.example.demo.domain.user.model;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkUser {
//	private int id;
//	private String userName;
//	
//	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//	private LocalDateTime   startTime;
//	
//	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//	private LocalDateTime   finishTime;
//	private int  workFlag;
//	
////	@Max(value = 100)
//	@NotNull
//	private String message;
//	private String  duration;
//	private LocalDate  startDate;
//	private LocalTime  StartTiming;
//	private LocalTime  FinishTiming;
	
	private int id;
	private String userName;
	private LocalDateTime   startTime;
	private LocalDateTime   finishTime;
	private int  workFlag;
	private String message;
	private String  duration;
	private LocalDate  startDate;
	private LocalTime  StartTiming;
	private LocalTime  FinishTiming;
		
//	if(workUser.getWorkFlag() !=1) {
//		LocalDateTime  finishTime = workUser.getFinishTime();
//		LocalDateTime  startTime =  workUser.getStartTime();
//		Duration duration = Duration.between(startTime, finishTime);
//		long seconds = duration.getSeconds();
//		int hour =  (int) seconds / 3600;
//		int min =  (int) (seconds%3600) / 60;
//		int sec =  (int) seconds % 60;		
//		String duraiton = (hour+"時間"+min+"分"+sec+"秒");
//		
//	}
//	
//	String duraiton = "勤務中";
	
	
	/** CSV文字列の作成. */
	public String toCsv() {
		String workStr=null;
		if(workFlag== 1){
			workStr="勤務中";
		}else{
			workStr="退勤済";
		}
		
		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		String csv=userName+","+startTime+","+finishTime+","
		+","+message+","+workStr+"\r\n";
		return csv;
	}
}
