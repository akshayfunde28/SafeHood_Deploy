package com.SafeHood.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SafeHood.DTO.GuestNotificationDTO;
import com.SafeHood.Entities.Society;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Services.NotificationService;
import com.SafeHood.Services.SafeHoodServices;

@RestController
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private SocietyRepo societyRepo;
	@Autowired
	private SafeHoodServices safeHoodServices;

	// update fcm token when user login, download the app first time
	@PostMapping("/update-fcm-token")
	public ResponseEntity<?> updateFcmToken(@RequestParam String username,
			@RequestParam(required = false) Integer userId, @RequestParam String role, @RequestParam String token) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.getSocietyBySocietyName(username);

			if (society == null) {
				return ResponseEntity.status(404).body("❌ Society not found");
			}

			Integer finalUserId;

			// 🔥 2. Handle MANAGER case
			if ("MANAGER".equalsIgnoreCase(role)) {
				finalUserId = society.getSociety_Id(); // use societyId
			} else {
				if (userId == null) {
					return ResponseEntity.badRequest().body("❌ userId is required for this role");
				}
				finalUserId = userId;
			}

			// 🔹 3. Call service
			String result = notificationService.updateFcmTokenByUsername(username, finalUserId, role, token);

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			return ResponseEntity.status(500).body("🚨 Error: " + e.getMessage());
		}
	}

	// clear fcm token when log out type cases
	@PostMapping("/clear-fcm-token")
	public ResponseEntity<?> clearFcmToken(@RequestParam String username,
			@RequestParam(required = false) Integer userId, @RequestParam String role) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.getSocietyBySocietyName(username);

			if (society == null) {
				return ResponseEntity.status(404).body("❌ Society not found");
			}

			Integer finalUserId;

			// 🔹 2. Handle MANAGER
			if ("MANAGER".equalsIgnoreCase(role)) {
				finalUserId = society.getSociety_Id();
			} else {
				if (userId == null) {
					return ResponseEntity.badRequest().body("❌ userId required for role: " + role);
				}
				finalUserId = userId;
			}

			// 🔹 3. Call service
			String result = notificationService.clearFcmToken(username, finalUserId, role);

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			return ResponseEntity.status(500).body("🚨 Error: " + e.getMessage());
		}
	}

	// send Emergency alert
	@PostMapping("/send-emergency-alert")
	public ResponseEntity<?> sendEmergencyAlert(@RequestParam String username,
			@RequestParam(required = false) Integer userId, @RequestParam String role,
			@RequestParam String locationLink) {

		try {
			safeHoodServices.emergencyTriggerLog(username, userId, role, locationLink);
			String result = notificationService.sendEmergencyAlert(username, userId, role, locationLink);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("🚨 Error: " + e.getMessage());
		}
	}

	// send guest notification to resident
	@PostMapping("/{username}/guest-notification/{residentId}")
	public ResponseEntity<?> sendGuestNotification(@PathVariable String username, @PathVariable Integer residentId,
			@RequestBody GuestNotificationDTO guestData) {

		try {

			String result = notificationService.sendGuestNotification(username, residentId, guestData);

			return ResponseEntity.ok(result);

		} catch (Exception e) {

			return ResponseEntity.status(500).body("🚨 Error: " + e.getMessage());
		}
	}

}
