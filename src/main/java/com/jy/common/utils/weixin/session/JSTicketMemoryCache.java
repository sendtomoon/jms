package com.jy.common.utils.weixin.session;

import com.jy.common.utils.weixin.vo.api.*;
import java.util.*;
import java.util.concurrent.*;

public class JSTicketMemoryCache implements MemoryCache<JSTicket>
{
    private Map<String, JSTicket> jsts;
    
    public JSTicketMemoryCache() {
        this.jsts = new ConcurrentHashMap<String, JSTicket>();
    }
    
    @Override
    public JSTicket get(final String key) {
        return this.jsts.get(key);
    }
    
    @Override
    public void set(final String key, final JSTicket jsTicket) {
        this.jsts.put(key, jsTicket);
    }
    
    @Override
    public void remove(final String key) {
        this.jsts.remove(key);
    }
}
