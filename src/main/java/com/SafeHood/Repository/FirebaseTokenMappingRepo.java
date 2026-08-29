package com.SafeHood.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SafeHood.Entities.FirebaseTokenMapping;

public interface FirebaseTokenMappingRepo extends JpaRepository<FirebaseTokenMapping, Integer> {

	@Query("SELECT f FROM FirebaseTokenMapping f " +
		       "WHERE f.society.society_Id = :societyId " +
		       "AND f.userId = :userId " +
		       "AND f.role = :role")
		Optional<FirebaseTokenMapping> findToken(
		        @Param("societyId") Integer societyId,
		        @Param("userId") Integer userId,
		        @Param("role") String role
		);
	
	@Query("SELECT f FROM FirebaseTokenMapping f " +
		       "WHERE f.society.society_Id = :societyId " +
		       "AND f.is_Emrgency = true")
		List<FirebaseTokenMapping> findEmergencyUsers(
		        @Param("societyId") Integer societyId
		);
	
	Optional<FirebaseTokenMapping> findByUserId(Integer userId);
	
	@Query("SELECT f FROM FirebaseTokenMapping f " +
		       "WHERE f.society.society_Id = :societyId " +
		       "AND f.role = :role")
		List<FirebaseTokenMapping> findAllBySocietyAndRole(
		        @Param("societyId") Integer societyId,
		        @Param("role") String role
		);
	
	@Query("SELECT f FROM FirebaseTokenMapping f " +
		       "WHERE f.society.society_Id = :societyId " +
		       "AND f.userId = :userId " +
		       "AND f.role = :role")
		List<FirebaseTokenMapping> findResidentTokens(
		        @Param("societyId") Integer societyId,
		        @Param("userId") Integer userId,
		        @Param("role") String role
		);
	
	@Query("SELECT f FROM FirebaseTokenMapping f " +
		       "WHERE f.society.society_Id = :societyId")
		List<FirebaseTokenMapping> findAllBySociety(
		        @Param("societyId") Integer societyId
		);
	@Query("SELECT f FROM FirebaseTokenMapping f " +
		       "WHERE f.society.society_Id = :societyId " +
		       "AND f.role IN :roles")
		List<FirebaseTokenMapping> findBySocietyAndRoles(
		        @Param("societyId") Integer societyId,
		        @Param("roles") List<String> roles
		);
	
}
