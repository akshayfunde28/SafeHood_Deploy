package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Events;

public interface EventRepo  extends JpaRepository<Events, Integer>{

}
