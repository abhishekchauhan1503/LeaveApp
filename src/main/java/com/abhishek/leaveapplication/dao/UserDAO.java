package com.abhishek.leaveapplication.dao;

import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;

public interface UserDAO {
public long createUser(User user) throws Exception;
public User getUser(String userName, long id) throws Exception;
}
