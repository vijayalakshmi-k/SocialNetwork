package com.niit.SocialNetworksBackend;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.Dao.UserDao;
import com.niit.configuration.DBConfiguartion;
import com.niit.model.User;

public class UserDaoTest {

	static UserDao userDao;
	int currentUserId;
	//String currentUser;
	
	@BeforeClass
	public static void initialize() {
		AnnotationConfigApplicationContext annotationConfigAppContext=new AnnotationConfigApplicationContext(DBConfiguartion.class);
		annotationConfigAppContext.scan("com.niit");
//		annotationConfigAppContext.refresh();
		userDao=(UserDao)annotationConfigAppContext.getBean(UserDao.class);
	 }
	
	@Test	
	public void addUserTest()
	{
		User user=new User();
		//user.setUserId(2);
		user.setFirstname("viji");
		user.setLastname("k");
		
		user.setOnline(false);
		user.setEmails("viji@xyz.com");
		user.setPassword("123");
		user.setPhonenumber
		("123456");
		user.setRole("user");
		System.out.println("create user:"+user);
		assertTrue("problem in creating user",userDao.registerUser(user));
     }
}