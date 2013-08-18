package com.abhishek.leaveapplication.dao.impl;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.dao.ApplicationDAO;
import com.abhishek.leaveapplication.model.Application;
import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplication.model.User;

@TransactionConfiguration
@Transactional
@Repository
public class ApplicationDaoImpl extends DaoImplBase implements ApplicationDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationDaoImpl.class);

	public long createNewApplication(Application application) throws Exception {
		if (application.getFrom() == null
				|| application.getTo() == null
				|| application.getSubmissionDate() == null
				|| application.getContent() == null
				|| (application.getStatus() != 'P'
						&& application.getStatus() != 'R' && application
						.getStatus() != 'A')) {
			throw new DataRetrievalFailureException("Missing attributes");
		}
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(application);
		return id;
	}

	public long updateApplication(Application application) throws Exception {
		String attributeValues = "ID: " + application.getId() + "\nTo: "
				+ application.getTo().getId() + "\nFrom: "
				+ application.getFrom().getId() + "\nContent: "
				+ application.getContent() + "\nDate: "
				+ application.getSubmissionDate() + "\nStatus: "
				+ application.getStatus();

		logger.info("Attribute Values In DAO:\n" + attributeValues);

		if (application.getId() <= 0

				|| application.getContent() == null
				|| (application.getStatus() != 'P'
						&& application.getStatus() != 'R' && application
						.getStatus() != 'A'))

		{
			throw new DataRetrievalFailureException("Missing attributes");
		}
		String hql = "UPDATE Application SET status =:status, modificationDate = :modificationDate WHERE id = :id";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("status", application.getStatus());
		query.setParameter("modificationDate",
				application.getModificationDate());
		query.setParameter("id", application.getId());

		int result = query.executeUpdate();

		// session.update(application);
		return result;
	}

	public Application getApplication(long id) throws Exception {
		if (id <= 0) {
			throw new DataRetrievalFailureException(
					"Null or Inalid application Id");
		}
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Application.class);
		criteria.add(Restrictions.eq("id", id));
		return (Application) criteria.uniqueResult();
	}

	public ArrayList<Application> getAllApplicationsForUser(User user)
			throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Application.class);
		criteria.add(Restrictions.eq("to", user));
		ArrayList<Application> applications = (ArrayList<Application>) criteria
				.list();
		return applications;
	}

	public ArrayList<Application> getAllPendingApplicationsForUser(User user)
			throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Application.class);
		criteria.add(Restrictions.eq("to", user));
		criteria.add(Restrictions.eq("status", 'P'));
		ArrayList<Application> applications = (ArrayList<Application>) criteria
				.list();
		return applications;
	}
}
