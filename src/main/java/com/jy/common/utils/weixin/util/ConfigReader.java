package com.jy.common.utils.weixin.util;

import org.nutz.log.*;
import java.io.*;
import org.nutz.lang.*;
import java.util.*;

public class ConfigReader
{
    private static final Log log;
    private Map<String, String> confs;
    
    static {
        log = Logs.get();
    }
    
    public ConfigReader(final String path) {
        this.confs = new LinkedHashMap<String, String>();
        this.load(path);
    }
    
    protected synchronized void load(final String path) {
        if (ConfigReader.log.isDebugEnabled()) {
            ConfigReader.log.debugf("Loading config file[%s].", new Object[] { path });
        }
        InputStream is = null;
        BufferedReader br = null;
        final Properties p = new Properties();
        try {
            is = this.getClass().getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(is));
            p.load(br);
        }
        catch (Exception e) {
            throw Lang.wrapThrow((Throwable)e);
        }
        finally {
            Streams.safeClose((Closeable)is);
            Streams.safeClose((Closeable)br);
        }
        Streams.safeClose((Closeable)is);
        Streams.safeClose((Closeable)br);
        this.putAll(p);
    }
    
    protected void putAll(final Map p) {
        this.confs.putAll(p);
    }
    
    public synchronized void clear() {
        this.confs.clear();
    }
    
    protected void valid(final String key) {
        if (Strings.isBlank((CharSequence)key)) {
            throw new NullPointerException("Key can't not be NULL or empty value.");
        }
    }
    
    public void put(final String key, final String value) {
        this.confs.put(key, value);
    }
    
    public List<String> keys() {
        return new ArrayList<String>(this.confs.keySet());
    }
    
    public Collection<String> values() {
        return this.confs.values();
    }
    
    public String get(final String key) {
        this.valid(key);
        return this.confs.get(key);
    }
    
    public int getInt(final String key) {
        this.valid(key);
        return Integer.parseInt(this.get(key));
    }
    
    public long getLong(final String key) {
        this.valid(key);
        return Long.valueOf(this.get(key));
    }
    
    public boolean getBoolean(final String key) {
        this.valid(key);
        return Boolean.valueOf(this.get(key));
    }
}
