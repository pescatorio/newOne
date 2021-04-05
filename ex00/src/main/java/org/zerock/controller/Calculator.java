package org.zerock.controller;

public class Calculator {
	String a="10";
	String b="20";
	String c="-";
	public int calculator(String a, String b, String c) {
		int total = 0;
		if("+".equals(c))
			total=Integer.parseInt(a)+Integer.parseInt(b);
		if(c=="-")
			total=Integer.parseInt(a)-Integer.parseInt(b);
		if(c=="*")
			total=Integer.parseInt(a)*Integer.parseInt(b);
		if(c=="/")
			total=Integer.parseInt(a)/Integer.parseInt(b);
		return total;
	}

}

