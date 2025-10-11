package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Complaint;

public interface ComplaintRepo  extends JpaRepository<Complaint, Integer>{

}
