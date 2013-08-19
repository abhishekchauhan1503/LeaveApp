package com.abhishek.leaveapplication.dao;

import java.util.ArrayList;

import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplication.model.User;

public interface MessageDAO {
	public long saveNewMessage(Message message) throws Exception;

	public long findNumberOfUnreadMessagesForUser(long userId) throws Exception;

	public ArrayList<Message> getAllMessagesForUser(User user) throws Exception;
}
