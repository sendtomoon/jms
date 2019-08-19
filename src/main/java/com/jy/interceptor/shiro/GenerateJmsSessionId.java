package com.jy.interceptor.shiro;

import java.io.Serializable;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

public class GenerateJmsSessionId implements SessionIdGenerator {

	@Override
	public Serializable generateId(Session session) {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}
	
}
