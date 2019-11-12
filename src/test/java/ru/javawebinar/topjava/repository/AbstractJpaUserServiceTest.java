package ru.javawebinar.topjava.repository;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

abstract public class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }
}