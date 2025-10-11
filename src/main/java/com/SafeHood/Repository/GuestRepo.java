package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Guest;

public interface GuestRepo  extends JpaRepository<Guest, Integer>{

}
