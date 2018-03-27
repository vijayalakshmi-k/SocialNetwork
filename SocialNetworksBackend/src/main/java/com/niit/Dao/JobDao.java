package com.niit.Dao;

import java.util.List;

import com.niit.model.Job;

public interface JobDao {

	void add(Job job);

	List<Job> getAllJobs();

	Job getJob(int id);
}
