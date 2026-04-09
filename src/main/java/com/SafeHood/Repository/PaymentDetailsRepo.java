package com.SafeHood.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SafeHood.Entities.PaymentDetails;
import com.SafeHood.Entities.PaymentDetailsDTO;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Integer> {
	Optional<PaymentDetails> findBySociety_Username(String username);
	@Query("SELECT new com.SafeHood.Entities.PaymentDetailsDTO(" +
		       "p.upiId, p.mobileNumber, p.accountNumber, p.ifscCode, p.accountHolderName) " +
		       "FROM PaymentDetails p " +
		       "WHERE p.society.username = :username")
		PaymentDetailsDTO getPaymentDetailsDTO(@Param("username") String username);
}
