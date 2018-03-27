package com.niit.Dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.User;

@Repository("userDao")
@Transactional
public class UserDaoimpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	private UserDaoimpl() {
		System.out.println("UserDaoimpl been is created");
	}

		public boolean registerUser(User user) {                         //void
		System.out.println("registerUser in Dao" + user);
		
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		// inser into the front controller fn,ln ,emai ,password
		return true;                                                          //no return statement
	}

	public boolean isEmailUnique(String email) {

		boolean b;
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, email);
		if (user == null)

			b = true;
		else
			b = false;
		return b;

	}

	
	public User login(User user) 
	{
		Session session = sessionFactory.getCurrentSession();
		// parameter position are // 0(string) 1(User)
		Query query = (Query) session.createQuery("from User where emails=? and password=?");
		query.setString(0, user.getEmails());
		query.setString(1, user.getPassword());
		return (User) query.uniqueResult(); // 1 object or null value
		// 1 object for valid credentials
		// null for invalid credentials
	}

	
	public void update(User validUser) {
		Session session = sessionFactory.getCurrentSession();
		session.update(validUser);
	}

	public User getUser(String emails) {

		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, emails);
		return user;
	}


	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
            session.update(user);    /// update User_s180250 are where email=??...
		
	}

	
	
	
	
	
}
