package com.abhishek.leaveapplication.dao;

import com.abhishek.leaveapplication.model.User;

public interface AuthenticationDAO {
public User authenticateUserAndReturnProfileIfExists(String userName, String password) throws Exception;
}
