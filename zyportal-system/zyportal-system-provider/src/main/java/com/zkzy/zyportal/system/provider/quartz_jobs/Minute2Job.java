package com.zkzy.zyportal.system.provider.quartz_jobs;

import org.quartz.DisallowConcurrentExecution;

/**
 * StatefulJob: 串行执行,已废弃,替换   @DisallowConcurrentExecution
 */

@DisallowConcurrentExecution
public class Minute2Job extends BaseJob {
//public class Minute2Job implements Job {
//public class Minute2Job implements StatefulJob {

	//@Override
	//public void execute(JobExecutionContext context) throws JobExecutionException {
	//	System.out.println("Minute2Job正在执行"+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss "));
	//}

}
