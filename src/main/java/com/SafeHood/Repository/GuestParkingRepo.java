package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.GuestParking;

public interface GuestParkingRepo  extends JpaRepository<GuestParking, Integer>{

}
