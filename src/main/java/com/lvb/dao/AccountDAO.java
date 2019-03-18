package com.lvb.dao;

import org.springframework.data.repository.CrudRepository;

import com.lvb.model.Account;


public interface AccountDAO extends CrudRepository<Account,Long>{
   Account findByUsername(String username);
}
