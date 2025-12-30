package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.EventHallBooking;

public interface BookingHallRepo extends JpaRepository<EventHallBooking,Integer>{

 }
