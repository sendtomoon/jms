package com.jy.common.utils.weixin.session;

import com.jy.common.utils.weixin.vo.api.*;
import java.util.*;
import java.util.concurrent.*;

public class AccessTokenMemoryCache implements MemoryCache<AccessToken>
{
    private Map<String, AccessToken> ats;
    
    public AccessTokenMemoryCache() {
        this.ats = new ConcurrentHashMap<String, AccessToken>();
    }
    
    @Override
    public AccessToken get(final String mpId) {
        return this.ats.get(mpId);
    }
    
    @Override
    public void set(final String mpId, final AccessToken object) {
        this.ats.put(mpId, object);
    }
    
    @Override
    public void remove(final String mpId) {
        this.ats.remove(mpId);
    }
}
