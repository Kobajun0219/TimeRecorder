package com.example.demo.form;

import java.time.LocalTime;

import lombok.Data;

@Data

public class RecordUpdateForm {
	private LocalTime startTime;
	private LocalTime finishTime;
	private String message;
}
