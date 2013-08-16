package com.abhishek.leaveapplication.dao.impl;

import org.hibernate.Query;

import org.hibernate.Session;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplication.dao.MessageDAO;

@TransactionConfiguration
@Transactional
@Repository
public class MessageDaoImpl extends DaoImplBase implements MessageDAO {

	public long saveNewMessage(Message message) throws Exception {

		if (message.getFrom() == null || message.getTo() == null
				|| message.getCreationDate() == null
				|| message.getContent() == null) {
			throw new DataRetrievalFailureException("Missing attributes");
		}
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(message);
		return id;
	}

	public long findNumberOfUnreadMessagesForUser(long userId) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM Message WHERE read = :false";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("false", false);
		long count = ((Long)query.uniqueResult()).longValue();//((Long) session.createQuery(hql).uniqueResult())
				//.longValue();

		return count;
	}

}
