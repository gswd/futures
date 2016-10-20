package com.bjgreenchem.production.cost.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lingban.futures.model.FuturesFunds;
import com.lingban.futures.service.FuturesFundsServiceImpl;


public class StudentTest {
	
	private ApplicationContext ctx;
	
	@Before
	public void initialize() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	
	@Test
	public void testGetFuturesFunds(){
		FuturesFundsServiceImpl dao = (FuturesFundsServiceImpl)ctx.getBean("futuresFundsService");
		FuturesFunds f = new FuturesFunds();
		f.setId(1);
		List<FuturesFunds> funturesFundsList = dao.select(f);
		System.out.println(funturesFundsList.get(0));
	}
	
	@Test
	
	public void testCreateStudent(){
		
		
	}
}
