package com.dianping.puma.monitor.todo;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.LionException;

public abstract class AbstractTaskMonitor implements Runnable{

	private static final Logger LOG = LoggerFactory.getLogger(AbstractTaskMonitor.class);
	
	private long initialDelay;
	private long interval;
	private TimeUnit unit;
	@SuppressWarnings("unchecked")
	protected Future future;
	
	public AbstractTaskMonitor(long initialDelay,TimeUnit unit){
		this.initialDelay=initialDelay;
		this.unit=unit;
		init();
	}
	
	public void init(){
		doInit();
	}
	public abstract void doInit();
	
	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}

	public long getInitialDelay() {
		return initialDelay;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getInterval() {
		return interval;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public TimeUnit getUnit() {
		return unit;
	}
	
	@PostConstruct
	public void execute(){
		future = doExecute();
	}
	
	@SuppressWarnings("unchecked")
	public abstract Future doExecute();

	public void run(){
		doRun();
	};
	
	public abstract void doRun();
	
	public long getLionInterval(String intervalName) {
		long interval = 60000;
		try {
			Long temp = ConfigCache.getInstance().getLongProperty(intervalName);
			if (temp != null) {
				interval = temp.longValue();
			}
		} catch (LionException e) {
			LOG.error(e.getMessage(), e);
		}
		return interval;
	}
}