package com.abhishek.leaveapplication.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.dao.UserDAO;
import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;

@TransactionConfiguration
@Transactional
public class UserDaoImpl extends DaoImplBase implements UserDAO {

	public long createUser(User user) throws Exception {
		if (user == null || user.getUserName() == null
				|| user.getPassword() == null || user.getRoleType() == null) {
			throw new Exception(
					"ERROR: Username, password and role type are required.");
		}
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(user);
		return id;
	}

}
