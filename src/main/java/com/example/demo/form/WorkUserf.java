package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
public class WorkUserf {
//	private int id;
//	private String userName;
//	private LocalDateTime   startTime;
//	private LocalDateTime   finishTime;
//	private int  workFlag;
//	private String message;
//	private String  duration;
//	private LocalDate  startDate;
//	private LocalTime  StartTiming;
//	private LocalTime  FinishTiming;
	
	private int id;
	private String userName;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime   startTime;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime   finishTime;
	private int  workFlag;
	
//	@Max(value = 100)
	private String message;
	private String  duration;
	private LocalDate  startDate;
	private LocalTime  StartTiming;
	private LocalTime  FinishTiming;	
}
