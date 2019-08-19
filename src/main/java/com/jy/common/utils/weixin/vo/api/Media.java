package com.jy.common.utils.weixin.vo.api;

import org.nutz.json.*;

public class Media
{
    private String type;
    @JsonField("media_id")
    private String mediaId;
    @JsonField("created_at")
    private long createdAt;
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public String getMediaId() {
        return this.mediaId;
    }
    
    public void setMediaId(final String mediaId) {
        this.mediaId = mediaId;
    }
    
    public long getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(final long createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Media [type=" + this.type + ", mediaId=" + this.mediaId + ", createdAt=" + this.createdAt + "]";
    }
}
