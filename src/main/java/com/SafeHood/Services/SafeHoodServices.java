package com.SafeHood.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SafeHood.Entities.Complaint;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.Guard;
import com.SafeHood.Entities.Guest;
import com.SafeHood.Entities.Notice;
import com.SafeHood.Entities.SOS_Alert;
import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.User;
import com.SafeHood.Repository.ComplaintRepo;
import com.SafeHood.Repository.EventRepo;
import com.SafeHood.Repository.GuardRepo;
import com.SafeHood.Repository.GuestRepo;
import com.SafeHood.Repository.NoticeRepo;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Repository.SosRepo;
import com.SafeHood.Repository.UserRepo;

@Service
public class SafeHoodServices {
	
	@Autowired
	private ComplaintRepo complaintRepo;
	@Autowired
	private EventRepo eventRepo;
	@Autowired
	private GuardRepo guardRepo;
	@Autowired
	private GuestRepo guestRepo;
	@Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private SocietyRepo societyRepo ;
	@Autowired
	private SosRepo sosRepo ;
	@Autowired
	private UserRepo userRepo;
	
	
	
	
	  //--------------------->>>>>>>>>>>>>>>.Save All Data............<<<<<<<<<<<<<<<------------------------------
	
	// Society  #SAVE and UPDATE
	public void saveSociety(Society society) {
		this.societyRepo.save(society);
	}
	// Complaints	 #SAVE 
	public void 	saveComplaints(Society society,Complaint complaint) {
		complaint.setSociety(society);
		society.getComplaint().add(complaint);
//		societyRepo.save(society); // only if society is not exist alredy in that case we save society 
		this.complaintRepo.save(complaint);
	}
	//Events #SAVE and UPDATE
	public void saveEvents(Society society,Events event) {
		event.setSociety(society);
		society.getEvent().add(event);
		this.eventRepo.save(event);
	}
	//Guard #SAVE and UPDATE
	public void saveGuard(Society society,Guard guard) {
		guard.setSociety(society);
		society.getGuard().add(guard);
		this.guardRepo.save(guard);
	}
	//Guest #SAVE and UPDATE
	public void saveGuest(Society society,Guest guest) {
		guest.setSociety(society);
		society.getGuest().add(guest);
		this.guestRepo.save(guest);
	}
	//Notice  #SAVE and UPDATE
	public void saveNotice (Society society,Notice notice) {
		notice.setSociety(society);
		society.getNotice().add(notice);
		this.noticeRepo.save(notice);
	}
	//SOS  #SAVE and UPDATE
	public void saveSos(Society society,SOS_Alert sos_Alert) {
		sos_Alert.setSociety(society);
		society.getSos().add(sos_Alert);
		this.sosRepo.save(sos_Alert);
	}
	// User  #SAVE and UPDATE
	public void saveUser(Society society,User user) {
		user.setSociety(society);
		society.getUser().add(user);
		this.userRepo.save(user);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
