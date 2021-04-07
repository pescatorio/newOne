package jmp.spring.vo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class sampleVOList {

	ArrayList <sampleVo> list;
	
	public sampleVOList() {
		list = new ArrayList<>();
	}
}
