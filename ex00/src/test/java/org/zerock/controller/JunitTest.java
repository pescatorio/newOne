package org.zerock.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JunitTest {
	
	@Test
	public void TestA() {
		Calculator cal = new Calculator();
		int res = cal.calculator("10", "20", "+");
		
		assertEquals(30, res);
	}
	@Test
	public void TestB() {
		Calculator cal = new Calculator();
		int res = cal.calculator("1", "1", "+");
		
		assertEquals(1, res);
	}
	@Test
	public void TestC() {
		Calculator cal = new Calculator();
		//스트링 배열생성{"1","2","str"}
		//반복처리
		String [] array = {"1","2","+"};
		for(String value : array) {
			System.out.println(value);
			int res = cal.calculator(value, value, value);
			System.out.println(res);
			assertEquals(3, res);
		}
	}
	
	
}

