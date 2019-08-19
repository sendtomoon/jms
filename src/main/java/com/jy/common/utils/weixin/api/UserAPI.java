package com.jy.common.utils.weixin.api;

import com.jy.common.utils.weixin.vo.api.*;
import java.util.*;

public interface UserAPI
{
    public static final String user_remark = "/user/info/updateremark?access_token=";
    public static final String user_list = "/user/get?access_token=%s&next_openid=%s";
    public static final String user_info = "/user/info?access_token=%s&openid=%s&lang=%s";
    public static final String batch_user_info = "/user/info/batchget?access_token=";
    
    boolean updateRemark(final String p0, final String p1);
    
    FollowList getFollowerList(final String p0);
    
    Follower getFollower(final String p0, final String p1);
    
    List<Follower> getFollowers(final Collection<Follower2> p0);
}
