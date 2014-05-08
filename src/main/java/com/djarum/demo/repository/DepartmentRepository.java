package com.djarum.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.djarum.demo.model.Department;

@Service
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	
	@Query("select d from com.djarum.demo.model.Department d where d.name = ?1")
	public Department findByName(String name);
	
}
