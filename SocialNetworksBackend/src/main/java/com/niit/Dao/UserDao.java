package com.niit.Dao;

import com.niit.model.User;

public interface UserDao {
	boolean registerUser(User user);

	boolean isEmailUnique(String email);

	User login(User user); // return either 1 user object or null value

	void update(User validUser);

	User getUser(String emails);

	void updateUser(User user);
}
