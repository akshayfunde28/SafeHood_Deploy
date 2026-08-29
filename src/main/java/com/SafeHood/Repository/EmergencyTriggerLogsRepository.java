package com.SafeHood.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Emergency_Trigger_logs;
import com.SafeHood.Entities.Society;

public interface EmergencyTriggerLogsRepository extends JpaRepository<Emergency_Trigger_logs, Integer> {
	List<Emergency_Trigger_logs>
	findBySocietyAndTriggeredAtAfterOrderByTriggeredAtDesc(
	        Society society,
	        LocalDateTime date);
}
