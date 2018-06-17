package com.lzy.jurisdcition.ssh.common.sys.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kuyu on 2016/9/16.
 */
public class MyOpenSessionInViewFilter extends OpenSessionInViewFilter{

//    @Override
//    protected Session openSession(SessionFactory sessionFactory)
//            throws DataAccessResourceFailureException {
//        Session session = super.openSession(sessionFactory);
//        session.setFlushMode(FlushMode.AUTO);
//
//        return session;
//    }



}
