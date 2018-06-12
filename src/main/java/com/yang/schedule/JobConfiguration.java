package com.yang.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import com.yang.schedule.jobs.TestJob;

@Configuration
public class JobConfiguration {
	private String testJobCron = "0 0/1 * * * ?";

	@Bean(name = "testJobDetail")
	public JobDetailFactoryBean batchSyncSettingsDetail() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(TestJob.class);
		jobDetailFactory.setDescription("Invoke Sample Job service...");
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean(name = "testJobTrigger")
	public CronTriggerFactoryBean batchSyncSettingsTrigger() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(batchSyncSettingsDetail().getObject());
		factoryBean.setCronExpression(testJobCron);
		return factoryBean;
	}
}
