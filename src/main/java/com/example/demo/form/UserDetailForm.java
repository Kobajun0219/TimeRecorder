package com.example.demo.form;

import java.util.Date;
import java.util.List;
import com.example.demo.domain.user.model.Department;
import com.example.demo.domain.user.model.Salary;
import com.example.demo.domain.user.model.WorkUser;

import lombok.Data;


@Data
public class UserDetailForm {
	private String userMail;
	private String password;
	private String userName;
	private Date birthday;
	private Integer age;
	private Integer gender;
	private Department department;
	private List<Salary> salaryList;
	private List<WorkUser> UserTime;	
}
