package com.jy.common.utils.weixin.aes;

import java.util.*;

public class ByteGroup
{
    private List<Byte> byteContainer;
    
    public ByteGroup() {
        this.byteContainer = new ArrayList<Byte>();
    }
    
    public byte[] toBytes() {
        final byte[] bytes = new byte[this.byteContainer.size()];
        for (int i = 0; i < this.byteContainer.size(); ++i) {
            bytes[i] = this.byteContainer.get(i);
        }
        return bytes;
    }
    
    public ByteGroup addBytes(final byte[] bytes) {
        for (final byte b : bytes) {
            this.byteContainer.add(b);
        }
        return this;
    }
    
    public int size() {
        return this.byteContainer.size();
    }
}
