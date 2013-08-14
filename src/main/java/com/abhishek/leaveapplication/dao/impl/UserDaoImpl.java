package com.abhishek.leaveapplication.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Expectations;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.dao.UserDAO;
import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;

@TransactionConfiguration
@Transactional
@Repository
public class UserDaoImpl extends DaoImplBase implements UserDAO {

	public long createUser(User user) throws Exception {
		if (user == null || user.getUserName() == null
				|| user.getPassword() == null || user.getRoleType() == null) {
			throw new DataRetrievalFailureException(
					"ERROR: Username, password and role type are required.");
		}
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(user);
		return id;
	}

	public User getUser(String userName, long id) throws Exception {
		if (userName == null && id == 0) {
			throw new DataRetrievalFailureException(
					"Username or Id is required");
		}
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		if (userName != null) {
			criteria.add(Restrictions.eq("userName", userName));
		} else {
			criteria.add(Restrictions.eq("id", id));
		}
		User user = (User) criteria.uniqueResult();
		return user;
	}

}
