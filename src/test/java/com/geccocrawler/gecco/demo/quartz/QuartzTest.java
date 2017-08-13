package com.geccocrawler.gecco.demo.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by riverzu on 2017/8/12.
 */
public class QuartzTest {

    public static void main(String[] args) throws SchedulerException {
        System.out.println("quartzTest。。。。。");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        System.out.println("end quartzTest。。。。。");

    }
}
