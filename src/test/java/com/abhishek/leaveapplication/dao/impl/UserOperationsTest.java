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
		Role roleType = new Role();
		roleType.setRoleId(1);
		roleType.setRoleName("Admin");
		user.setUserName("Abhishek");
		user.setPassword("password");

		user.setRoleType(roleType);
		long id = userDaoImpl.createUser(user);
		assertEquals(1, id);
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
		roleCreated.setRoleId(1);
		roleCreated.setRoleName("Admin");
		long id = roleDaoImpl.createRole(roleCreated);
		assertEquals(1, id);
	}

	@Test
	public void getRole_ValidRoleId_ReturnsRole() throws Exception {
		Role roleCreated = new Role();
		roleCreated.setRoleId(0);
		roleCreated.setRoleName("Admin");
		long id = roleDaoImpl.createRole(roleCreated);
		assertEquals(1, id);
		Role roleReceived = roleDaoImpl.getRole(VALID_ROLE_ID);
		assertEquals(roleCreated, roleReceived);
	}

}
