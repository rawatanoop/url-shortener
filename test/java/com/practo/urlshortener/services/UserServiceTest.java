package com.practo.urlshortener.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.practo.urlshortener.entities.Users;
import com.practo.urlshortener.models.UserModel;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestComponent
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class UserServiceTest {

	@Autowired
	UserService userService;

	@Test
	public void registerUser_test_1() {
		UserModel user = new UserModel();
		user.setName("test");
		user.setEmailID("test@Test.com");
		user.setPassword("test");
		int id = userService.registerUser(user);
		Assert.assertTrue(id > 0);
	}

	@Test
	public void registerUser_test_2() {
		UserModel user = new UserModel();
		user.setName("test");
		user.setEmailID("test");
		user.setPassword("test");
		int id = userService.registerUser(user);
		Assert.assertTrue(id == -1);
	}

	@Test
	public void isVaildUser_test_1() {
		UserModel user = new UserModel();
		user.setPassword("test");
		user.setEmailID("test@Test.com");
		Users entiry = userService.isVaildUser(user);
		Assert.assertTrue(entiry != null);
	}

	@Test
	public void isVaildUser_test_2() {
		UserModel user = new UserModel();
		user.setEmailID("test@Test.com");
		user.setPassword("test_1");
		Users entiry = userService.isVaildUser(user);
		Assert.assertTrue(entiry == null);
	}

}
