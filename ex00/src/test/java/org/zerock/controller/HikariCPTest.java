package org.zerock.controller;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class HikariCPTest {
	
	@Autowired
	HikariDataSource dataSource;
	
	@Test
	public void test() {
		Connection con=null;
		try {
			con=dataSource.getConnection();
			System.out.println(con);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
		}
	}
	
}
