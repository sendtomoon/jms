package com.jy.task;

import javax.inject.Inject;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
/**
 * 2017/03/21
 * @author lisongbai
 * 解决job中无法注入bean的问题
 */
public class JobSpringFactory extends AdaptableJobFactory {
	@Inject
	private AutowireCapableBeanFactory capableBeanFactory;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
	    Object jobInstance = super.createJobInstance(bundle);
	    capableBeanFactory.autowireBean(jobInstance);
	    return jobInstance;
	}
}
