package jmp.spring.ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaxxer.hikari.HikariDataSource;

import jmp.spring.mapper.timeMapper;

@RunWith(SpringJUnit4ClassRunner.class) //applicationcontext에 bean을 생성하여 테스트한다.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OjdbcTest {
	
	@Autowired
	HikariDataSource dataSource;
	
	@Autowired
	SqlSessionFactory factory;
	
	@Autowired
	timeMapper tm;
	
	@Test
	public void tmTest() {
		System.out.println(tm.getTime());
	}
	@Test
	public void tmTest2() {
		System.out.println(tm.getTime2());
	}
	
	@Test
	public void sqlSessionFactoryTest() {
		SqlSession sqlSession = factory.openSession();
		sqlSession.getConnection();
	}
	
	@Test
	public void hikariCPTest() {
		try {
			dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	@Test
	public void ojdbcTest()  throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "jweb","4321");
		conn.close();
		System.out.println(conn);
	}
}
