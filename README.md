# SCHEDULER #
To enable support scheduling tasks with @Scheduled annotation in Spring, we first have to enable it by using 
*@EnableScheduling* in our config class.
The *@EnableScheduling* annotation ensures that a background task executor is created. Without it, nothing gets scheduled.

```java
package com.sabu.springschedulerdemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {
    //......    
}
```

Then we create our scheduled tasks using *@Scheduled* annotations.

```java
package com.sabu.springschedulerdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    // fixedRate, which specifies the interval between method invocations, measured from the start time of each invocation
    public void reportCurrentTime (){
        log.info(":::::::::::::::::::::::::SCHEDULER:::::::::::::::::::::::::");
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
   
}
```

### Parallel Scheduling ###
If we want to support parallel behavior in scheduled tasks, we need to add the *@Async* annotation:

```java
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

}
```
Now this asynchronous task will be invoked each 5 seconds, even if the previous task isn't done.

### Conditional Scheduling ###

There's another way to enable scheduling - by using the @ConditionalOnProperty annotation. It allows us to "switch on" 
and "switch off" our configuration classes by setting a property in the application.properties class.

```java
@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "spring.enable.scheduling")
public class ScheduleEnabling {}
```

```properties
spring.enable.scheduling = true
```

### Cron Expression ###
Cron expressions are basically strings that describe the details of the schedule. 
It is a daemon process which runs without the need for user intervention and executes tasks.

|Name|	Required|	Allowed Values	|Allowed Special Characters|
|:---|:---|:---|:---|
|Seconds|	Yes|	0-59|	, - * / |
|Minutes|	Yes	|0-59|	, - * / |
|Hours|	Yes|	0-23|	, - * / |
|Day of Month|	Yes|	1-31|	, - * / L W C |
|Month|	Yes	|0-11 or JAN-DEC|	, - * / |
|Day of Week|	Yes |	1-7 or SUN-SAT	| , - * / L C #|
|Year	|No|	empty or 1970-2099|	, - * / |

And the format,Except for the year field, all the other fields are mandatory:
```
<second> <minute> <hour> <day-of-month> <month> <day-of-week> <year>
```
Example: 0 0 12 * * ? 2019 – This cron expression fires at 12 PM, every day of the month, for every month, 
for the year 2019.
References:

- https://www.baeldung.com/spring-scheduled-tasks
- https://stackabuse.com/scheduling-spring-boot-tasks/
- https://www.javadevjournal.com/spring-boot/spring-boot-scheduler/