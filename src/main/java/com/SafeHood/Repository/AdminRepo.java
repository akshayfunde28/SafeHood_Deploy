package com.SafeHood.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Admin_Info;

public interface AdminRepo extends JpaRepository<Admin_Info, Integer> {
	Optional<Admin_Info> findByAdminName(String adminName);
}
 