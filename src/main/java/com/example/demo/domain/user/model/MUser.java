package com.example.demo.domain.user.model;

import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//
//import javax.persistence.Table;

import lombok.Data;

//@Entity
//@Table(name ="m_user")
@Data
public class MUser {
	private Integer id;
	private String userMail;
	private String password;
	private String userName;
	private Date birthday;
	private Integer age;
	private Integer gender;
	private Integer departmentId;
	private String role;
	private Department department;
	private List<Salary> salaryList;
	private List<WorkUser> UserTime;
	

	/** CSV文字列の作成. */
	public String toCsv() {
		String genderStr=null;
		if(gender== 1){
			genderStr="男性";
		}else{
			genderStr="女性";
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		String csv=userMail+","+userName+","+sdf.format(birthday)
		+","+age+","+genderStr+"\r\n";
		return csv;
	}
}