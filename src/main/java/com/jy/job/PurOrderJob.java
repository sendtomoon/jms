package com.jy.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jy.entity.task.base.ScheduleJob;
import com.jy.task.job.weixn.SyncFollowerJob;
import com.jy.task.utils.ScheduleUtils;
import com.jy.task.utils.TaskLogUtil;

public class PurOrderJob implements Job {
	 /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(SyncFollowerJob.class);
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		ScheduleJob scheduleJob= (ScheduleJob)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY); 
		String jobName=scheduleJob.getJobName();
		String jobGroup=scheduleJob.getJobGroup();
		String jobClass=scheduleJob.getJobClass();
		LOG.info("任务[" + jobName + "]成功运行");
		try {
//			ApplicationContext ac = SpringWebContextUtil.getApplicationContext();
//			OrderInterimService service = (OrderInterimService) ac.getBean("OrderInterimService");
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "B2B商城导入任务正常运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}
	}
}
