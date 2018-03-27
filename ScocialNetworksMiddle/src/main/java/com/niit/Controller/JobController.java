package com.niit.Controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.Dao.JobDao;
import com.niit.Dao.UserDao;
import com.niit.model.ErrorClazz;
import com.niit.model.Job;
import com.niit.model.User;

@Controller
public class JobController {
	@Autowired
	private UserDao userDao;
	@Autowired
	private JobDao jobDao;
	
	@RequestMapping(value = "/addjob", method = RequestMethod.POST)
 public ResponseEntity<?> addJob(@RequestBody Job job, HttpSession session) {
		// Authentication
		//System.out.println(job.getJobTitle());
		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(4, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);

		}

		// User is Authenticated
		User user = userDao.getUser(email);
		System.out.println("job controller:"+user);
		if (!user.getRole().equals("ADMIN")) { // if role is not an admin,authorized to post job...

			ErrorClazz error = new ErrorClazz(5, "access denied..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		try {
			job.setPostedOn(new Date(0));
			System.out.println("job controller:"+job);
			jobDao.add(job);
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		} catch (Exception e) {
			ErrorClazz error = new ErrorClazz(6, "Unable to post job details..." + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@RequestMapping(value = "/alljobs", method = RequestMethod.GET)   
	public ResponseEntity<?> getAllJobs(HttpSession session) {
		// Authentication
		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(4, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);

		}
		List<Job> jobs = jobDao.getAllJobs();
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);

	}

	@RequestMapping(value="/getjob/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getJob(@PathVariable int id,HttpSession session)
	{
		//Authentication
		String email =(String)session.getAttribute("loginId"); 
		if(email==null) {
			ErrorClazz error=new ErrorClazz(4,"Unauthorized access..");
			return new ResponseEntity<ErrorClazz> (error,HttpStatus.UNAUTHORIZED);

	}
Job job=jobDao.getJob(id);
return new ResponseEntity<Job> (job,HttpStatus.OK);
}

}