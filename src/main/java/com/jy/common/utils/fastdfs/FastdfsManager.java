package com.jy.common.utils.fastdfs;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.entity.system.account.Account;

public class FastdfsManager {
	private static final Logger logger = LoggerFactory.getLogger(FastdfsManager.class);
	public static final String SEPARATOR = "/";
	private ConnectionPool connectionPool = null;

	private long minPoolSize = 10L;

	private long maxPoolSize = 30L;

	private long waitTimes = 200L;

	private FastdfsManager() {
		init();
	}

	private void init() {
		String logId = UUID.randomUUID().toString();
		logger.info("[初始化线程池(Init)][" + logId + "][默认参数：minPoolSize=" + this.minPoolSize + ",maxPoolSize="
				+ this.maxPoolSize + ",waitTimes=" + this.waitTimes + "]");
		this.connectionPool = new ConnectionPool(this.minPoolSize, this.maxPoolSize, this.waitTimes);
	}

	public static FastdfsManager getInstance() {
		return FastdfsUtilHolder.instance;
	}

	public String upload(byte[] content, String fileName) throws IOException, MyException {
		String logId = UUID.randomUUID().toString();
		TrackerServer trackerServer = this.connectionPool.checkout(logId);
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		String fileExtName = getFileExt(fileName);
		NameValuePair[] metaList = new NameValuePair[4];
		metaList[0] = new NameValuePair("fileName", fileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(content.length));
		metaList[3] = new NameValuePair("fileAuthor", AccountShiroUtil.getCurrentUser().getLoginName());
		String[] result = storageClient.upload_file(content, fileExtName, metaList);

		this.connectionPool.checkin(trackerServer, logId);

		return result[0] + "/" + result[1];
	}

	public String upload(byte[] content, String fileExtName, NameValuePair[] metaList) throws IOException, MyException {
		String logId = UUID.randomUUID().toString();
		TrackerServer trackerServer = this.connectionPool.checkout(logId);
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		String[] result = storageClient.upload_file(content, fileExtName, metaList);

		this.connectionPool.checkin(trackerServer, logId);
		return result[0] + "/" + result[1];
	}

	public byte[] download(String groupName, String remoteFileName) throws IOException, MyException {
		String logId = UUID.randomUUID().toString();
		TrackerServer trackerServer = this.connectionPool.checkout(logId);
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		return storageClient.download_file(groupName, remoteFileName);
	}

	public void download(String groupName, String remoteFileName, String localPath) throws IOException, MyException {
		String logId = UUID.randomUUID().toString();
		TrackerServer trackerServer = this.connectionPool.checkout(logId);
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		byte[] content = storageClient.download_file(groupName, remoteFileName);
		FileUtils.writeByteArrayToFile(new File(localPath), content);
	}

	public int delete(String groupName, String remoteFileName) throws IOException, MyException {
		return toDelete(groupName, remoteFileName);
	}

	public int deleteFullPath(String fileUrl) throws IOException, MyException {
		String[] result = parseUrl(fileUrl);
		return toDelete(result[0], result[1]);
	}

	private int toDelete(String groupName, String remoteFileName) throws IOException, MyException {
		String logId = UUID.randomUUID().toString();
		TrackerServer trackerServer = this.connectionPool.checkout(logId);
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		int result = storageClient.delete_file(groupName, remoteFileName);

		this.connectionPool.checkin(trackerServer, logId);
		return result;
	}

	private String getFileExt(String fileName) {
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index + 1);
	}

	public String[] parseUrl(String fileUrl) {
		int beginIndex = fileUrl.indexOf("/") + 2;
		int fromIndex = fileUrl.indexOf("/", beginIndex);
		int toIndex = fileUrl.indexOf("/", fromIndex + 1);
		String groupName = fileUrl.substring(fromIndex + 1, toIndex);
		String remoteFileName = fileUrl.substring(toIndex + 1);
		String[] result = new String[2];
		result[0] = groupName;
		result[1] = remoteFileName;
		return result;
	}

	private static final class FastdfsUtilHolder {
		private static FastdfsManager instance = new FastdfsManager();
	}
}
