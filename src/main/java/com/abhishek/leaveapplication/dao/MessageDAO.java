package com.abhishek.leaveapplication.dao;

import com.abhishek.leaveapplication.model.Message;

public interface MessageDAO {
	public long saveNewMessage(Message message) throws Exception;
	public long findNumberOfUnreadMessagesForUser(long userId) throws Exception;
}
