package com.sertecimedco.ruido;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MainQuartz {
	public static void main( String[] args ) throws Exception {
		Run.cargaParametros();
    	JobDetail job = JobBuilder.newJob(RunJob.class)
    			.withIdentity("Job de ruido")
    			.build();
    	
    	Trigger trigger = TriggerBuilder.newTrigger()
    			.withIdentity("Trigger de ruido")
				.withSchedule(CronScheduleBuilder
				.cronSchedule(Run.FRECUENCIA))
				.build();
    	
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);
    }
}
