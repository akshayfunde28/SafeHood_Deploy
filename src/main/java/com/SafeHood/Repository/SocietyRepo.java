package com.SafeHood.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SafeHood.Entities.Society;
import java.util.List;

 

public interface SocietyRepo  extends JpaRepository<Society, Integer> {

	@Query("select s from Society s where username= :UserName")
	public Society getSocietyBySocietyName(@Param("UserName") String username);
	
	Optional<Society> findByUsername(String username);
	
	
}
