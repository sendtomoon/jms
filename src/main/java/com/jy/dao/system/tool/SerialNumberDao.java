package com.jy.dao.system.tool;

import com.jy.dao.base.JYBatis;
import com.jy.entity.system.tool.SerialNumber;

/**
 * the repository interface of serial number.
 * @author lisongbai
 *
 */
@JYBatis
public interface SerialNumberDao {
	
	/**
	 * to create a config record of serial number.
	 * @param serialNumber
	 * @return
	 */
	int insertSerialNumber(SerialNumber serialNumber);
	
	/**
	 * to make serial number plus one by bussiness key.
	 * @param bussinessKey
	 * @return
	 */
	int increaseSerialNumber(String bussinessKey,String type);
	
	/**
	 * to get the configuration information of serial number by bussiness key.
	 * @param bussinessKey
	 * @return
	 */
	SerialNumber getSerialNumber(String bussinessKey,String type);
	
	/**
	 * when the next day,to reset the serial number and update the dateCode.
	 * @param bussinessKey
	 * @return
	 */
	int resetSerilNumberByDay(String bussinessKey,String type);
	
}
