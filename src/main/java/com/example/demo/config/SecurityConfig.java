//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
//@SuppressWarnings("deprecation")
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
//
//	/** セキュリティ の 対象 外 を 設定 */
//	@Override
//	public void configure(WebSecurity web)throws Exception {
//		// セキュリティ を 適用 し ない
//		web
//			.ignoring()
//			.antMatchers("/webjars/**")
//			.antMatchers("/css/**")
//			.antMatchers("/js/**")
//			.antMatchers("/h2-console/**");
//	}
//	
//	/** セキュリティ の 各種 設定 */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {	
//		// ログイン 不要 ページ の 設定
//		http
//		.authorizeRequests()
//			.antMatchers("/login").permitAll()//直 リンク OK
//			.antMatchers("/user/signup").permitAll()//直 リンク OK
//			.antMatchers("/user/signup/rest").permitAll()//直リンクOK
//			.antMatchers("/admin").hasAuthority("ROLE_ADMIN") // 権限 制御
//			.anyRequest().authenticated();// それ 以外 は 直 リンク NG
//		
//		// ログイン 処理
//		http
//		.formLogin()
//			.loginProcessingUrl("/login")// ログイン 処理 の パス
//			.loginPage("/login")// ログイン ページ の 指定
//			.failureUrl("/login")// ログイン 失敗 時の遷移先
//			.usernameParameter("userId")// ログインページのユーザー ID
//			.passwordParameter("password")// ログインページのパスワード
//			.defaultSuccessUrl("/user/list",true); // 成功後の遷移先
//		
//		// ログアウト 処理
//		http
//		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//		.logoutUrl("/logout")
//		.logoutSuccessUrl("/login?logout"); 
//		
////		 CSRF 対策 を 無効 に 設定（ 一時的）
////		http.csrf().disable();
//	}
//	
//	/** 認証 の 設定 */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
//		
//		PasswordEncoder encoder = passwordEncoder();
//		
////		// イン メモリ 認証
////		auth
////		.inMemoryAuthentication()
////		//インメモリのuserを定義
////		.withUser("user").password(encoder.encode("user")).roles("GENERAL")
////		.and()
////		//インメモリのadminを定義
////		  .withUser("admin").password(encoder.encode("admin")).roles("ADMIN");
//		
////		ユーザー データ で 認証
//		auth
//		 .userDetailsService(userDetailsService)
//		 .passwordEncoder(encoder);
//	}
//}


//最新のコード

package com.example.demo.config;



import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/webjars/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login
                .loginProcessingUrl("/login") // ログイン処理のパス
                .loginPage("/login") //ログインページ
                .failureUrl("/login?error") //失敗時
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/user/list", true)
        ).logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
        ).authorizeHttpRequests(authz -> authz
                .mvcMatchers("/login").permitAll() // 直リンクOK
                .mvcMatchers("/user/signup").permitAll() // 直リンクOK
                .mvcMatchers("/user/signup/rest").permitAll() // 直リンクOK
                .mvcMatchers("/admin").hasAuthority("ROLE_ADMIN") // 権限制御
                .anyRequest().authenticated()
        );
        
        // CSRF 対策 を 無効 に 設定（ 一時的）
        //http.csrf().disable();
        return http.build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        String userQuery =
                "select user_id,password,true from m_user where user_id = ?";
        String authoritiesQuery =
                "select user_id,role from m_user where user_id = ?";
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery(userQuery);
        users.setAuthoritiesByUsernameQuery(authoritiesQuery);
        return users;
    }

//　　　インメモリの設定
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder encoder = passwordEncoder();
//    	List<UserDetails> userDetailsList = new ArrayList<>();
//        userDetailsList.add(User.withUsername("user").password(encoder.encode("user")).roles("GENERAL").build());
//        userDetailsList.add(User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build());
//        return new InMemoryUserDetailsManager(userDetailsList);
//    }
}
