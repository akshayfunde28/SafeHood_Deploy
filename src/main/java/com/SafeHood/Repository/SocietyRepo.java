package com.SafeHood.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.SocietyDTO;

import java.util.List;
 

public interface SocietyRepo  extends JpaRepository<Society, Integer> {

	@Query("select s from Society s where username= :UserName")
	public Society getSocietyBySocietyName(@Param("UserName") String username);
	
	Optional<Society> findByUsername(String username);
	
	@Query("SELECT new com.SafeHood.Entities.SocietyDTO(" +
		       "s.username, s.societyAddress, s.adminPassword, s.pinCode, s.password, s.status) " +
		       "FROM Society s")
		List<SocietyDTO> getAllSocietyDTO();
	
	
}
