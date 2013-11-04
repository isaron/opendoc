package com.cloud.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		System.out.println("execute job...");
	}
}
