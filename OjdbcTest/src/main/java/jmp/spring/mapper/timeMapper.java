package jmp.spring.mapper;

import org.apache.ibatis.annotations.Select;

public interface timeMapper {

	@Select("SELECT sysdate FROM dual")
	public String getTime();
	
	public String getTime2();
}
