package com.lvb.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lvb.model.Person;

@Service
public class DataService {
  public List<Person> getPeople(){
	  List<Person> l=new ArrayList<Person>();
	  l.add(new Person(12,"Ravi","Chennai"));
	  l.add(new Person(13,"Rajesh","Chennai"));
	  l.add(new Person(14,"Kiran","Indore"));
	  l.add(new Person(15,"Prathap","Hyderabad"));
	  l.add(new Person(16,"Sameul","Bangalore"));
	  return l;
	  
	  
  }
}
