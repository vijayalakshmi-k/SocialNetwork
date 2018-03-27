package com.niit.Dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.User;

@Repository
@Transactional
public class BlogPostLikesDaoImpl implements BlogPostLikesDao {
@Autowired
private SessionFactory sessionFactory;

public BlogPostLikes hasUserLikedBlog(int blogId, String email)
{
	Session session=sessionFactory.getCurrentSession();
	//select*from blogpostlikes_s180250 where blogpost id=? and email=?
	Query query=session.createQuery("From BlogPostLikes where blogPost.id=? and user.email=?");
	query.setInteger(0, blogId);
	query.setString(1, email);
	//if blog is not liked by user,query retrns null;
	//if blog liked the user ,query return 1 blogpostlikes object
	BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
	return blogPostLikes;
	}



public BlogPostLikes updateLikes(int id, String email) {
	Session session=sessionFactory.getCurrentSession();
	BlogPostLikes blogPostLikes=hasUserLikedBlog(id,email);
	BlogPost blogPost=(BlogPost)session.get(BlogPost.class, id);
	if(blogPostLikes==null) {//-insert-likes
		blogPostLikes=new BlogPostLikes();
		User user=(User)session.get(User.class, email);
	blogPostLikes.setBlogPost(blogPost);
	blogPostLikes.setUser(user);
	session.save(blogPostLikes);
	blogPost.setLikes(blogPost.getLikes()+1);
	session.update(blogPost);
	}else {
		//delete-unliked
		session.delete(blogPostLikes);
		blogPost.setLikes(blogPost.getLikes()-1);
		session.update(blogPost);
		
		
	}
	return blogPostLikes;

}
}
