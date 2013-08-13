package com.abhishek.leaveapplication.dao.impl;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class UserOperationsTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SessionFactory sessionFactory;

	private UserDaoImpl userDaoImpl;
	private RoleDaoImpl roleDaoImpl;
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
		userDaoImpl.setSessionFactory(sessionFactory);
		roleDaoImpl.setSessionFactory(sessionFactory);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = Exception.class)
	public void createUser_NullUser_ThrowsException() throws Exception {
		userDaoImpl.createUser(null);
	}

	@Test(expected = Exception.class)
	public void createUser_NullAttributes_ThrowsException() throws Exception {
		userDaoImpl.createUser(new User());
	}

	@Test
	public void createUser_ValidUser_ReturnsOne() throws Exception {
		User user = new User();
		Role managerRole = new Role();
		managerRole.setRoleId(1);
		managerRole.setRoleName(MANAGER_ROLE_NAME);
		Role userRole = new Role();
		userRole.setRoleId(2);
		userRole.setRoleName(USER_ROLE_NAME);

		user.setUserName(USER_USERNAME);
		user.setPassword(PASSWORD);
		User manager = new User();
		manager.setPassword(PASSWORD);
		manager.setUserName(MANAGER_USERNAME);
		manager.setRoleType(managerRole);
		user.setManagerId(manager);
		user.setRoleType(userRole);
		roleDaoImpl.createRole(managerRole);
		roleDaoImpl.createRole(userRole);
		long managerId = userDaoImpl.createUser(manager);
		long id = userDaoImpl.createUser(user);
		User receivedManager = userDaoImpl.getUser(MANAGER_USERNAME, 0);
		User receivedUser = userDaoImpl.getUser(USER_USERNAME, 0);
		assertNotNull(receivedManager);
		assertEquals(managerRole, receivedManager.getRoleType());
		assertEquals(MANAGER_USERNAME, receivedManager.getUserName());
		assertEquals(PASSWORD, receivedManager.getPassword());
		assertNotNull(receivedUser);
		assertEquals(userRole, receivedUser.getRoleType());
		assertEquals(USER_USERNAME, receivedUser.getUserName());
		assertEquals(PASSWORD, receivedUser.getPassword());
		assertEquals(receivedManager, receivedUser.getManagerId());
	}

	@Test(expected = Exception.class)
	public void createRole_NullRole_ThrowsException() throws Exception {
		roleDaoImpl.createRole(null);
	}

	@Test
	public void createRole_ValidRoleWithNoAttributes_ReturnsIdAsZero()
			throws Exception {
		long id = roleDaoImpl.createRole(new Role());
		assertEquals(0, id);
	}

	@Test
	public void createRole_ValidRoleWithAttributes_ReturnsIdAsOne()
			throws Exception {
		Role roleCreated = new Role();
		roleCreated.setRoleId(MANAGER_ID);
		roleCreated.setRoleName(MANAGER_ROLE_NAME);
		long id = roleDaoImpl.createRole(roleCreated);
		assertEquals(MANAGER_ID, id);
	}

	@Test
	public void getRole_ValidRoleId_ReturnsRole() throws Exception {
		Role roleCreated = new Role();
		roleCreated.setRoleId(MANAGER_ID);
		roleCreated.setRoleName(MANAGER_ROLE_NAME);
		long id = roleDaoImpl.createRole(roleCreated);
		assertEquals(MANAGER_ID, id);
		Role roleReceived = roleDaoImpl.getRole(MANAGER_ID);
		assertEquals(roleCreated, roleReceived);
	}

}
