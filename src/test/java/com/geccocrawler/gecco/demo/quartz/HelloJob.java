package com.geccocrawler.gecco.demo.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by riverzu on 2017/8/12.
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("job key:" + context.getJobDetail().getKey());
    }

}
