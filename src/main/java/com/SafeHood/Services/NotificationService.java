package com.SafeHood.Services;

import com.SafeHood.DTO.GuestNotificationDTO;
import com.SafeHood.Entities.Complaint;
import com.SafeHood.Entities.EventHallBooking;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.FirebaseTokenMapping;
import com.SafeHood.Entities.Guard;
import com.SafeHood.Entities.Notice;
import com.SafeHood.Entities.PaymentDetails;
import com.SafeHood.Entities.PaymentRecord;
import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.User;
import com.SafeHood.Repository.FirebaseTokenMappingRepo;
import com.SafeHood.Repository.GuardRepo;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Repository.UserRepo;
import com.google.firebase.messaging.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	@Autowired
	private SocietyRepo societyRepo;
	@Autowired
	private GuardRepo guardRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private FirebaseTokenMappingRepo tokenRepo;

	// update token base on user id and society id
	public String updateFcmTokenByUsername(
	        String username,
	        Integer userId,
	        String role,
	        String token) {

	    // 🔍 Step 1: Get society
	    Society society = societyRepo.getSocietyBySocietyName(username);

	    if (society == null) {
	        throw new RuntimeException("❌ Society not found");
	    }

	    Integer societyId = society.getSociety_Id();

	    // 🔍 Step 2: Find mapping
	    FirebaseTokenMapping mapping = tokenRepo
	            .findToken(societyId, userId, role)
	            .orElseThrow(() ->
	                    new RuntimeException("❌ Token mapping not found"));

	    // 🔥 IMPORTANT FIX
	    token = URLDecoder.decode(token, StandardCharsets.UTF_8);

	    // 🔄 Step 3: Update token
	    mapping.setFcmToken(token);

	    tokenRepo.save(mapping);

	    return "✅ FCM Token Updated Successfully";
	}

	// Clear FCM token when user logs out
	public String clearFcmToken(String username, Integer userId, String role) {
		Society society = societyRepo.getSocietyBySocietyName(username);
		if (society == null)
			throw new RuntimeException("Society not found");

		Integer societyId = society.getSociety_Id();

		FirebaseTokenMapping mapping = tokenRepo.findToken(societyId, userId, role)
				.orElseThrow(() -> new RuntimeException("Token mapping not found"));

		mapping.setFcmToken(null); // clear token on logout
		tokenRepo.save(mapping);

		return "Token cleared";
	}

