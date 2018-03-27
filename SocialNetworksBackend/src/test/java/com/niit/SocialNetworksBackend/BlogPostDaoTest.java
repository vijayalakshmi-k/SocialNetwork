package com.niit.SocialNetworksBackend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.Dao.BlogPostDao;
import com.niit.model.BlogPost;

public class BlogPostDaoTest {

	
	
	private static AnnotationConfigApplicationContext context;
	private static BlogPostDao blogPostDao;
	private static BlogPost blogPost;
	
	@BeforeClass
	public void init() {
		context=new AnnotationConfigApplicationContext();
		context.scan("com.niit.SocialNetworksBackend");
		context.refresh();
		blogPostDao=(BlogPostDao)context.getBean("blogPostDao");
		}
	@Test
	public void  testaddBlogPost()
	{
	
		BlogPost blogPost=new BlogPost();
		blogPost.setBlogTitle("spring");
		blogPost.setBlogContent("content is not explained well");
		blogPost.setApproved(true);
		blogPost.setId(0);
       //assertEquals("successfully added blogPost inside the table",blogPostDao.addBlogPost(blogPost)); 		
		//assertTrue("Sucessfully added blogPost inside the table",blogPostDao.addBlogPost(blogPost));
		
}
}

