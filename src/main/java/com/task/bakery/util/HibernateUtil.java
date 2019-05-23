package com.task.bakery.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.task.bakery.entity.Bakery;
import com.task.bakery.entity.Pack;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;

	static {
		try {
			loadSessionFactory();
		} catch (Exception e) {
			System.err.println("Exception while initializing hibernate util.. ");
			e.printStackTrace();
		}
	}

	public static void loadSessionFactory() {

		Configuration configuration = new Configuration();
		configuration.configure("/hibernate.cfg.xml");
		configuration.addAnnotatedClass(Bakery.class);
		configuration.addAnnotatedClass(Pack.class);
		ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
				.build();
		sessionFactory = configuration.buildSessionFactory(srvcReg);
	}

	public static Session getSession() throws HibernateException {

		Session retSession = null;
		try {
			retSession = sessionFactory.openSession();
		} catch (Throwable t) {
			System.err.println("Exception while getting session.. ");
			t.printStackTrace();
		}
		if (retSession == null) {
			System.err.println("session is discovered null");
		}

		return retSession;
	}

	public static void shutdown() {
		getSession().close();
	}
}
