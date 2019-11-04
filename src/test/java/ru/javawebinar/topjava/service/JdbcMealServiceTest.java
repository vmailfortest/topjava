package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

//@ActiveProfiles({"hsqldb", "jdbc"})
@ActiveProfiles({"postgres", "jdbc"})
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
