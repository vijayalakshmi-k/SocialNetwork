package com.niit.Dao;

import java.util.List;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;

public interface BlogPostDao {
	void addBlogPost(BlogPost blogPost);

	List<BlogPost> ListOfBlogs(int approved);

	BlogPost getBlog(int id);

	void approve(BlogPost blog);

	void reject(BlogPost blog, String rejectionReason);

	void addblogComment(BlogComment blogComment);

	List<BlogComment> getAllBlogComments(int blogPostId);
}
