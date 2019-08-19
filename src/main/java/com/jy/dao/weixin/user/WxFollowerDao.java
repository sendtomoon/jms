package com.jy.dao.weixin.user;

import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.weixin.user.WxFollower;


/**
 * 微信关注者数据层
 */
@JYBatis
public interface WxFollowerDao  extends BaseDao<WxFollower>{
 
    public void clearFollower();
    
    public void insertFollowers(List<WxFollower> o);
}
