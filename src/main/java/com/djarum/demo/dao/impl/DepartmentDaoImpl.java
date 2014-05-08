package com.djarum.demo.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.djarum.demo.dao.DepartmentDao;
import com.djarum.demo.model.Department;
import com.djarum.demo.repository.DepartmentRepository;

@Service
public class DepartmentDaoImpl implements DepartmentDao {
	
	@Resource
	private DepartmentRepository deptRepo;

	@Transactional
	public Department createDepartment(Department dept) {
		return deptRepo.save(dept);
	}

	public Department getDepartment(int id) {
		return deptRepo.findOne(id);
	}
	
	public Department getDepartmentByName(String name){
		return deptRepo.findByName(name);
	}
	
	@Transactional
	public Department updateDepartment(Department dept) throws Exception {
		Department updated = null;
		try{
			updated = deptRepo.save(dept);
		}catch(Exception ex){
			throw ex;
		}
		return updated;
	}

	@Transactional
	public void deleteDepartment(Department dept) {
		deptRepo.delete(dept);
	}

	public List<Department> getAllDepartment() {
		return deptRepo.findAll();
	}

}
