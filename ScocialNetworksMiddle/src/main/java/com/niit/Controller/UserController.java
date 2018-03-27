package com.niit.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.Dao.UserDao;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@Controller
//@CrossOrigin(value = "http://localhost:8091")
public class UserController {
	@Autowired
	private UserDao userDao;

	public UserController() {
		System.out.println("UserController bean is created");
	}

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user) // User from frontend
	{
		// check for duplicate email
		// if email is not unique return Errorclazz object
		// if email is unique then call registerUser method
		System.out.println("registerUser in UserController:" + user); // automattically calls from tostring method in
																		// user class
		System.out.println("email:" + user.getEmails());

		if (!userDao.isEmailUnique(user.getEmails())) {
			ErrorClazz error = new ErrorClazz(1, "Email is already exist please enter new different email address");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.CONFLICT);
		}
		try {

			userDao.registerUser(user);// insert
		} catch (Exception e) {
			ErrorClazz error = new ErrorClazz(2, "Some required fields may be empty... " + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
		System.out.println(user);
		User validUser = userDao.login(user);
		System.out.println(validUser);
		if (validUser == null) // invalid credentials
		{
			ErrorClazz error = new ErrorClazz(5, "login failed.Invalid Email and password... "); // response.data
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
			// session.setAttribute("loginId",user.getEmail());
		}

		else { // valid credentials
			validUser.setOnline(true);
			userDao.update(validUser);
			session.setAttribute("loginId", user.getEmails());
			return new ResponseEntity<User>(validUser, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session) {
		String email = (String) session.getAttribute("loginId");
		if (email == null) {

			ErrorClazz error = new ErrorClazz(4, "please login..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);

		}
		User user = userDao.getUser(email);
		user.setOnline(false);// update Online Status to false
		userDao.update(user);
		session.removeAttribute("loginId");
		session.invalidate();

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session) {

		String email = (String) session.getAttribute("loginId");
		if ((email == null)) {
			ErrorClazz error = new ErrorClazz(5, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User user = userDao.getUser(email);
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user, HttpSession session) {
		String email = (String) session.getAttribute("loginId");
		if ((email == null)) // logged in,logged returns null
		{

			ErrorClazz error = new ErrorClazz(5, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		try {
			userDao.update(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {

			ErrorClazz error = new ErrorClazz(5, "unable to update user details...." + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
