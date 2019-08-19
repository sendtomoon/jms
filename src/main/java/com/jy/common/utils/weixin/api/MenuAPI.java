package com.jy.common.utils.weixin.api;

import java.util.*;
import com.jy.common.utils.weixin.vo.api.*;

public interface MenuAPI
{
    public static final String query_menu = "/menu/get?access_token=";
    public static final String create_menu = "/menu/create?access_token=";
    public static final String del_menu = "/menu/delete?access_token=";
    
    List<Menu> getMenu();
    
    boolean createMenu(final Menu... p0);
    
    boolean delMenu();
}
