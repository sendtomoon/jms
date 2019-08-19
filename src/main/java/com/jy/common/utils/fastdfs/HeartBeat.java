/*    */ package com.jy.common.utils.fastdfs;
/*    */ 
/*    */ import java.util.Timer;
/*    */ import java.util.TimerTask;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.csource.fastdfs.ProtoCommon;
/*    */ import org.csource.fastdfs.TrackerServer;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class HeartBeat
/*    */ {
/* 21 */   private static final Logger logger = LoggerFactory.getLogger(HeartBeat.class);
/*    */ 
/* 24 */   private ConnectionPool pool = null;
/*    */ 
/* 27 */   public static int ahour = 3600000;
/*    */ 
/* 30 */   public static int waitTimes = 200;
/*    */ 
/*    */   public HeartBeat(ConnectionPool pool) {
/* 33 */     this.pool = pool;
/*    */   }
/*    */ 
/*    */   public void beat()
/*    */   {
/* 42 */     logger.info("[心跳任务方法（beat）]");
/* 43 */     TimerTask task = new TimerTask()
/*    */     {
/*    */       public void run() {
/* 46 */         String logId = UUID.randomUUID().toString();
/* 47 */         HeartBeat.logger.info("[心跳任务方法（beat）][" + logId + "][Description:对idleConnectionPool中的TrackerServer进行监测]");
/* 48 */         LinkedBlockingQueue idleConnectionPool = HeartBeat.this.pool.getIdleConnectionPool();
/* 49 */         TrackerServer ts = null;
/* 50 */         for (int i = 0; i < idleConnectionPool.size(); i++)
/*    */           try {
/* 52 */             ts = (TrackerServer)idleConnectionPool.poll(HeartBeat.waitTimes, TimeUnit.SECONDS);
/* 53 */             if (ts == null) break;
/* 54 */             ProtoCommon.activeTest(ts.getSocket());
/* 55 */             idleConnectionPool.add(ts);
/*    */           }
/*    */           catch (Exception e)
/*    */           {
/* 62 */             HeartBeat.logger.error("[心跳任务方法（beat）][" + logId + "][异常：当前连接已不可用将进行重新获取连接]", e);
/* 63 */             HeartBeat.this.pool.drop(ts, logId);
/*    */           }
/*    */       }
/*    */     };
/* 68 */     Timer timer = new Timer();
/* 69 */     timer.schedule(task, ahour, ahour);
/*    */   }
/*    */ }

/* Location:           E:\下载\jy\jy\framework\jy-framework\0.0.35-SNAPSHOT\jy-framework-0.0.35-20170420.085006-1.jar
 * Qualified Name:     com.jy.common.utils.fastdfs.HeartBeat
 * JD-Core Version:    0.6.2
 */