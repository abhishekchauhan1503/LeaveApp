package com.abhishek.leaveapplication.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.dao.RoleDAO;
import com.abhishek.leaveapplication.dao.UserDAO;
import com.abhishek.leaveapplication.model.Role;

@TransactionConfiguration
@Transactional
@Repository
public class RoleDaoImpl extends DaoImplBase implements RoleDAO {

	
	public long createRole(Role roleType) throws Exception {
		if (roleType == null) {
			throw new DataRetrievalFailureException("ERROR: Role is required");
		}
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(roleType);
		return id;
	}

	public Role getRole(long roleId) throws Exception {
		if (Long.valueOf(roleId) == null) {
			throw new DataRetrievalFailureException(
					"ERROR: Role id is required");
		}
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Role.class);
		criteria.add(Restrictions.eq("id", roleId));
		Role roleType = (Role) criteria.uniqueResult();
		return roleType;
	}

	}
