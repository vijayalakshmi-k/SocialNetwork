package com.niit.Controller;

import java.util.Date;
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

import com.niit.Dao.BlogPostDao;
import com.niit.Dao.UserDao;
import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@Controller
public class BlogPostController {
	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/addblogpost", method = RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost, HttpSession session) {
		String email = (String) session.getAttribute("loginId");
		if (email == null) // Not loggedin
		{
			ErrorClazz error = new ErrorClazz(5, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		blogPost.setPostedOn(new Date());
		User postedBy = userDao.getUser(email);
		blogPost.setPostedBy(postedBy);

		try {
			blogPostDao.addBlogPost(blogPost);
			return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);
		} catch (Exception e) {
			ErrorClazz error = new ErrorClazz(6, "Unable to post blog ..." + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/getblogs/{approved}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogs(@PathVariable int approved, HttpSession session) {
		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(6, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		if (approved == 0) // list of logs waiting for approval
		{

			User user = userDao.getUser(email);
			if (!user.getRole().equals("ADMIN")) {
				ErrorClazz error = new ErrorClazz(7, "Access denied...");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);

			}
		}

		List<BlogPost> blogs = blogPostDao.ListOfBlogs(approved);
		return new ResponseEntity<List<BlogPost>>(blogs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getblog/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBlog(@PathVariable int id, HttpSession session) {
		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(4, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}

		BlogPost blogPost =  blogPostDao.getBlog(id);
		return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);

	}

	@RequestMapping(value = "/approve", method = RequestMethod.PUT)
	public ResponseEntity<?> approve(@RequestBody BlogPost blog, HttpSession session) {

		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(4, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User user = userDao.getUser(email);
		if (!user.getRole().equals("ADMIN")) {
			ErrorClazz error = new ErrorClazz(7, "access denied..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		blogPostDao.approve(blog); // update blogpost set approved=1 where id=?
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/reject/{rejectionReason}", method = RequestMethod.PUT)
	public ResponseEntity<?> reject(@RequestBody BlogPost blog, @PathVariable String rejectionReason,
			HttpSession session) {
		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(5, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}

		User user = userDao.getUser(email);
		if (!user.getRole().equals("ADMIN")) {
			ErrorClazz error = new ErrorClazz(7, "access denied..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		blogPostDao.reject(blog, rejectionReason); // update blogpost set approved=1 where id=?
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@RequestMapping(value = "/addcomment", method = RequestMethod.POST)
	public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment, HttpSession session) {

		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(5, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}

		User commentedBy = userDao.getUser(email);
		blogComment.setCommentedOn(new Date());
		blogComment.setCommentBy(commentedBy);
		try {
			blogPostDao.addblogComment(blogComment);
		} catch (Exception e) {
			ErrorClazz error = new ErrorClazz(6, "Unable to post comment.." + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
	}

	@RequestMapping(value = "/blogComments/{blogPostId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogComments(@PathVariable int blogPostId, HttpSession session) {
		String email = (String) session.getAttribute("loginId");
		if (email == null) {
			ErrorClazz error = new ErrorClazz(5, "Unauthorized access..");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		List<BlogComment> blogComment = blogPostDao.getAllBlogComments(blogPostId);

		return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.OK);

	}

}
