package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.SOS_Alert;

public interface SosRepo extends JpaRepository<SOS_Alert, Integer>{

}
