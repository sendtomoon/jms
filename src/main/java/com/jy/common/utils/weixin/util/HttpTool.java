package com.jy.common.utils.weixin.util;

import org.nutz.log.*;
import org.nutz.http.*;
import org.nutz.http.sender.*;
import org.nutz.lang.*;
import java.io.*;

public class HttpTool
{
    private static final Log log;
    private static final int CONNECT_TIME_OUT = 5000;
    private static final String FILE_NAME_FLAG = "filename=";
    
    static {
        log = Logs.get();
    }
    
    public static String get(final String url) {
        if (HttpTool.log.isDebugEnabled()) {
            HttpTool.log.debugf("Request url: %s, default timeout: %d", new Object[] { url, 5000 });
        }
        try {
            final Response resp = Http.get(url, 5000);
            if (resp.isOK()) {
                final String content = resp.getContent("UTF-8");
                if (HttpTool.log.isInfoEnabled()) {
                    HttpTool.log.infof("GET Request success. Response content: %s", new Object[] { content });
                }
                return content;
            }
            throw Lang.wrapThrow((Throwable)new RuntimeException(String.format("Get request [%s] failed. status: %d", url, resp.getStatus())));
        }
        catch (Exception e) {
            throw Lang.wrapThrow((Throwable)e);
        }
    }
    
    public static String post(final String url, final String body) {
        if (HttpTool.log.isDebugEnabled()) {
            HttpTool.log.debugf("Request url: %s, post data: %s, default timeout: %d", new Object[] { url, body, 5000 });
        }
        try {
            final Request req = Request.create(url, Request.METHOD.POST);
            req.setEnc("UTF-8");
            req.setData(body);
            final Response resp = Sender.create(req, 5000).send();
            if (resp.isOK()) {
                final String content = resp.getContent();
                if (HttpTool.log.isInfoEnabled()) {
                    HttpTool.log.infof("POST Request success. Response content: %s", new Object[] { content });
                }
                return content;
            }
            throw Lang.wrapThrow((Throwable)new RuntimeException(String.format("Post request [%s] failed. status: %d", url, resp.getStatus())));
        }
        catch (Exception e) {
            throw Lang.wrapThrow((Throwable)e);
        }
    }
    
    public static String upload(final String url, final File file) {
        if (HttpTool.log.isDebugEnabled()) {
            HttpTool.log.debugf("Upload url: %s, file name: %s, default timeout: %d", new Object[] { url, file.getName(), 5000 });
        }
        try {
            final Request req = Request.create(url, Request.METHOD.POST);
            req.getParams().put("media", file);
            final Response resp = new FilePostSender(req).send();
            if (resp.isOK()) {
                final String content = resp.getContent();
                return content;
            }
            throw Lang.wrapThrow((Throwable)new RuntimeException(String.format("Upload file [%s] failed. status: %d", url, resp.getStatus())));
        }
        catch (Exception e) {
            throw Lang.wrapThrow((Throwable)e);
        }
    }
    
    public static Object download(final String url) {
        if (HttpTool.log.isDebugEnabled()) {
            HttpTool.log.debugf("Upload url: %s, default timeout: %d", new Object[] { url, 5000 });
        }
        try {
            final Response resp = Http.get(url);
            if (!resp.isOK()) {
                throw Lang.wrapThrow((Throwable)new RuntimeException(String.format("Download file [%s] failed. status: %d, content: %s", url, resp.getStatus(), resp.getContent())));
            }
            String cd = resp.getHeader().get("Content-disposition");
            if (HttpTool.log.isInfoEnabled()) {
                HttpTool.log.infof("Get download file info: %s", new Object[] { cd });
            }
            if (Lang.isEmpty((Object)cd)) {
                return resp.getContent();
            }
            cd = cd.substring(cd.indexOf("filename=") + "filename=".length());
            String tmp = cd.startsWith("\"") ? cd.substring(1) : cd;
            tmp = (tmp.endsWith("\"") ? cd.replace("\"", "") : cd);
            final String filename = tmp.substring(0, tmp.lastIndexOf("."));
            final String fileext = tmp.substring(tmp.lastIndexOf("."));
            if (HttpTool.log.isInfoEnabled()) {
                HttpTool.log.infof("Download file name: %s", new Object[] { filename });
                HttpTool.log.infof("Download file ext: %s", new Object[] { fileext });
            }
            final File tmpfile = File.createTempFile(filename, fileext);
            final InputStream is = resp.getStream();
            final OutputStream os = new FileOutputStream(tmpfile);
            Streams.writeAndClose(os, is);
            return tmpfile;
        }
        catch (Exception e) {
            throw Lang.wrapThrow((Throwable)e);
        }
    }
}