// Complaint push notification only to the manager 
	public String complaintPushNotification(String societyUsername, Complaint complaint) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(societyUsername)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get all MANAGER tokens
			List<FirebaseTokenMapping> managers = tokenRepo.findAllBySocietyAndRole(societyId, "MANAGER");

			if (managers.isEmpty()) {
				return "No managers found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = managers.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens available";
			}

			// 🔹 4. Build message
			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(complaint.getComplaint_Title())
							.setBody(complaint.getComplaint_Description()).build())
					.putData("type", "complaint").build();

			// 🔥 5. Send (UPDATED METHOD)
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount() + " managers";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Event hall booking notification to all MANAGERS
	public String eventBookingNotification(String societyUsername, EventHallBooking booking) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(societyUsername)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get all MANAGER tokens
			List<FirebaseTokenMapping> managers = tokenRepo.findAllBySocietyAndRole(societyId, "MANAGER");

			if (managers.isEmpty()) {
				return "No managers found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = managers.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens available";
			}

			// 🔹 4. Build message (ONLY required fields)
			String title = "Hall Booking by " + booking.getName();

			String body = booking.getName() + " requested to book the hall\n" + booking.getDate() + " | "
					+ booking.getTimeSlot() + "\nPurpose: " + booking.getPurpose();

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "hall_booking") // 🔥 identify notification type
					.build();

			// 🔹 5. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount() + " managers";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Notify resident about hall booking status update
	public String notifyResidentEventUpdate(String societyUsername, EventHallBooking booking) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(societyUsername)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get resident tokens
			List<FirebaseTokenMapping> residents = tokenRepo.findResidentTokens(societyId, booking.getResidentID(),
					"RESIDENT");

			if (residents.isEmpty()) {
				return "No resident tokens found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = residents.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens available";
			}

			// 🔹 4. Create message
			String title = "Hall Booking Update 📢";

			String body = booking.getName() + ", your hall booking is " + booking.getStatus() + "\n" + "Date: "
					+ booking.getDate() + " | Time: " + booking.getTimeSlot() + "\n" + "Purpose: "
					+ booking.getPurpose() + (booking.getRemark() != null ? "\nRemark: " + booking.getRemark() : "");

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "event_status_update") // 🔥 for navigation
					.build();

			// 🔹 5. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Notification sent to " + response.getSuccessCount() + " resident(s)";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Notify resident when complaint status changes
	public String notifyComplaintStatus(String societyUsername, Complaint complaint) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(societyUsername)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get resident tokens
			List<FirebaseTokenMapping> residents = tokenRepo.findResidentTokens(societyId, complaint.getResident_Id(),
					"RESIDENT");

			if (residents.isEmpty()) {
				return "No resident tokens found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = residents.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens";
			}

			// 🔹 4. Create message
			String title = "Complaint Status Update 📢";

			String body = "Your complaint \"" + complaint.getComplaint_Title() + "\" is now "
					+ complaint.getComplaint_Status();

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "complaint_status") // 🔥 for navigation
					.build();

			// 🔹 5. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount();

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Notify ALL users (resident + guard + manager) about a notice
	public String notifyAllUsersNotice(String societyUsername, Notice notice) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(societyUsername)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get all tokens
			List<FirebaseTokenMapping> users = tokenRepo.findAllBySociety(societyId);

			if (users.isEmpty()) {
				return "No users found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = users.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens";
			}

			// 🔹 4. Build message
			String title = notice.getNotice_Title();

			String body = notice.getNotice_Description() + "\nDate: " + notice.getNotice_Date();

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "notice") // 🔥 for navigation
					.build();

			// 🔹 5. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount() + " users";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Notify ALL users about a society event
	public String notifyAllUsersEvent(String societyUsername, Events event) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(societyUsername)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get all tokens (all roles)
			List<FirebaseTokenMapping> users = tokenRepo.findAllBySociety(societyId);

			if (users.isEmpty()) {
				return "No users found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = users.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens";
			}

			// 🔹 4. Build notification
			String title = event.getEvent_Title();

			String body = event.getEvent_Description() + "\nDate: " + event.getEvent_Date() + "\nLocation: "
					+ event.getEvent_Location();

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "event") // 🔥 identify event notification
					.build();

			// 🔹 5. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount() + " users";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Notify RESIDENT + MANAGER about payment details (add/update)
	public String notifyPaymentDetails(String username, PaymentDetails paymentDetails, String action) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(username)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get RESIDENT + MANAGER tokens
			List<FirebaseTokenMapping> users = tokenRepo.findBySocietyAndRoles(societyId,
					List.of("RESIDENT", "MANAGER"));

			if (users.isEmpty()) {
				return "No users found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = users.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens";
			}

			// 🔹 4. Build message
			String title = "Payment Details " + action + " 💰";

			String body = "UPI: " + (paymentDetails.getUpiId() != null ? paymentDetails.getUpiId() : "N/A")
					+ "\nMobile: "
					+ (paymentDetails.getMobileNumber() != null ? paymentDetails.getMobileNumber() : "N/A")
					+ "\nAccount: "
					+ (paymentDetails.getAccountNumber() != null ? paymentDetails.getAccountNumber() : "N/A");

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "payment") // 🔥 for navigation
					.build();

			// 🔹 5. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount() + " users";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Notify RESIDENT + MANAGER about monthly maintenance
	public String notifyMaintenanceReminder(String username, double amount, String month) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(username)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get RESIDENT + MANAGER tokens
			List<FirebaseTokenMapping> users = tokenRepo.findBySocietyAndRoles(societyId,
					List.of("RESIDENT", "MANAGER"));

			if (users.isEmpty()) {
				return "No users found";
			}

			// 🔹 3. Extract valid tokens
			List<String> tokens = users.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens";
			}

			// 🔹 4. Build message
			String title = "Maintenance Reminder 💰";

			String body = "Monthly maintenance for " + month + " is generated.\n" + "Amount: ₹" + amount + "\n"
					+ "Please pay on time.";

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "maintenance") // 🔥 navigation key
					.build();

			// 🔹 5. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount() + " users";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	// Notify specific user about payment update
	public String notifyPaymentUpdate(String username, PaymentRecord record) {

		try {
			// 🔹 1. Get society
			Society society = societyRepo.findByUsername(username)
					.orElseThrow(() -> new RuntimeException("Society not found"));

			Integer societyId = society.getSociety_Id();

			// 🔹 2. Get userId from record
			Integer userId = record.getUser().getUser_Id();

			// 🔹 3. Get tokens for that user (RESIDENT)
			List<FirebaseTokenMapping> users = tokenRepo.findResidentTokens(societyId, userId, "RESIDENT");

			if (users.isEmpty()) {
				return "No tokens found for user";
			}

			// 🔹 4. Extract valid tokens
			List<String> tokens = users.stream().map(FirebaseTokenMapping::getFcmToken)
					.filter(token -> token != null && !token.isBlank()).toList();

			if (tokens.isEmpty()) {
				return "No valid tokens";
			}

			// 🔹 5. Build message
			String title = "Payment Updated 💰";

			String body = "Your payment has been updated\n" + "Amount: ₹" + record.getTotalAmount() + "\n" + "Status: "
					+ record.getStatus() + (record.getFineAmount() > 0 ? "\nFine: ₹" + record.getFineAmount() : "")
					+ (record.getFineReason() != null ? "\nReason: " + record.getFineReason() : "");

			MulticastMessage message = MulticastMessage.builder().addAllTokens(tokens)
					.setNotification(Notification.builder().setTitle(title).setBody(body).build())
					.putData("type", "payment_update") // 🔥 for navigation
					.build();

			// 🔹 6. Send
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

			return "Sent to " + response.getSuccessCount();

		} catch (Exception e) {
			e.printStackTrace();
			return "Error sending notification";
		}
	}

	
	// Send emergency alert ONLY to users with is_Emrgency = true
