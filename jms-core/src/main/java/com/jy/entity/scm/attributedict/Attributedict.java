package com.jy.entity.scm.attributedict;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;
@Alias("baseAttributedict")
public class Attributedict extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String code;
	private Integer sort;
	private String type;
	private String status;
	private String dictId;
	private Integer length;
	private String nullable;
	private String filtertag;
	private String dictName;
	
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getFiltertag() {
		return filtertag;
	}
	public void setFiltertag(String filtertag) {
		this.filtertag = filtertag;
	}
	
	
	
}
