package com.jy.entity.pos.payments;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

/**
 *
 */
@Alias("posPaymentBill")
public class PaymentBill extends BaseEntity {

    private static final long serialVersionUID = -4837588495494859340L;

    private String id;
    private String memberId;
    private String orderNo;
    private String status;
    private Double payAmount;
    private String types;
    private Integer payIntegral;
    private Integer getIntegral;
    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Integer getPayIntegral() {
        return payIntegral;
    }

    public void setPayIntegral(Integer payIntegral) {
        this.payIntegral = payIntegral;
    }

    public Integer getGetIntegral() {
        return getIntegral;
    }

    public void setGetIntegral(Integer getIntegral) {
        this.getIntegral = getIntegral;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
