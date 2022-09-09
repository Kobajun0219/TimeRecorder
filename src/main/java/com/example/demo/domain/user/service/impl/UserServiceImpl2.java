//package com.example.demo.domain.user.service.impl;
//
//import com.example.demo.domain.user.model.MUser;
//import com.example.demo.domain.user.service.UserService;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.ExampleMatcher;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Primary
//public class UserServiceImpl2 implements UserService {
//	
//    @Autowired
//    private UserRepository repository;
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    /** ユーザー 登録 */
//    @Transactional
//    @Override
//    public void signup(MUser user) {
//        boolean exists = repository.existsById(user.getUserId());
//        if (exists) {
//            throw new DataAccessException("ユーザーが既に存在") {
//            };
//        }
//        
//        user.setDepartmentId(1);
//        user.setRole("ROLE_GENERAL");
//
//        String rawPassword = user.getPassword();
//        user.setPassword(encoder.encode(rawPassword));
//        
//        //insert
//        repository.save(user);
//    }
//    
//    /** ユーザー 取得 */
//    @Override
//    public List<MUser> getUsers(MUser user) {
//        ExampleMatcher matcher = ExampleMatcher.matching()//andの条件
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//Like句
//                .withIgnoreCase();//大文字小文字の両方
//        return repository.findAll(Example.of(user, matcher));
////    	return repository.findAll();
//    }
//    
//    /** ユーザー 取得( 1 件) */
//    @Override
//    public MUser getUserOne(String userId) {
////        return repository.findById(userId).orElse(null);
//        
//    	Optional<MUser>option = repository.findById(userId);
//    	MUser user = option.orElse(null);
//    	return user;
//    }
//
//    /** ユーザー 更新( 1 件) */
//    @Transactional
//    @Override
//    public void updateUserOne(String userId, String password, String userName) {
//    	
//    	//パスワード暗号化
//        String encryptPassword = encoder.encode(password);
//        //ユーザー更新
//        repository.updateUser(userId, password, userName);
//    }
//
//    /** ユーザー 削除( 1 件) */
//    @Transactional
//    @Override
//    public void deleteUserOne(String userId) {
//        repository.deleteById(userId);
//    }
//    
//    /** ログイン ユーザー 取得 */
//    @Override
//    public MUser getLoginUser(String userId) {
//        return repository.findLoginUser(userId);
//    }
//}
