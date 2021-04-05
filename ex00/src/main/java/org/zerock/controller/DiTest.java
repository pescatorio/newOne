package org.zerock.controller;


class Chef{
	
	String type;

	public Chef(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
}

class Rest{
	
	Chef chef;

	public Rest() {
		System.out.println("Rest()생성자 호출");
	}
	
	@Override
	public String toString() {
		return chef.type+"식당입니다.";
	}
	
}


public class DiTest {

	public static void main(String[] args) {

		Chef chef = new Chef("중식");

		Rest rest = new Rest();
		System.out.println(rest);
		
	

	}

}
