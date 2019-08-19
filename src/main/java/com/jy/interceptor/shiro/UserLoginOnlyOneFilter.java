package com.jy.interceptor.shiro;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

public class UserLoginOnlyOneFilter extends AccessControlFilter {

    private int maxUser = 1; 
	private SessionManager sessionManager;    
    private Cache<String, Deque<Serializable>> userNameCache;
    private String loginUrl;

	public int getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public Cache<String, Deque<Serializable>> getUserNameCache() {
		return userNameCache;
	}

	public void setCacheManager(CacheManager cacheManager) {
        this.userNameCache = cacheManager.getCache("user-session-onlyone");
    }

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object object) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String projectName = req.getContextPath();
		Subject subject = getSubject(request, response);
	    if(!subject.isAuthenticated() && !subject.isRemembered()) {
	        return true;
	    }
	    Session session = subject.getSession();
	    String username = (String) subject.getPrincipal();
	    Serializable sessionId = session.getId();
	    Deque<Serializable> deque = userNameCache.get(username);
	    if(deque == null) {
	        deque = new LinkedList<Serializable>();
	        userNameCache.put(username, deque);
	    }
	
	    if(!deque.contains(sessionId) && session.getAttribute("limitUser") == null) {
	        deque.push(sessionId);
	    }
	
	    while(deque.size() > maxUser) {
	        Serializable limitSessionId = null;
	        try {
	        	limitSessionId = deque.removeLast();
	            Session limitUserSession = sessionManager.getSession(new DefaultSessionKey(limitSessionId));
	            if(limitUserSession != null) {
	            	limitUserSession.setAttribute("limitUser", true);
	            }
	        } catch (Exception e) {
	        	
	        }
	    }
	
	    if (session.getAttribute("limitUser") != null) {
	        try {
	            subject.logout();
	        } catch (Exception e) {
	        	
	        }
	        saveRequest(request);
	        if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
	        	resp.addHeader("sessionstatus", "sessionLimit");
	            resp.addHeader("loginPath", projectName+loginUrl);
	        } else {
	        	String str = "<script language='javascript'>alert('抱歉，此账号在另一处被登录。您被迫下线！');"
                        + "window.top.location.href='"
                        + projectName+loginUrl
                        + "';</script>";
	        	resp.setContentType("text/html;charset=UTF-8");
                try {
                    PrintWriter writer = resp.getWriter();
                    writer.write(str);
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                   
                }
                //WebUtils.issueRedirect(request, response, callbackUrl);
	        }
	        return false;
	    }
	
	    return true;
	}


}
