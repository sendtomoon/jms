package com.jy.controller.system.verifyCode;

import com.jy.controller.base.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import com.jy.common.utils.verifyCode.*;
import org.apache.shiro.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import org.apache.shiro.subject.*;
import org.apache.shiro.session.*;
import java.awt.image.*;

@Controller
@RequestMapping({ "/verifyCode" })
public class VerifyCodeController extends BaseController<Object>
{
    @RequestMapping({ "/slogin" })
    public void slogin(final HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        final String verifyCode = VerifyCodeUtil.generateTextCode(2, 4, null);
        final Subject currentUser = SecurityUtils.getSubject();
        final Session session = currentUser.getSession();
        session.setAttribute((Object)"sessionSecCode", (Object)verifyCode);
        try {
            response.setContentType("image/jpeg");
            final BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null, null);
            ImageIO.write(bufferedImage, "JPEG", (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
