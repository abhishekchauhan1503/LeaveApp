package com.abhishek.leaveapplication.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataRetrievalFailureException;

import com.abhishek.leaveapplication.dao.ApplicationDAO;
import com.abhishek.leaveapplication.model.Application;

public class ApplicationDaoImpl extends DaoImplBase implements ApplicationDAO {

	public long createNewApplication(Application application) throws Exception {
		if(application.getFrom() == null || application.getTo() == null || application.getSubmissionDate() == null || application.getContent() ==null || (application.getStatus() != 'P' && application.getStatus() != 'R' && application.getStatus() != 'A'))
		{
			throw new DataRetrievalFailureException("Missing attributes");
		}
			Session session = sessionFactory.getCurrentSession();
		long id = (Long)session.save(application);
		return id;
	}

	public long updateApplication(Application application) throws Exception{
		if(application.getId()<=0 || application.getFrom() == null || application.getTo() == null || application.getSubmissionDate() == null || application.getContent() ==null || (application.getStatus() != 'P' && application.getStatus() != 'R' && application.getStatus() != 'A'))
			
		{
			throw new DataRetrievalFailureException("Missing attributes");
		}
		Session session = sessionFactory.getCurrentSession();
		session.update(application);
		return 1;
	}

	public Application getApplication(long id) throws Exception {
		if(id <=0){
			throw new DataRetrievalFailureException("Null or Inalid application Id");
		}
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Application.class);
		criteria.add(Restrictions.eq("id", id));
		return (Application)criteria.uniqueResult();
	}
	
	

}
