package com.yang.schedule.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class FirstJob extends QuartzJobBean{
	
	@Setter
	private String name;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("execute FirstJob: "+ name);
		
	}

}
