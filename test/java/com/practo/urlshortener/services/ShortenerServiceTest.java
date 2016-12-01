package com.practo.urlshortener.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.practo.urlshortener.entities.Urls;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestComponent
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class ShortenerServiceTest {

	@Autowired
	ShortenerService shortenerService;

	@Test
	public void createShortURL_test_1() {
		String str = shortenerService.createShortURL("www.test1.com", 1);
		Assert.assertTrue(str != null);
	}

	// @Test
	// public void createShortURL_test_2() {
	// String str = shortenerService.createShortURL("", 1);
	// Assert.assertTrue(str == null);
	// }

	// @Test
	// public void getLongURL_test_1() {
	// Urls url = shortenerService.getLongURL("1");
	// Assert.assertTrue(url != null);
	// }

	@Test
	public void getLongURL_test_2() {
		Urls url = shortenerService.getLongURL("1*1");
		Assert.assertTrue(url == null);
	}

	// @Test
	// public void getURL_test_1() {
	// Urls url = shortenerService.getURL("www.test.com", 1);
	// Assert.assertTrue(url != null);
	// }

	// @Test
	// public void getURL_test_2() {
	// Urls url = shortenerService.getURL("www.test_1.com", 1);
	// Assert.assertTrue(url != null);
	// }
}
