package com.example.demo.domain.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class WorkUser {
	private int id;
	private String userName;
	private LocalDateTime   startTime;
	private LocalDateTime   finishTime;
	private int  workFlag;
	private String  duration;
	private LocalDate  startDate;
}
