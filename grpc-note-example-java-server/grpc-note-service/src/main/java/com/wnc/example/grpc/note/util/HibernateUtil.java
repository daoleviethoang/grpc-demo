package com.wnc.example.grpc.note.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public HibernateUtil() {
        sessionFactory = createSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return HibernateUtil.sessionFactory;
    }

    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }
}
