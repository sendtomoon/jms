package com.jy.common.authority;

import com.jy.entity.system.account.Account;

public class Authority {
	
	public static String powerUser(Account user,String table){
		String power="";
		if(user.getRoleId()==""){
			power+= null==table||table==""?"t.CREATETIME":table+".CREATETIME";
		}
		return power;
		
	}
	public static void main(String[] args) {
		Account user=new Account();
		user.setRoleId("");
		String m=Authority.powerUser(user, "sb");
		System.out.println(m);
	}
}
