package com.niit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "blogpost_s180250")
public class BlogPost {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String blogTitle;
	@Lob
	@Column(nullable = false)
	private String blogContent;
	@ManyToOne
	private User postedBy;
	private int likes;
	private boolean approved;


	public User getPostedBy() {
		return postedBy;
	}

	private Date PostedOn;

	public Date getPostedOn() {
		return PostedOn;
	}

	public void setPostedOn(Date postedOn) {
		PostedOn = postedOn;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogContent() {
		return blogContent;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public void setPostedBy(User postedBy2) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "BlogPost [id=" + id + ", blogTitle=" + blogTitle + ", blogContent=" + blogContent + ", postedBy="
				+ postedBy + ", likes=" + likes + ", approved=" + approved + ", PostedOn=" + PostedOn + "]";
	}
	
	
	
	
	
	
	
	

}
