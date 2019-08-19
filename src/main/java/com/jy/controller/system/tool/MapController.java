package com.jy.controller.system.tool;

import com.jy.controller.base.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.*;
import java.io.*;

@Controller
@RequestMapping({ "/backstage/tool/map/" })
public class MapController extends BaseController<Object>
{
    @RequestMapping({ "index" })
    public String index(final Model model) throws UnsupportedEncodingException {
        if (this.doSecurityIntercept("1")) {
            return "/system/tool/map/list";
        }
        return "/system/noAuthorized";
    }
}
