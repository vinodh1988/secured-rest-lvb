package com.lvb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvb.dao.AccountDAO;
import com.lvb.model.Account;

@Service
public class AccountService {
	
	@Autowired
	AccountDAO acc;
	
	public List<Account> getAccount(){
		return (List<Account>)acc.findAll();
	}
	
	public void addAccount(Account account){
		acc.save(account);
	}
	
	public Account getAccount(String username){
		return acc.findByUsername(username);
	}

}
