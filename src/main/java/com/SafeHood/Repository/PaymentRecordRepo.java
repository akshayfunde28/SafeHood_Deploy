package com.SafeHood.Repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SafeHood.Entities.PaymentRecord;
import com.SafeHood.Entities.PaymentRecordDTO;
import com.SafeHood.Entities.UserPaymentDTO;

public interface PaymentRecordRepo extends JpaRepository<PaymentRecord, Integer>{

	@Query("SELECT new com.SafeHood.Entities.UserPaymentDTO(" +
		       "p.paymentId, u.user_Id, u.user_name, u.flat_No, " +
		       "p.maintenanceAmount, p.fineAmount, p.totalAmount, p.status, p.fineReason) " +
		       "FROM PaymentRecord p JOIN p.user u " +
		       "WHERE p.society.username = :username " +
		       "AND p.dateTime BETWEEN :start AND :end")
	    List<UserPaymentDTO> getCurrentMonthData(
	            @Param("username") String username,
	            @Param("start") LocalDateTime start,
	            @Param("end") LocalDateTime end);
	
	@Query("SELECT new com.SafeHood.Entities.UserPaymentDTO(" +
		       "p.paymentId, u.user_Id, u.user_name, u.flat_No, " +
		       "p.maintenanceAmount, p.fineAmount, p.totalAmount, p.status, p.fineReason) " +
		       "FROM PaymentRecord p JOIN p.user u " +
		       "WHERE p.society.username = :username " +
		       "AND p.dateTime BETWEEN :start AND :end")
		List<UserPaymentDTO> getPaymentByMonth(
		        @Param("username") String username,
		        @Param("start") java.time.LocalDateTime start,
		        @Param("end") java.time.LocalDateTime end);
	
	
	@Query("SELECT new com.SafeHood.Entities.PaymentRecordDTO(" +
		       "p.paymentId, p.totalAmount, p.maintenanceAmount, " +
		       "p.fineAmount, p.status, p.fineReason, p.dateTime) " +
		       "FROM PaymentRecord p " +
		       "WHERE p.society.username = :username " +
		       "AND p.user.user_Id = :userId " +
		       "ORDER BY p.dateTime DESC")
		List<PaymentRecordDTO> getTopPayments(
		        @Param("username") String username,
		        @Param("userId") int userId,
		        org.springframework.data.domain.Pageable pageable);
	
	
	
	
	
	
}
