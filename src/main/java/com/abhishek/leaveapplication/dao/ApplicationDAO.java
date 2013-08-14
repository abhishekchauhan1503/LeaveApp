package com.abhishek.leaveapplication.dao;

import com.abhishek.leaveapplication.model.Application;

public interface ApplicationDAO {
	public long createNewApplication(Application application) throws Exception;
	public long updateApplication(Application application) throws Exception;
	public Application getApplication(long id) throws Exception;
}
