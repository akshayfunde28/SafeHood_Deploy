package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Parking;

public interface ParkingRepo extends JpaRepository<Parking, Integer> {
}

