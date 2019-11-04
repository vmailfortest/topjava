package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

//@ActiveProfiles({"hsqldb", "datajpa"})
@ActiveProfiles({"postgres", "datajpa"})
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
}
