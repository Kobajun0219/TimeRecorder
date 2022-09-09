//package com.example.demo.domain.user.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.domain.user.model.MUser;
//import com.example.demo.domain.user.service.UserService;
//
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService{
//
//	@Autowired 
//	private UserService service;
//	
//	@Override 
//	public UserDetails loadUserByUsername(String username)
//		throws UsernameNotFoundException{
//		
//		// ユーザー 情報 取得
//		MUser loginUser = service.getLoginUser(username);
//		
//		// ユーザー が 存在 し ない 場合
//		if (loginUser == null) {
//			throw new UsernameNotFoundException("user not found");
//		}
//		
//		// 権限 List 作成
//		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole());
//		List<GrantedAuthority>authorities = new ArrayList<>();
//		authorities.add(authority);
//		
//		// UserDetails 生成
//		UserDetails userDetails =(UserDetails) new User(loginUser.getUserId(),
//			loginUser.getPassword(),
//			authorities);
//		
//		return userDetails;
//	}
//}
