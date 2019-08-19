package com.jy.controller.scm.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;

@RequestMapping("/scm/common/")
@Controller
public class CommonController extends BaseController {

	@RequestMapping(value = "downloads", method = RequestMethod.GET)
	public String downloads(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		if(!StringUtils.isEmpty(name) && name.equals(new String(name.getBytes("iso8859-1"),"iso8859-1"))){
			name = new String(name.getBytes("iso8859-1"), "UTF-8"); 
		}
		String rootDir = request.getSession().getServletContext().getRealPath("/");
		response.reset();
		response.setContentType(getContentType(name));
		response.addHeader("Content-Disposition", "attachment;filename=\"" + encodeFileName(request, name) + "\"");
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {

			bis = new BufferedInputStream(new FileInputStream(rootDir + "/template/" + name));
			bos = new BufferedOutputStream(out);

			byte[] buff = new byte[2048];
			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;

	}

	@RequestMapping("logs")
	public String index(Model model) {
		if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "system/log/downloadLogs";
		}
		return Const.NO_AUTHORIZED_URL;
	}

	@RequestMapping(value = "logs/downloads", method = RequestMethod.GET)
	public String downloadsLogs(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		name = new String(name.getBytes("iso8859-1"), "UTF-8");
		String rootDir = getTomcatWebappsPath(request);
		response.reset();
		response.setContentType(getContentType(name));
		response.addHeader("Content-Disposition", "attachment;filename=\"" + encodeFileName(request, name) + "\"");
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {

			bis = new BufferedInputStream(new FileInputStream(rootDir + "/logs/JY_Log/" + name));
			bos = new BufferedOutputStream(out);

			byte[] buff = new byte[2048];
			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;

	}

	private static String getContentType(String fileName) {
		String ext = "";
		if (fileName.contains(".")) {
			ext = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
		}
		if (ext.equals(".zip")) {
			return "application/zip";
		} else if (ext.equals(".xls") || ext.equals(".xlsx")) {
			return "application/x-excel";
		} else if (ext.equals(".doc") || ext.equals(".docx")) {
			return "application/msword";
		} else if (ext.equals(".pdf")) {
			return "application/pdf";
		} else if (ext.equals(".jpg") || ext.equals(".jpeg")) {
			return "image/jpeg";
		} else if (ext.equals(".gif")) {
			return "image/gif";
		} else if (ext.equals(".png")) {
			return "image/png";
		} else if (ext.equals(".bmp")) {
			return "image/bmp";
		}
		return "application/force-download";
	}

	/**
	 * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
	 * 
	 * @param request
	 * @param pFileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String encodeFileName(HttpServletRequest request, String name) throws UnsupportedEncodingException {

		String filename = null;
		String agent = request.getHeader("USER-AGENT");
		if (null != agent) {
			if (-1 != agent.indexOf("Firefox")) {// Firefox
				filename = "=?UTF-8?B?" + (new String(Base64.encodeBase64(name.getBytes("UTF-8")))) + "?=";
			} else if (-1 != agent.indexOf("Chrome")) {// Chrome
				filename = new String(name.getBytes(), "ISO8859-1");
			} else {// IE7+
				filename = java.net.URLEncoder.encode(name, "UTF-8");
				filename = StringUtils.replace(filename, "+", "%20");// 替换空格
			}
		} else {
			filename = name;
		}
		return filename;
	}

	private static String getTomcatWebappsPath(HttpServletRequest request) {
		String path = "";
		String projectRoot = request.getSession().getServletContext().getRealPath("/");
		if (projectRoot.contains("wtpwebapps")) {
			path = projectRoot.substring(0, projectRoot.indexOf("wtpwebapps"));
		} else if (projectRoot.contains("webapps")) {
			path = projectRoot.substring(0, projectRoot.indexOf("webapps"));
		} else if (projectRoot.contains("WebRoot")) {
			path = projectRoot.substring(0, projectRoot.indexOf("WebRoot"));
		}
		return path;
	}

}
