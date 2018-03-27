package com.niit.model;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
public class BlogPostLikes {
	@Id
	private int id;
	@ManyToOne
	private BlogPost blogPost;
	@ManyToOne
	private User user ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BlogPost getBlogPost() {
		return blogPost;
	}
	public void setBlogPost(BlogPost blogPost) {
		this.blogPost = blogPost;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BlogPostLikes hasUserLikedBlog(int blogId, String email) {
		// TODO Auto-generated method stub
		return null;
	}
	public BlogPost updateLikes(int id2, String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
