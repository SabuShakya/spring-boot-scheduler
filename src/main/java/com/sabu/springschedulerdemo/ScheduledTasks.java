package com.sabu.springschedulerdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableAsync
@Slf4j
@Component
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    // fixedRate, which specifies the interval between method invocations, measured from the start time of each invocation
    public void reportCurrentTime() {
        log.info(":::::::::::::::::::::::::SCHEDULER:::::::::::::::::::::::::");
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    @Async
    @Scheduled(fixedDelay = 5000)
    public void printIn5Seconds() throws InterruptedException {
        log.info("I am being logged in 5 seconds!");
        Thread.sleep(5000);
    }

    @Scheduled(cron = "0 15 10 15 * ?")
    // we're scheduling a task to be executed at 10:15 AM on the 15th day of every month.
    public void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }
}
