package com.yang.schedule.jobs;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class TestJob extends QuartzJobBean {

	@Autowired
	private Scheduler scheduler;

	private String executeCron = "0/10 * * * * ?";

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("execute TestJob");
		for (int i = 0; i < 5; i++) {
			addNewJob("job-" + i, context);
		}
	}

	protected void addNewJob(String jobName, JobExecutionContext context) {
		try {

			JobKey jobKey = new JobKey("job-notification:" + jobName);
			JobDetail job = scheduler.getJobDetail(jobKey);
			if (job == null) {
				job = JobBuilder.newJob(FirstJob.class)
						.usingJobData("name", jobName.toLowerCase())
						.storeDurably()
						.withIdentity(jobKey).build();

				TriggerKey triggerKey = new TriggerKey("trigger-notification:" + jobName);
				Trigger trigger = TriggerBuilder.newTrigger().withDescription("Notification Trigger")
						.withIdentity(triggerKey).forJob(job).withSchedule(cronSchedule(executeCron)).build();
				context.getScheduler().scheduleJob(job, trigger);
			}

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}

}
