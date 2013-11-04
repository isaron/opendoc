package com.cloud.job;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleRunner {

	public static void main(String[] args) throws SchedulerException {
		
		JobDetail jobDetail = new JobDetail("job_name", "job_group", SimpleJob.class);
		
		SimpleTrigger trigger = new SimpleTrigger("trigger_name", "trigger_group");
		trigger.setStartTime(new Date());
		trigger.setRepeatInterval(2000);
		trigger.setRepeatCount(10);
		
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}
}
