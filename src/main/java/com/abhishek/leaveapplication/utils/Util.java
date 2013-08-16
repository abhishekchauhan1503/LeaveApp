package com.abhishek.leaveapplication.utils;

import javax.annotation.Resource;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import com.abhishek.leaveapplication.dao.RoleDAO;
import com.abhishek.leaveapplication.dao.UserDAO;
import com.abhishek.leaveapplication.model.Application;
import com.abhishek.leaveapplication.model.Messages;
import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;

@ContextConfiguration(locations = { "classpath:/META-INF/spring/app-context.xml" })
public class Util {

	/**
	 * @param args
	 */
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

	public static void exportSchema() {
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Role.class);
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(Application.class);
		config.addAnnotatedClass(Messages.class);
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

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"META-INF/spring/app-context.xml");
		BeanFactory factory = context;
		Util util = (Util) factory.getBean("util");
		User newUser = util.getUser();
		System.out.println(newUser.getUserName());
		// util.testRoles();
		exportSchema();
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
