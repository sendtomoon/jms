package com.jy.service.system.tool;

/**
 * the service interface of serial number
 * @author lisongbai
 * 
 */
public interface SerialNumberService {
	
	/**
	 * to generate a serial number by bussiness key.
	 * @param bussinessKey:bussiness mark. eg:CG101,CK102...
	 * @return CG101+20161229+00001
	 */
	public String generateSerialNumberByBussinessKey(String bussinessKey);

	/**
	 * to generate a serial code.
	 * @return eg:JY+00001
	 */
	public String generateSerialNumber();
	
	/**
	 * to generate a sequence number by bussiness key and the length of placeholders.
	 * @param bussinessKey:bussiness mark. eg:AB,AC...
	 * @param num:the length of placeholders. eg:##(2),###(3)... 
	 * @return eg:AB+0001
	 */
	public String generateSequenceNumber(String bussinessKey,int num);
	
	
	/**
	 * to generate a serial code.
	 * @return eg:JY+str+00001
	 */
	public String generateSerialNumber(String string);
}
