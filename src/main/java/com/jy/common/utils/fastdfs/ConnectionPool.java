package com.jy.common.utils.fastdfs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionPool {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

	private static String configFile = "/fdfs_client.conf";

	private LinkedBlockingQueue<TrackerServer> idleConnectionPool = null;

	private long minPoolSize = 10L;

	private long maxPoolSize = 30L;

	private volatile long nowPoolSize = 0L;

	private long waitTimes = 200L;
	private static final int COUNT = 1;

	static {
		try {
			String classPath = new File(ConnectionPool.class.getResource("/").getFile()).getCanonicalPath();
			configFile = classPath + File.separator + configFile;
		} catch (Exception e) {
			logger.error("获取根路径出错", e);
		}
	}

	public ConnectionPool(long minPoolSize, long maxPoolSize, long waitTimes) {
		String logId = UUID.randomUUID().toString();
		logger.info("[线程池构造方法(ConnectionPool)][" + logId + "][默认参数：minPoolSize=" + minPoolSize + ",maxPoolSize="
				+ maxPoolSize + ",waitTimes=" + waitTimes + "]");
		this.minPoolSize = minPoolSize;
		this.maxPoolSize = maxPoolSize;
		this.waitTimes = waitTimes;

		poolInit(logId);

		HeartBeat beat = new HeartBeat(this);
		beat.beat();
	}

	private void poolInit(String logId) {
		try {
			initClientGlobal();

			this.idleConnectionPool = new LinkedBlockingQueue();

			for (int i = 0; i < this.minPoolSize; i++)
				createTrackerServer(logId, 1);
		} catch (Exception e) {
			logger.error("[FASTDFS初始化(init)--异常][" + logId + "][异常：{}]", e);
		}
	}

	// ERROR //
	public void createTrackerServer(String logId, int flag) {
	}

	public TrackerServer checkout(String logId) {
		logger.info("[获取空闲连接(checkout)][" + logId + "]");
		TrackerServer trackerServer = (TrackerServer) this.idleConnectionPool.poll();
		if (trackerServer == null) {
			if (this.nowPoolSize < this.maxPoolSize) {
				createTrackerServer(logId, 1);
				try {
					trackerServer = (TrackerServer) this.idleConnectionPool.poll(this.waitTimes, TimeUnit.SECONDS);
				} catch (Exception e) {
					logger.error("[获取空闲连接(checkout)-error][" + logId + "][error:获取连接超时:{}]", e);
				}
			}
			if (trackerServer == null) {
				logger.error("[获取空闲连接(checkout)-error][" + logId + "][error:获取连接超时（" + this.waitTimes + "s）]");
			}
		}
		logger.info("[获取空闲连接(checkout)][" + logId + "][获取空闲连接成功]");
		return trackerServer;
	}

	public void checkin(TrackerServer trackerServer, String logId) {
		logger.info("[释放当前连接(checkin)][" + logId + "][prams:" + trackerServer + "] ");
		if (trackerServer != null)
			if (this.idleConnectionPool.size() < this.minPoolSize)
				this.idleConnectionPool.add(trackerServer);
			else
				synchronized (this) {
					if (this.nowPoolSize != 0L)
						this.nowPoolSize -= 1L;
				}
	}

	public void drop(TrackerServer trackerServer, String logId) {
		logger.info("[删除不可用连接方法(drop)][" + logId + "][parms:" + trackerServer + "] ");
		if (trackerServer != null)
			try {
				synchronized (this) {
					if (this.nowPoolSize != 0L) {
						this.nowPoolSize -= 1L;
					}
				}
				trackerServer.close();
			} catch (IOException e) {
				logger.info("[删除不可用连接方法(drop)--关闭trackerServer异常][" + logId + "][异常：{}]", e);
			}
	}

	private void initClientGlobal() throws Exception {
		ClientGlobal.init(configFile);
		logger.info("FastDfs初始化完成:" + configFile);
	}

	public LinkedBlockingQueue<TrackerServer> getIdleConnectionPool() {
		return this.idleConnectionPool;
	}

	public long getMinPoolSize() {
		return this.minPoolSize;
	}

	public void setMinPoolSize(long minPoolSize) {
		if (minPoolSize != 0L)
			this.minPoolSize = minPoolSize;
	}

	public long getMaxPoolSize() {
		return this.maxPoolSize;
	}

	public void setMaxPoolSize(long maxPoolSize) {
		if (maxPoolSize != 0L)
			this.maxPoolSize = maxPoolSize;
	}

	public long getWaitTimes() {
		return this.waitTimes;
	}

	public void setWaitTimes(int waitTimes) {
		if (waitTimes != 0)
			this.waitTimes = waitTimes;
	}
}
