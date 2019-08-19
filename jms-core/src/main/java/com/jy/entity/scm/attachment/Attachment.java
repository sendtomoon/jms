package com.jy.entity.scm.attachment;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;


/**
 * 附件管理
 * @author Administrator
 *
 */
@Alias("Attachment")
public class Attachment extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -3524345196322300490L;
	//主键
	private String id;
	//业务ID
	private String busnessid;
	//附件名称
	private String name;
	//文件相对路径
	private String path;
	//创建时间
	private Date createtime;
	
	private List<Attachment> attachments;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusnessid() {
		return busnessid;
	}
	public void setBusnessid(String busnessid) {
		this.busnessid = busnessid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
