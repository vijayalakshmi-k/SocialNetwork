package com.niit.Dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.Notification;

@Repository
@Transactional
public class BlogPostDaoimpl implements BlogPostDao {

	@Autowired
	 SessionFactory sessionFactory;

	public void addBlogPost(BlogPost blogPost) {
		Session session = sessionFactory.getCurrentSession();
		session.save(blogPost); // insert into blogPost_s180250 values(?....)

	}
	// List of blogs waiting for approval if approved=0
	// List of blogs waiting for approval if approved=1

	public List<BlogPost> ListOfBlogs(int approved) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BlogPost where approved=" + approved);
		List<BlogPost> blogs = query.list();
		return blogs;
	}

	public BlogPost getBlog(int id) {
		Session session = sessionFactory.getCurrentSession();
		BlogPost blogPost = (BlogPost) session.get(BlogPost.class, id);
		return blogPost;
	}

	public void approve(BlogPost blog) {
		Session session = sessionFactory.getCurrentSession();
		blog.setApproved(true);
		session.update(blog); // update blogpost set approved=1 where id=?
		Notification notification = new Notification();
		notification.setBlogTitle(blog.getBlogTitle());
		notification.setApprovalStatus("Approved");
		// notification.setEmail(blog.getPostedBy().getEmail());
		session.save(notification); // insert into notification values
	}

	public void reject(BlogPost blog, String rejectionReason) {
		Session session = sessionFactory.getCurrentSession();
		Notification notification = new Notification();
		notification.setBlogTitle(blog.getBlogTitle());
		notification.setApprovalStatus("Rejected");
		// notification.setEmail(blog.getPostedBy().getEmail());
		notification.setRejectionReason(rejectionReason);
		session.save(notification); // insert into notification
		session.delete(blog); // delete from blogpost where id=?
	}

	public void addblogComment(BlogComment blogComment) {
		Session session = sessionFactory.getCurrentSession();
		session.save(blogComment); // insert into blogComment values
	}

	
	public List<BlogComment> getAllBlogComments(int blogPostId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogComment where blogPodt.id=?");
		List<BlogComment> blogComments=query.list();
		return blogComments;
	}

}
