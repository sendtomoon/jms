package com.jy.common.utils.weixin.api;

import com.jy.common.utils.weixin.vo.api.*;
import java.util.*;

public interface GroupsAPI
{
    public static final String create_groups = "/groups/create?access_token=";
    public static final String get_groups = "/groups/get?access_token=";
    public static final String get_member_group = "/groups/getid?access_token=";
    public static final String update_group = "/groups/update?access_token=";
    public static final String update_member_group = "/groups/members/update?access_token=";
    public static final String update_members_group = "/groups/members/batchupdate?access_token=";
    public static final String delete_groups = "/groups/delete?access_token=";
    
    int createGroup(final String p0);
    
    List<Groups> getGroups();
    
    int getGroup(final String p0);
    
    boolean renGroups(final int p0, final String p1);
    
    boolean move2Group(final String p0, final int p1);
    
    boolean batchMove2Group(final Collection<String> p0, final int p1);
    
    boolean delGroup(final int p0);
}
