package com.abhishek.leaveapplication.dao;

import java.util.ArrayList;

import com.abhishek.leaveapplication.model.Application;
import com.abhishek.leaveapplication.model.User;

public interface ApplicationDAO {
	public long createNewApplication(Application application) throws Exception;
	public long updateApplication(Application application) throws Exception;
	public Application getApplication(long id) throws Exception;
	public ArrayList<Application> getAllApplicationsForUser(User user) throws Exception;
	public ArrayList<Application> getAllPendingApplicationsForUser(User user) throws Exception;

}

