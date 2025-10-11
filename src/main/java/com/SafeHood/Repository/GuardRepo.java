package com.SafeHood.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Guard;

public interface GuardRepo  extends JpaRepository<Guard, Integer>{

}
