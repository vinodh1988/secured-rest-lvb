package com.lvb.model;

public class Person {
    private int sno;
    private String name;
    private String city;
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Person(int sno, String name, String city) {
		super();
		this.sno = sno;
		this.name = name;
		this.city = city;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
    
    
}
