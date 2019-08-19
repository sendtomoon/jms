package com.jy.common.utils.weixin.session;

public interface MemoryCache<T>
{
    T get(final String p0);
    
    void set(final String p0, final T p1);
    
    void remove(final String p0);
}
