package com.jy.entity.scm.attachment;

import java.io.Serializable;

public class RestFileInfo
  implements Serializable
{
  private static final long serialVersionUID = -6985520131638767506L;
  private boolean status;
  private String desc;
  private String name;
  private String path;

  public boolean isStatus()
  {
    return this.status;
  }
  public void setStatus(boolean status) {
    this.status = status;
  }
  public String getDesc() {
    return this.desc;
  }
  public void setDesc(String desc) {
    this.desc = desc;
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
  
}
