package org.zerock.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class OjdbcTest {
	@Test
	public void testConnection(){
		
		Connection con = null;

		// 시간을 찍기위한 변수
		long start = System.currentTimeMillis(); 

		// 클래스 로드
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "jweb","4321");
			log.info(con);
			con.close();//bad code
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
