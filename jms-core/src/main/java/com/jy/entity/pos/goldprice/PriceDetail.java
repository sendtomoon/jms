package com.jy.entity.pos.goldprice;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 *
 */
@Alias("ScmPriceDetail")
public class PriceDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3826530000187700493L;

    private String id;
    private String applyId;
    private String goldCode;
    private Double oldPrice;
    private Double price;
    private Double otherPrice;
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getGoldCode() {
        return goldCode;
    }

    public void setGoldCode(String goldCode) {
        this.goldCode = goldCode;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(Double otherPrice) {
        this.otherPrice = otherPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
