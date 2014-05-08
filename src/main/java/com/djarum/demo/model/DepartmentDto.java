package com.djarum.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DepartmentDto {
	
	private String id;
	private String name;
	private String lastModified;
	
	public DepartmentDto(){}
	
	public DepartmentDto(Department dept){
		this.id = ""+dept.getId();
		this.name = dept.getName();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = new Date(dept.getLastModified().getTime());
		this.lastModified = sdf.format(dt);
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
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "DepartmentDto [id=" + id + ", name=" + name + ", lastModified="
				+ lastModified + "]";
	}
	
	
}
