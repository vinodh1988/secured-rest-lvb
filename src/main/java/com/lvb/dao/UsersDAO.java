package com.lvb.dao;

import org.springframework.data.repository.CrudRepository;

import com.lvb.model.Users;

public interface UsersDAO extends CrudRepository<Users,Long>{
    Users findUsersByUsername(String username);
}
