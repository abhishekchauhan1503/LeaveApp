package com.abhishek.leaveapplication.dao.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.model.Application;
import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class ApplicationTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SessionFactory sessionFactory;

	private UserDaoImpl userDaoImpl;
	private RoleDaoImpl roleDaoImpl;
	private ApplicationDaoImpl applicationDaoImpl;
	private User from;
	private User to;
	private Role userRole;
	private Role managerRole;
	private Application application;
	private static final char DEFAULT_STATUS = 'P';
	private static final String DEFAULT_CONTENT = "Content";

	private static final long VALID_ROLE_ID = 1;
	private static final long DEFAULT_MANAGER_ID = 0;
	private static final String MANAGER_USERNAME = "manager";
	private static final String USER_USERNAME = "Abhishek";
	private static final String PASSWORD = "password";
	private static final String MANAGER_ROLE_NAME = "Manager";
	private static final String USER_ROLE_NAME = "User";
	private static final long MANAGER_ID = 1;
	private static final long USER_ID = 2;

	@Before
	public void setUp() throws Exception {

		userDaoImpl = new UserDaoImpl();
		roleDaoImpl = new RoleDaoImpl();
		applicationDaoImpl = new ApplicationDaoImpl();
		userDaoImpl.setSessionFactory(sessionFactory);
		roleDaoImpl.setSessionFactory(sessionFactory);
		applicationDaoImpl.setSessionFactory(sessionFactory);
	}

	@After
	public void tearDown() throws Exception {
	}

	public void initiallizeUsersAndRoles() throws Exception {
		from = new User();
		to = new User();

		managerRole = new Role();
		managerRole.setRoleId(1);
		managerRole.setRoleName(MANAGER_ROLE_NAME);

		userRole = new Role();
		userRole.setRoleId(2);
		userRole.setRoleName(USER_ROLE_NAME);

		from.setUserName(USER_USERNAME);
		from.setPassword(PASSWORD);
		from.setRoleType(userRole);

		to.setUserName(MANAGER_USERNAME);
		to.setPassword(PASSWORD);
		to.setRoleType(userRole);

		from.setManagerId(to);

		roleDaoImpl.createRole(managerRole);
		roleDaoImpl.createRole(userRole);
		userDaoImpl.createUser(from);
		userDaoImpl.createUser(to);

		Date submissionDate = new Date(2013, 7, 13);
		System.out.println(submissionDate);
		application = new Application();
		application.setContent(DEFAULT_CONTENT);
		application.setFrom(from);
		application.setTo(to);
		application.setSubmissionDate(submissionDate);
		application.setStatus(DEFAULT_STATUS);

	}

	@Test
	public void createNewApplication_ValidApplication_ReturnsIdOfApplication()
			throws Exception {
		initiallizeUsersAndRoles();
		long id = applicationDaoImpl.createNewApplication(application);
		assertNotSame(0, id);
	}

	@Test(expected = DataRetrievalFailureException.class)
	public void createNewApplication_NullSubmitter_ThrowsException()
			throws Exception {
		initiallizeUsersAndRoles();
		application.setFrom(null);
		applicationDaoImpl.createNewApplication(application);
	}

	@Test
	public void updateApplication_ValidParameters_ReturnsOne() throws Exception {
		initiallizeUsersAndRoles();

		long id = applicationDaoImpl.createNewApplication(application);
		assertNotSame(0, id);
		application.setStatus('A');
		application.setContent("New Content");
		long updateValue = applicationDaoImpl.updateApplication(application);
		assertEquals(1, updateValue);
	}

	@Test
	public void getApplication_ValidId_ReturnsApplication() throws Exception{
		initiallizeUsersAndRoles();

		long id = applicationDaoImpl.createNewApplication(application);
		assertNotSame(0, id);
		Application receivedApplication = applicationDaoImpl.getApplication(id);
		assertNotNull(receivedApplication);
		assertEquals(id, receivedApplication.getId());
		assertEquals(to, receivedApplication.getTo());
		assertEquals(from, receivedApplication.getFrom());
		assertEquals(DEFAULT_CONTENT, receivedApplication.getContent());
		assertEquals(DEFAULT_STATUS, receivedApplication.getStatus());
		
	}

}
