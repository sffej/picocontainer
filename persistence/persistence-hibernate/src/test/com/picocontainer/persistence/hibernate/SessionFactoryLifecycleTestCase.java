/*****************************************************************************
 * Copyright (C) 2003-2011 PicoContainer Committers. All rights reserved.    *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *****************************************************************************/

package com.picocontainer.persistence.hibernate;

import static com.picocontainer.tck.MockFactory.mockeryWithCountingNamingScheme;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * Test that lifecycle closes session factory
 */
public class SessionFactoryLifecycleTestCase {

	private Mockery mockery = mockeryWithCountingNamingScheme();
	
    @Test 
    public void canCloseSessionFactoryOnStop() throws Exception {
    	final SessionFactory sessionFactory = mockery.mock(SessionFactory.class);
    	mockery.checking(new Expectations() {{
    		one(sessionFactory).close();
    	}});
        SessionFactoryLifecycle lifecycle = new SessionFactoryLifecycle(sessionFactory);
        lifecycle.stop();
    }
    
    @Test(expected=HibernateException.class)
    public void cannotCloseSessionFactoryOnStop() throws Exception {
        final SessionFactory sessionFactory = mockery.mock(SessionFactory.class);
        mockery.checking(new Expectations() {{
            one(sessionFactory).close();
            will(throwException(new HibernateException("mock")));
        }});
        SessionFactoryLifecycle lifecycle = new SessionFactoryLifecycle(sessionFactory);
        lifecycle.stop();
    }

}
