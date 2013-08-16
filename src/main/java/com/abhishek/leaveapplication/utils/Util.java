package com.abhishek.leaveapplication.utils;

import java.util.Date;

import javax.annotation.Resource;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.abhishek.leaveapplication.dao.MessageDAO;
import com.abhishek.leaveapplication.dao.RoleDAO;
import com.abhishek.leaveapplication.dao.UserDAO;
import com.abhishek.leaveapplication.dao.impl.MessageDaoImpl;
import com.abhishek.leaveapplication.model.Application;
import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;

@ContextConfiguration(locations = { "classpath:/META-INF/spring/app-context.xml" })
public class Util {

	/**
	 * @param args
	 */

	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	private User user;
	private Role role;

	public Util() {
		super();
		System.out.println("Generated Util");
	}

	public void viewUser() {
		System.out.println(user.getUserName());
	}

	@Autowired
	private UserDAO userDao;

	@Autowired
	private RoleDAO roleDao;

	@Autowired
	private MessageDAO messageDao;

	public static void exportSchema() {
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Role.class);
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(Application.class);
		config.addAnnotatedClass(Message.class);
		config.configure("hibernate.cfg.xml");

		new SchemaExport(config).create(true, false);
	}

	public void testRoles() {

		try {

			user.setRoleType(role);
			System.out.println("ROLE: " + user.getRoleType().getRoleName());
			roleDao.createRole(role);
			userDao.createUser(user);
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

	}

	public void testMessageCreation() {
		User regUser = new User();
		regUser.setId(2);
		User manager = new User();
		manager.setId(1);
		Date date = new Date();
		String content = "Update";
		Message message = new Message();
		message.setContent(content);
		message.setCreationDate(date);
		message.setRead(false);
		message.setFrom(regUser);
		message.setTo(manager);
		logger.info("Testing message creation");

		try {
			long id = messageDao.saveNewMessage(message);
			System.out.println("Message Saved. ID = " + id);
			logger.info("Message Saved");

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			System.out.println("ERROR Saving message\n" + ex);
			logger.error("ERROR Saving message", ex);
		}
	}

	public void testUnreadMessageCount() {
		long userId = 1;
		logger.info("Testing count");

		try {
			long count = messageDao.findNumberOfUnreadMessagesForUser(userId);
			System.out.println("Count retrieved = " + count);
			logger.info("Count retrieved");
		} catch (Exception ex) {
			System.out.println("ERROR retrieving count\n" + ex);
			logger.error("ERROR retrieving count", ex);
		}
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"META-INF/spring/app-context.xml");
		BeanFactory factory = context;
		Util util = (Util) factory.getBean("util");
		User newUser = util.getUser();
		System.out.println(newUser.getUserName());
		// util.testRoles();
		// exportSchema();
		// util.testMessageCreation();
		util.testUnreadMessageCount();
	}

	public Role getRole() {
		return role;
	}

	@Resource(name = "roleTest")
	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	@Resource(name = "userTest")
	public void setUser(User user) {
		this.user = user;
	}

}
