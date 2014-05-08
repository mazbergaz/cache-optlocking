package com.djarum.demo.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.djarum.demo.model.Department;


public interface DepartmentDao {
	
	public Department createDepartment(Department dept);
	public Department getDepartment(int id);
	public Department getDepartmentByName(String name);
	public Department updateDepartment(Department dept) throws Exception;
	public void deleteDepartment(Department dept);
	public List<Department> getAllDepartment();
	
}
