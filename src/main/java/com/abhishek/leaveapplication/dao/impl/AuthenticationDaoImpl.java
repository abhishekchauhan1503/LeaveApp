package com.abhishek.leaveapplication.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.dao.AuthenticationDAO;
import com.abhishek.leaveapplication.model.User;

@TransactionConfiguration
@Transactional
@Repository
public class AuthenticationDaoImpl extends DaoImplBase implements
		AuthenticationDAO {

	public User authenticateUserAndReturnProfileIfExists(String userName,
			String password) throws Exception {
		if (userName == null || password == null) {
			throw new DataRetrievalFailureException(
					"ERROR: Username and password are required");
		}
		Session session = sessionFactory.getCurrentSession();
		// Criteria criteria = session.createCriteria(User.class);
		// criteria.add(Restrictions.eqProperty("userName", userName));
		// criteria.add(Restrictions.eqProperty("password", password));
		String hql = "from User where userName = :userName and password = :password";
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		User authenticatedUser = (User) query.uniqueResult();// (User)
																// criteria.uniqueResult();
		return authenticatedUser;
	}
}
