package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Bugreport;

public interface BugreportRepo extends JpaRepository<Bugreport, Integer>{

}
