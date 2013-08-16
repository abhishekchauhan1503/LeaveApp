package com.abhishek.leaveapplication.dao.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.dao.DataRetrievalFailureException;

public class MessageTest {

	@Test(expected=DataRetrievalFailureException.class)
	public void saveNewMessageInvalidInput_ThrowsException() throws Exception{
		
	}

}