public String sendEmergencyAlert(
        String societyUsername,
        Integer userId,
        String role,
        String locationLink) {

    try {

        // 🔹 Normalize role
        role = role != null ? role.toUpperCase().trim() : "";

        // 🔹 1. Get society
        Society society = societyRepo.findByUsername(societyUsername)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        Integer societyId = society.getSociety_Id(); 

        // 🔥 2. Get ONLY emergency-enabled users
        List<FirebaseTokenMapping> emergencyUsers =
                tokenRepo.findEmergencyUsers(societyId);

        if (emergencyUsers.isEmpty()) {
            return "No emergency users found";
        }

        // 🔹 3. Extract valid tokens
        List<String> tokens = emergencyUsers.stream()
                .map(FirebaseTokenMapping::getFcmToken)
                .filter(token -> token != null && !token.isBlank())
                .toList();

        if (tokens.isEmpty()) {
            return "No valid emergency tokens";
        }

        // 🔹 4. Sender details
        String senderName = "Unknown";
        String senderFlat = "N/A";
        String senderContact = "N/A";
        String roleMessage = "";

        // =========================================================
        // 🔥 MANAGER
        // =========================================================
        if ("MANAGER".equals(role)) {

            senderName = "Society Manager";
            senderFlat = "Management Office";
            senderContact = "N/A";

            roleMessage =
                    "🚨 Society Manager needs immediate help!";

        }

        // =========================================================
        // 🔥 RESIDENT
        // =========================================================
        else if ("RESIDENT".equals(role)) {

            if (userId == null) {
                return "User ID required for RESIDENT";
            }

            User user = userRepo.findById(userId)
                    .orElseThrow(() ->
                            new RuntimeException("Resident not found"));

            // Safety check
            if (user.getSociety() == null
                    || user.getSociety().getSociety_Id() != societyId) {

                return "Resident does not belong to this society";
            }

            senderName = user.getUser_name();
            senderFlat = user.getFlat_No();
            senderContact = user.getUser_Phone();

            roleMessage =
                    senderName + " needs immediate help!";

        }

        // =========================================================
        // 🔥 GUARD
        // =========================================================
        else if ("GUARD".equals(role)) {

            if (userId == null) {
                return "Guard ID required for GUARD";
            }

            Guard guard = guardRepo.findById(userId)
                    .orElseThrow(() ->
                            new RuntimeException("Guard not found"));

            // Safety check
            if (guard.getSociety() == null
                    || guard.getSociety().getSociety_Id() != societyId) {

                return "Guard does not belong to this society";
            }

            senderName = guard.getGuard_Name();
            senderFlat = "Security Gate";
            senderContact = guard.getGuard_Phone();

            roleMessage =
                    senderName + " needs immediate help!";

        }

        // =========================================================
        // 🔥 DEFAULT
        // =========================================================
        else {

            roleMessage =
                    "🚨 Someone needs immediate help!";

        }

        // 🔹 5. Notification Content
        String title = "🚨 EMERGENCY ALERT";

        String body =
                roleMessage
                + "\n\n🏠 Flat no: " + senderFlat
                + "\n📞 Contact: " + senderContact
                + "\n\n📍 Live Location:\n"
                + locationLink;

        // 🔹 6. Build FCM message
        MulticastMessage message = MulticastMessage.builder()

                .addAllTokens(tokens)

                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )

                // 🔥 custom data
                .putData("type", "emergency_alert")
                .putData("userId",
                        userId != null ? userId.toString() : "")
                .putData("role", role)
                .putData("senderName", senderName)
                .putData("senderFlat", senderFlat)
                .putData("senderContact", senderContact)
                .putData("locationLink", locationLink)

                // 🔥 Android Config
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setPriority(AndroidConfig.Priority.HIGH)
                                .setNotification(
                                        AndroidNotification.builder()
                                                .setChannelId("emergency_channel")
                                                .setSound("emergency_sound")
                                                .build()
                                )
                                .build()
                )

                // 🔥 iOS Config
                .setApnsConfig(
                        ApnsConfig.builder()
                                .setAps(
                                        Aps.builder()
                                                .setSound("emergency_sound.mp3")
                                                .build()
                                )
                                .build()
                )

                .build();

        // 🔹 7. Send
        BatchResponse response =
                FirebaseMessaging.getInstance()
                        .sendEachForMulticast(message);
