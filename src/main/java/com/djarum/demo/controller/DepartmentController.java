package com.djarum.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djarum.demo.dao.DepartmentDao;
import com.djarum.demo.model.Department;
import com.djarum.demo.model.DepartmentDto;

@Controller
@RequestMapping("department")
public class DepartmentController {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@RequestMapping("{id}")
	@ResponseBody
	public DepartmentDto getById(@PathVariable Integer id){
		System.out.println("accessing get department by id "+ id +" via http request");
		return new DepartmentDto(departmentDao.getDepartment(id));
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
    @ResponseBody
	public String saveDepartment(@RequestBody DepartmentDto departmentDto){
		Department department = new Department();
		department.setId(Integer.parseInt(departmentDto.getId()));
		department.setName(departmentDto.getName());
		System.out.println("saving "+department+" via http request");
		return ""+departmentDao.createDepartment(department).getId();
	}
	
	@RequestMapping(value="update/{id}", method=RequestMethod.PUT)
    @ResponseBody
	public String updateDepartment(@RequestBody DepartmentDto departmentDto){
		String result = null;
		try {
			System.out.println("received = "+departmentDto);
			Department department = new Department();
			department.setId(Integer.parseInt(departmentDto.getId()));
			department.setName(departmentDto.getName());
			result = ""+departmentDao.updateDepartment(department).getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteDepartment(Department department){
		System.out.println("delete "+department+" via http request");
		String result = null;
		try{
			departmentDao.deleteDepartment(department);
			result = "success";
		}catch(Exception e){
			result = "failed. "+e.getMessage();
		}
		return result;
	}
	
	@RequestMapping(value="all", method=RequestMethod.GET)
	@ResponseBody
	public List<DepartmentDto> getAllDepartment(){
		System.out.println("accessing get all department via http request");
		return convertAllToDto(departmentDao.getAllDepartment());
	}
	
	private List<DepartmentDto> convertAllToDto(List<Department> deptList){
		List<DepartmentDto> deptDtoList = new ArrayList<DepartmentDto>();
		for(Department dept : deptList){
			deptDtoList.add(new DepartmentDto(dept));
		}
		return deptDtoList;
	}
	
}
