package com.jy.controller.system.tool;

import com.jy.controller.base.*;
import org.springframework.stereotype.*;
import org.apache.commons.lang3.*;
import org.springframework.web.multipart.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import net.sf.json.*;
import com.jy.common.utils.*;
import com.jy.common.utils.base.*;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping({ "/backstage/tool/webuploader/" })
public class WebuploaderController extends BaseController<Object>
{
    @RequestMapping({ "/test/{change}" })
    public String index(@PathVariable("change") final String change) throws UnsupportedEncodingException {
        if (StringUtils.equals((CharSequence)"img", (CharSequence)change)) {
            return "/system/tool/upload/img";
        }
        if (StringUtils.equals((CharSequence)"file", (CharSequence)change)) {
            return "/system/tool/upload/file";
        }
        return "/system/tool/upload/moreImg";
    }
    
    @RequestMapping({ "uploadPic" })
    public void uploadPic(@RequestParam(value = "file", required = false) final MultipartFile file, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        final JSONObject json = new JSONObject();
        final PrintWriter out = response.getWriter();
        this.logger.info("\u4e0a\u4f20\u56fe\u7247\u5f00\u59cb");
        try {
            final Map<String, String> uploadMap = PropertyUtil.getPropertyMap("upload.properties");
            final String picAllowSuffix = uploadMap.get("picAllowSuffix");
            final String picAllowSize = uploadMap.get("picAllowSize");
            final String picFilePath = uploadMap.get("picFilePath");
            final String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            if (StringUtils.isNotBlank((CharSequence)picAllowSuffix)) {
                final int length = picAllowSuffix.indexOf(suffix.toLowerCase());
                if (length == -1) {
                    json.put((Object)"res", (Object)0);
                    json.put((Object)"resMsg", (Object)"\u8bf7\u4e0a\u4f20\u5141\u8bb8\u683c\u5f0f\u7684\u56fe\u7247");
                    out.print(json.toString());
                    return;
                }
            }
            final long size = file.getSize();
            if (StringUtils.isNotBlank((CharSequence)picAllowSize)) {
                final long allowsize = Long.parseLong(picAllowSize);
                if (size > allowsize) {
                    json.put((Object)"res", (Object)0);
                    json.put((Object)"resMsg", (Object)"\u8d85\u8fc7\u4e0a\u4f20\u56fe\u7247\u5927\u5c0f\u9650\u5236");
                    out.print(json.toString());
                    return;
                }
            }
            final String realPath = request.getSession().getServletContext().getRealPath("/");
            final String path = String.valueOf(realPath) + picFilePath;
            final String fileName = String.valueOf(DateUtils.getDate("yyyyMMdd")) + UuidUtil.get32UUID() + "." + suffix;
            final File baseFile = new File(path);
            final File targetFile = new File(baseFile, fileName);
            if (!baseFile.exists()) {
                baseFile.mkdirs();
            }
            file.transferTo(targetFile);
            json.put((Object)"res", (Object)1);
            json.put((Object)"resMsg", (Object)"\u4e0a\u4f20\u6210\u529f");
            json.put((Object)"saveUrl", (Object)("/" + picFilePath + fileName));
            json.put((Object)"size", (Object)size);
            out.print(json.toString());
            this.logger.info("\u4e0a\u4f20\u56fe\u7247\u7ed3\u675f\uff0c\u4f4d\u7f6e\uff1a" + path);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.logger.error("\u4e0a\u4f20\u56fe\u7247\u51fa\u9519", (Throwable)e);
        }
    }
    
    @RequestMapping({ "uploadFile" })
    public void uploadFile(@RequestParam(value = "file", required = false) final MultipartFile file, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        final JSONObject json = new JSONObject();
        final PrintWriter out = response.getWriter();
        this.logger.info("\u4e0a\u4f20\u6587\u4ef6\u5f00\u59cb");
        try {
            final Map<String, String> uploadMap = PropertyUtil.getPropertyMap("upload.properties");
            final String fileAllowSuffix = uploadMap.get("fileAllowSuffix");
            final String fileAllowSize = uploadMap.get("fileAllowSize");
            final String fileFilePath = uploadMap.get("fileFilePath");
            final String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            if (StringUtils.isNotBlank((CharSequence)fileAllowSuffix)) {
                final int length = fileAllowSuffix.indexOf(suffix.toLowerCase());
                if (length == -1) {
                    json.put((Object)"res", (Object)0);
                    json.put((Object)"resMsg", (Object)"\u8bf7\u4e0a\u4f20\u5141\u8bb8\u683c\u5f0f\u7684\u6587\u4ef6");
                    out.print(json.toString());
                    return;
                }
            }
            final long size = file.getSize();
            if (StringUtils.isNotBlank((CharSequence)fileAllowSize)) {
                final long allowsize = Long.parseLong(fileAllowSize);
                if (size > allowsize) {
                    json.put((Object)"res", (Object)0);
                    json.put((Object)"resMsg", (Object)"\u8d85\u8fc7\u4e0a\u4f20\u6587\u4ef6\u5927\u5c0f\u9650\u5236");
                    out.print(json.toString());
                    return;
                }
            }
            final String realPath = request.getSession().getServletContext().getRealPath("/");
            final String path = String.valueOf(realPath) + fileFilePath;
            final String fileName = String.valueOf(DateUtils.getDate("yyyyMMdd")) + UuidUtil.get32UUID() + "." + suffix;
            final File baseFile = new File(path);
            final File targetFile = new File(baseFile, fileName);
            if (!baseFile.exists()) {
                baseFile.mkdirs();
            }
            file.transferTo(targetFile);
            json.put((Object)"res", (Object)1);
            json.put((Object)"saveUrl", (Object)("/" + fileFilePath + fileName));
            json.put((Object)"resMsg", (Object)"\u4e0a\u4f20\u6210\u529f");
            json.put((Object)"size", (Object)size);
            out.print(json.toString());
            this.logger.info("\u4e0a\u4f20\u6587\u4ef6\u7ed3\u675f\uff0c\u4f4d\u7f6e\uff1a" + path);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.logger.error("\u4e0a\u4f20\u6587\u4ef6\u51fa\u9519", (Throwable)e);
        }
    }
}
