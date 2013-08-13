package com.abhishek.leaveapplication.utils;

import javax.annotation.Resource;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.leaveapplication.dao.RoleDAO;
import com.abhishek.leaveapplication.dao.UserDAO;
import com.abhishek.leaveapplication.dao.impl.RoleDaoImpl;
import com.abhishek.leaveapplication.dao.impl.UserDaoImpl;
import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;

@ContextConfiguration(locations = {"classpath:/META-INF/spring/app-context.xml"})
public class Util{

	/**
	 * @param args
	 */
	private User user;

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

		config.configure("hibernate.cfg.xml");

		new SchemaExport(config).create(true, false);
	}

	public void testRoles() {

		try {
			 Role role = new Role();
			 role.setRoleName("Admin");
			 
			 //roleDao.createRole(role);

			 Role newRole = roleDao.getRole(0);
			 System.out.println("Role: "+newRole.getRoleName());
			 userDao.createUser(user);
			 System.out.println("Done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+ e);
		}

	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
	    BeanFactory factory = context;
		Util util = (Util)factory.getBean("util");
		User newUser = util.getUser();
		System.out.println(newUser.getUserName());
		util.testRoles();
	}

	public User getUser() {
		return user;
	}

	@Resource(name = "userTest")
	public void setUser(User user) {
		this.user = user;
	}

}
