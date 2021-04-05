package org.zerock.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class OjdbcTest2 {
	long start = System.currentTimeMillis(); 
	@Test
	public void testConn(){
		Connection conn=null;
		for(int i=0;i<100;i++) {
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "jweb","4321");
				conn.close();
				System.out.println("========="+conn+"========"+i);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(!conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}//catch
			}//finally
		}//for
		long end = System.currentTimeMillis();
		System.out.println("소요시간:"+(end-start)/1000.0+"초");
		System.out.println("소요시간:"+(end-start)+"ms");
	}//testconn
}//ojdbcTest2
