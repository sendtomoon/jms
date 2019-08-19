package com.jy.common.utils.weixin.api;

public interface TemplateAPI
{
    public static final String set_industry = "/template/api_set_industry?access_token=";
    public static final String add_template = "/template/api_add_template?access_token=";
    
    boolean setIndustry(final int p0, final int p1);
    
    String getTemplateId(final String p0);
}
