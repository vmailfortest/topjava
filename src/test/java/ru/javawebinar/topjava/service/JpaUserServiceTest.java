package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

//@ActiveProfiles({"hsqldb", "jpa"})
@ActiveProfiles({"postgres", "jpa"})
public class JpaUserServiceTest extends AbstractUserServiceTest {
}
