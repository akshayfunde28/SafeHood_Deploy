package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.User;

public interface UserRepo  extends JpaRepository<User, Integer>{

}
