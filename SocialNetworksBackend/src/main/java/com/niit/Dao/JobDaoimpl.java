package com.niit.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Job;

@Repository("jobDao")
@Transactional
public class JobDaoimpl implements JobDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void add(Job job) {

		Session session = sessionFactory.getCurrentSession();
		System.out.println("jobdaoimpl:"+job);
		session.save(job);
	}

	public List<Job> getAllJobs() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Job");
		return query.list();

	}

	public Job getJob(int id) {
		Session session = sessionFactory.getCurrentSession();
		Job job = (Job) session.get(Job.class, id);   //select * from job s180250
		return job;
	}
}
