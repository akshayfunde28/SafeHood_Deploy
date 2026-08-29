package com.SafeHood.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SafeHood.Entities.Guest;
import com.SafeHood.Entities.Society;

public interface GuestRepo  extends JpaRepository<Guest, Integer>{
	List<Guest> findByResidentIdOrderByCreatedAtDesc(
	        Integer residentId);
	List<Guest>
	findBySocietyOrderByCreatedAtDesc(
	        Society society);
	List<Guest>
	findBySocietyAndVerificationStatusAndEntryStatusAndValidTillAfterOrderByCreatedAtDesc(
	        Society society,
	        String verificationStatus,
	        String entryStatus,
	        LocalDateTime currentTime);
	
	@Query("SELECT g FROM Guest g WHERE g.society = :society " +
		       "AND g.verificationStatus = :status " +
		       "ORDER BY g.guest_EntryTime DESC")
		List<Guest> getApprovedGuestList(
		        @Param("society") Society society,
		        @Param("status") String status);
}
