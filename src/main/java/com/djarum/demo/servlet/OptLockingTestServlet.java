package com.djarum.demo.servlet;

import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.djarum.demo.dao.DepartmentDao;
import com.djarum.demo.model.Department;

public class OptLockingTestServlet extends HttpServlet {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	private static final String TEST_DEPT = "TEST_DEPT";
	private int deptId; 
	private ExecutorService executor = Executors.newFixedThreadPool(3);
	
	public void init(ServletConfig config){
		try {
			super.init(config);
		    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		for(int i=1; i<=4; i++){
			Runnable runner = new RunnableTester2(i, departmentDao);
			executor.execute(runner);
			System.out.println("runner "+i+" starts.");
		}
	}
	
	private void createData(){
		System.out.println("\n"+this.getClass().getName()+" - create data "+TEST_DEPT);
		Department department = new Department();
		department.setName(TEST_DEPT);
		department.setLastModified(new Timestamp(System.currentTimeMillis()));
		departmentDao.createDepartment(department);
		deptId = getCreatedDataId();
	}
	
	private int getCreatedDataId(){
		System.out.println("\n"+this.getClass().getName()+" - getting inserted data by name - query cache");
		Department dept = departmentDao.getDepartmentByName(TEST_DEPT);
		return dept.getId();
	}
	
	private class RunnableTester2 implements Runnable{
		private int count;
		private DepartmentDao deptDao;
		public RunnableTester2(int count, DepartmentDao deptDao){
			this.count = count;
			this.deptDao = deptDao;
		}
		public void run() {
			Department dept = deptDao.getDepartment(deptId);
			Department temp = null;
			if(dept!=null){
				try {
					temp = (Department) BeanUtils.cloneBean(dept);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				dept.setName(TEST_DEPT+"_UPD_"+count);
				try{
					dept = deptDao.updateDepartment(dept);
					System.out.println(this.getClass().getName()+" - "+temp+" updated to "+dept);
				}catch(Exception e){
					System.out.println(this.getClass().getName()+" - can't update "+temp+" to "+dept+" : "+e.getMessage());
					e.printStackTrace();
				}
			}else{
				System.out.println(this.getClass().getName()+" - there's no Department with id "+deptId);
			}
		}
	}
	
	private void clearData(){
		System.out.println("\n"+this.getClass().getName()+" - deleting Department with id "+deptId);
		departmentDao.deleteDepartment(departmentDao.getDepartment(deptId));
	}
	
}
