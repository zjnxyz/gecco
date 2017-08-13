package com.geccocrawler.gecco.spider;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.scheduler.Scheduler;
import com.geccocrawler.gecco.scheduler.UniqueSpiderScheduler;
import com.geccocrawler.gecco.seed.Seed;
import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by riverzu on 2017/8/13.
 * <p>
 * 爬虫job
 */
public class SpiderJob implements Job {

    private static Log log = LogFactory.getLog(GeccoEngine.class);

    /**
     * 爬虫引擎上下文
     */
    private GeccoEngine engine;

    /**
     * 爬虫的种子数据
     */
    private Seed seed;

    private List<Spider> spiders;

    private CountDownLatch cdl;

    private String identity;

    /**
     * 调度的种子队列
     */
    private Scheduler spiderScheduler;

    public SpiderJob() {
        this.spiderScheduler = new UniqueSpiderScheduler();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        identity = jobDetail.getKey().getName();
        log.info("start craw job, key:" + identity);
        //初始化个种子进入队列
        spiderScheduler.into(seed.toRequest());

        //维护线程爬取队列
        spiders = Lists.newArrayListWithExpectedSize(seed.getThreadNum());
        cdl = new CountDownLatch(seed.getThreadNum());
        IntStream.range(0, seed.getThreadNum()).forEach(t -> {
            Spider spider = new Spider(engine, this);
            spiders.add(spider);
            //线程启动
            Thread thread = new Thread(spider, identity + "_" + t);
            thread.start();
            //休眠下,缓慢启动任务
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.warn("线程异常，key:" + identity, e);
            }
        });
        closeUnitlComplete();
    }

    public GeccoEngine getEngine() {
        return engine;
    }

    public void setEngine(GeccoEngine engine) {
        this.engine = engine;
    }

    public Seed getSeed() {
        return seed;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }

    public Scheduler getSpiderScheduler() {
        return spiderScheduler;
    }


    /**
     * spider线程告知engine执行结束
     */
    public void notifyComplete() {
        this.cdl.countDown();
    }

    /**
     * 关闭线程任务
     */
    private void closeUnitlComplete() {
        try {
            cdl.await();
        } catch (InterruptedException e) {
            log.warn("线程同步失败，key:" + identity, e);
        }
        //清理线程
        spiders.forEach(Spider::stop);
    }
}
