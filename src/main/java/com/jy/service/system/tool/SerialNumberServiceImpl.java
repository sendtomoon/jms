package com.jy.service.system.tool;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.dao.system.tool.SerialNumberDao;
import com.jy.entity.system.tool.SerialNumber;

@Service("serialNumberService")
public class SerialNumberServiceImpl implements SerialNumberService {

	@Autowired
	private SerialNumberDao dao;

	private static final Logger logger = LoggerFactory.getLogger(SerialNumberServiceImpl.class);

	private final ReentrantLock serialLock = new ReentrantLock();

	private final ReentrantLock codeLock = new ReentrantLock();

	private final ReentrantLock sequenceLock = new ReentrantLock();

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private final static DecimalFormat df = new DecimalFormat("00000");

	private final static DecimalFormat df8 = new DecimalFormat("00000000");
	
//	private final static String SEQ_PLACEHOLDER_DEFAULT = "00000";
	
	private final static String SEQ_PLACEHOLDER_EIGHT = "00000000";

	private final static String SEQ_PLACEHOLDER_ZORE = "0";

	private final static String CODE_PREFIX = "JY";

	private final static String SEQUENCE_TYPE_BARCODE = "0";

	private final static String SEQUENCE_TYPE_DATE_SERIAL = "1";

	private final static String SEQUENCE_TYPE_SEQ_NODATE = "2";

	@Override
	public String generateSerialNumber() {
		return generateSeqNum("");
	}

	@Override
	public String generateSerialNumber(String str) {
		return generateSeqNum(str);
	}
	
	@Override
	public String generateSerialNumberByBussinessKey(String bussinessKey) {
		serialLock.lock();
		logger.info(
				"############################ to generate a serial number : locked it!! ############################");
		String serialNo = "";
		try {
			Long number = 1L;
			SerialNumber serialNumber = dao.getSerialNumber(bussinessKey, SEQUENCE_TYPE_DATE_SERIAL);
			String currentDate = sdf.format(new Date());
			if (serialNumber != null) {
				if (currentDate.equals(serialNumber.getDateCode())) {
					dao.increaseSerialNumber(bussinessKey, SEQUENCE_TYPE_DATE_SERIAL);
					number = serialNumber.getSerialNumber() + 1;
				} else {
					dao.resetSerilNumberByDay(bussinessKey, SEQUENCE_TYPE_DATE_SERIAL);
				}
			} else {
				dao.insertSerialNumber(new SerialNumber(bussinessKey, currentDate, 1L, SEQUENCE_TYPE_DATE_SERIAL));
			}
			serialNo = bussinessKey + currentDate + df.format(number);
		} finally {
			logger.info("############################ to generate a serial number : 【" + serialNo
					+ "】 ==> released lock. ############################");
			serialLock.unlock();
		}
		return serialNo;
	}

	@Override
	public String generateSequenceNumber(String bussinessKey, int num) {
		sequenceLock.lock();
		logger.info(
				"############################ to generate a sequence number : locked it!! ############################");
		String sequenceNo = "";
		try {
			Long number = 1L;
			SerialNumber serialNumber = dao.getSerialNumber(bussinessKey, SEQUENCE_TYPE_SEQ_NODATE);
			if (serialNumber != null) {
				dao.increaseSerialNumber(bussinessKey, SEQUENCE_TYPE_SEQ_NODATE);
				number = serialNumber.getSerialNumber() + 1;
			} else {
				dao.insertSerialNumber(new SerialNumber(bussinessKey, null, 1L, SEQUENCE_TYPE_SEQ_NODATE));
			}
			sequenceNo = bussinessKey + new DecimalFormat(generatePlaceHolder(num)).format(number);
		} finally {
			logger.info("############################ to generate a sequence number : 【" + sequenceNo
					+ "】 ==> released lock. ############################");
			sequenceLock.unlock();
		}
		return sequenceNo;
	}

	private String generatePlaceHolder(int num) {
		String chs = "";
		for (int i = 0; i < num; i++) {
			chs += SEQ_PLACEHOLDER_ZORE;
		}
		return chs;
	}


	private String generateSeqNum(String str) {
		codeLock.lock();
		logger.info(
				"############################ to generate a serial code : locked it!! ############################");
		String serialNo = "";
		try {
			String seqNo = "";
			SerialNumber serialNumber = dao.getSerialNumber(CODE_PREFIX, SEQUENCE_TYPE_BARCODE);
			if (serialNumber != null) {
				int len = String.valueOf(serialNumber.getSerialNumber()).length();
				if (len > SEQ_PLACEHOLDER_EIGHT.length()) {
					seqNo = new DecimalFormat(generatePlaceHolder(len)).format(serialNumber.getSerialNumber() + 1);
				} else {
					seqNo = df8.format(serialNumber.getSerialNumber() + 1);
				}
				dao.increaseSerialNumber(CODE_PREFIX, SEQUENCE_TYPE_BARCODE);
			} else {
				dao.insertSerialNumber(new SerialNumber(CODE_PREFIX, "", 1L, SEQUENCE_TYPE_BARCODE));
				seqNo = df8.format(1);
			}
			serialNo = str + seqNo;
		} finally {
			logger.info("############################ to generate a serial code : 【" + serialNo
					+ "】 ==> released lock. ############################");
			codeLock.unlock();
		}
		return serialNo;
	}

}
