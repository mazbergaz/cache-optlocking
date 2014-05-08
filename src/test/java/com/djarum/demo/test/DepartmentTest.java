package com.djarum.demo.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.djarum.demo.dao.DepartmentDao;
import com.djarum.demo.model.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class DepartmentTest {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired 
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
	
	private SessionFactory sessionFactory;
//	private ExecutorService executor = Executors.newFixedThreadPool(3);
	
	private int deptId;
	private static final String TEST_DEPT = "TEST_DEPT";
	
	private int createData(){
		EntityManagerFactory emf = entityManagerFactoryBean.getNativeEntityManagerFactory();
		sessionFactory = ((EntityManagerFactoryImpl) emf).getSessionFactory();
		
		System.out.println("\ncreate data "+TEST_DEPT);
		Department department = new Department();
		department.setName(TEST_DEPT);
		department.setLastModified(new Timestamp(System.currentTimeMillis()));
		departmentDao.createDepartment(department);
		
		return getCreatedDataId();
		
	}
	
	private int getCreatedDataId(){
		System.out.println("\ngetting inserted data by name - query cache");
		Department dept = departmentDao.getDepartmentByName(TEST_DEPT);
		printStats(dept);
		return dept.getId();
	}
	
	@Test
	public void cacheTest(){
		deptId = createData();
		System.out.println("\ngetting Department with id "+deptId+" - 1");
		Department dept = departmentDao.getDepartment(deptId);
		printStats(dept);
		assertTrue(sessionFactory.getStatistics().getEntityFetchCount()==0);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCachePutCount()==1);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCacheHitCount()==1);
		
		System.out.println("\ngetting Department with id "+deptId+ " - 2");
		dept = departmentDao.getDepartment(deptId);
		printStats(dept);
		assertTrue(sessionFactory.getStatistics().getEntityFetchCount()==0);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCachePutCount()==1);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCacheHitCount()==2);
		
		dept.setName(TEST_DEPT+"_UPD");
		System.out.println("\nupdating Department with id "+deptId);
		try {
			dept = departmentDao.updateDepartment(dept);
		} catch (Exception e) {
			e.printStackTrace();
		}
		printStats(dept);
		assertTrue(sessionFactory.getStatistics().getEntityFetchCount()==0);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCachePutCount()==2);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCacheHitCount()==3);
		
		System.out.println("\ngetting Department with id "+deptId+ " - 3");
		dept = departmentDao.getDepartment(deptId);
		printStats(dept);
		assertTrue(sessionFactory.getStatistics().getEntityFetchCount()==0);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCachePutCount()==2);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCacheHitCount()==4);
		
	}
	
	/*@Test
	public void optLockTest(){
		deptId = createData();
		System.out.println("\ngetting Department with id "+deptId+" for update");
		Department dept = departmentDao.getDepartment(deptId);
		printStats(dept);*/
		
		/*assertTrue(sessionFactory.getStatistics().getEntityFetchCount()==0);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCachePutCount()==2);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCacheHitCount()==5);
		dept.setName(TEST_DEPT+"_UPD");
		System.out.println("\nupdating Department with id "+deptId);
		dept = departmentDao.updateDepartment(dept);
		printStats(dept);
		assertTrue(sessionFactory.getStatistics().getEntityFetchCount()==0);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCachePutCount()==3);
		assertTrue(sessionFactory.getStatistics().getSecondLevelCacheHitCount()==6);*/
		
		/*for(int i=1; i<=10; i++){
			Runnable runner = new RunnableTester2(deptId, i, departmentDao);
			executor.execute(runner);
			System.out.println("runner "+i+" starts.");
		}
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
	
	@After
	public void clearData(){
		System.out.println("\ndeleting Department with id "+deptId);
		departmentDao.deleteDepartment(departmentDao.getDepartment(deptId));
		printStats(null);
	}

	private void printStats(Object result) {
		if(result!=null){
			System.out.println("result: " + result);
		}
		System.out.println("entity fetch count: " + sessionFactory.getStatistics().getEntityFetchCount());
		System.out.println("L2 put count: " + sessionFactory.getStatistics().getSecondLevelCachePutCount());
		System.out.println("L2 hit count: " + sessionFactory.getStatistics().getSecondLevelCacheHitCount());
	}
	
	/*private class RunnableTester2 implements Runnable{
		private int count;
		private int deptId;
		private DepartmentDao deptDao;
		public RunnableTester2(int deptId, int count, DepartmentDao deptDao){
			this.count = count;
			this.deptId = deptId;
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
					System.out.println(temp+" updated to "+dept);
				}catch(Exception e){
					System.out.println("can't update "+temp+" to "+dept+" : "+e.getMessage());
					e.printStackTrace();
				}
			}else{
				System.out.println("there's no Department with id "+deptId);
			}
		}
	}*/
	
}