return "Emergency alert sent to "
                + response.getSuccessCount()
                + " users";

    } catch (Exception e) {

        e.printStackTrace();

        return "Error sending emergency alert: "
                + e.getMessage();
    }
}

	
	// add and remove the member form the emergency list 
	public String toggleEmergency(Integer userId) {

		Optional<FirebaseTokenMapping> optional = tokenRepo.findByUserId(userId);

		if (optional.isPresent()) {

			FirebaseTokenMapping token = optional.get();

			Boolean currentStatus = token.getIs_Emrgency();

			if (currentStatus == null) {
				currentStatus = false;
			}

			token.setIs_Emrgency(!currentStatus);

			tokenRepo.save(token);

			return "Emergency Status Changed To : " + token.getIs_Emrgency();
		}

		return "User Not Found";
	}
	
	// Send guest entry notification to specific resident
	public String sendGuestNotification(
	        String societyUsername,
	        Integer residentId,
	        GuestNotificationDTO guestData) {

	    try {

	        // 🔹 1. Get society
	        Society society = societyRepo.findByUsername(societyUsername)
	                .orElseThrow(() -> new RuntimeException("Society not found"));

	        Integer societyId = society.getSociety_Id();

	        // 🔹 2. Get resident token
	        List<FirebaseTokenMapping> residents =
	                tokenRepo.findResidentTokens(
	                        societyId,
	                        residentId,
	                        "RESIDENT"
	                );

	        if (residents.isEmpty()) {
	            return "No resident token found";
	        }

	        // 🔹 3. Extract valid tokens
	        List<String> tokens = residents.stream()
	                .map(FirebaseTokenMapping::getFcmToken)
	                .filter(token -> token != null && !token.isBlank())
	                .toList();

	        if (tokens.isEmpty()) {
	            return "No valid tokens available";
	        }

	        // 🔹 4. Build notification message
	        String title = "Guest Entry Alert 🚪";

	        String body =
	                "Guest: " + guestData.getGuest_Name()
	                + "\nPurpose: " + guestData.getGuest_Purpose()
	                + "\nEntry Time: " + guestData.getGuest_EntryTime()
	                + "\nPhone: " + guestData.getGuest_Phone()
	                + "\nAddress: " + guestData.getGuest_Address()
	                + (guestData.getVehicle_Information() != null
	                   && !guestData.getVehicle_Information().isBlank()
	                        ? "\nVehicle: " + guestData.getVehicle_Information()
	                        : "");

	        MulticastMessage message = MulticastMessage.builder()
	                .addAllTokens(tokens)
	                .setNotification(Notification.builder()
	                        .setTitle(title)
	                        .setBody(body)
	                        .build())
	                .putData("type", "guest_entry")
	                .build();

	        // 🔹 5. Send notification
	        BatchResponse response =
	                FirebaseMessaging.getInstance()
	                        .sendEachForMulticast(message);

	        return "Notification sent to "
	                + response.getSuccessCount()
	                + " device(s)";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error sending notification";
	    }
	}

}
