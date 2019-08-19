package com.jy.common.utils.tree.entity;

import java.io.*;

public class ZNodes implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String id;
    private String pId;
    private String name;
    private String checked;
    private String open;
    private String chkDisabled;
    private String other;
    private String orgGrade;
    
    public String getOrgGrade() {
        return this.orgGrade;
    }
    
    public void setOrgGrade(final String orgGrade) {
        this.orgGrade = orgGrade;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getpId() {
        return this.pId;
    }
    
    public void setpId(final String pId) {
        this.pId = pId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getChecked() {
        return this.checked;
    }
    
    public void setChecked(final String checked) {
        this.checked = checked;
    }
    
    public String getOpen() {
        return this.open;
    }
    
    public void setOpen(final String open) {
        this.open = open;
    }
    
    public String getChkDisabled() {
        return this.chkDisabled;
    }
    
    public void setChkDisabled(final String chkDisabled) {
        this.chkDisabled = chkDisabled;
    }
    
    public String getOther() {
        return this.other;
    }
    
    public void setOther(final String other) {
        this.other = other;
    }
}
