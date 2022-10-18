package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.domain.user.model.WorkUser;

import lombok.Data;




@Data
public class WorkUserForm {
	
	  @Valid
	  private List<WorkUserf> userworkList;

}