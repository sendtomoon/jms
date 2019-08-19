package com.jy.entity.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("batchVO")
public class BatchVO<T> implements Serializable{
	
	private static final long serialVersionUID = 7153095851416345690L;

	private List<T> insertList;
	
	private List<T> updateList;
	
	private List<T> deleteList;

	public List<T> getInsertList() {
		return insertList;
	}

	public void setInsertList(List<T> insertList) {
		this.insertList = insertList;
	}

	public List<T> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<T> updateList) {
		this.updateList = updateList;
	}

	public List<T> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<T> deleteList) {
		this.deleteList = deleteList;
	}
}
