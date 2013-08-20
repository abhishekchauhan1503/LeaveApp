package com.abhishek.leaveapplication.dao.impl;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplication.model.User;
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
		long count = ((Long) query.uniqueResult()).longValue();// ((Long)
																// session.createQuery(hql).uniqueResult())
		// .longValue();

		return count;
	}

	public ArrayList<Message> getAllMessagesForUser(User user) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Message.class);
		criteria.add(Restrictions.eq("to", user));
		ArrayList<Message> messages = (ArrayList<Message>) criteria.list();
		return messages;
	}

	public long updateMessage(Message messageToUpdate) throws Exception {
		if (messageToUpdate.getId() <= 0) {
			throw new DataRetrievalFailureException("Invalid message Id");
		}
		String hql = "UPDATE Message SET read =:read WHERE id = :id";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("read", messageToUpdate.isRead());

		query.setParameter("id", messageToUpdate.getId());

		int result = query.executeUpdate();

		// session.update(application);
		return result;

	}

}
