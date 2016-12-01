package com.practo.urlshortener.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.practo.urlshortener.models.URLModel;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestComponent
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class AnalyticsServiceTest {

	@Autowired
	AnalyticsService analyticsService;

	@Test
	public void getURL_test() {
		List<URLModel> url = analyticsService.getURLs(1, 1, 1);
		Assert.assertTrue(url != null);
	}

	@Test
	public void getURLs_test() {
		Assert.assertTrue(analyticsService.getURLs(1) != null);
	}

	// @Test
	// public void saveURLVisit_test_1() {
	// try {
	// analyticsService.saveURLVisit("1","1.1.1.1","referer.com","Chrome");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	// @Test
	// public void saveURLVisit_test_2() {
	// try {
	// analyticsService.saveURLVisit("1","1.1.1.1","www.test.com","Mozilla");
	// } catch (IOException e) {
	// Assert.assertFalse(true);
	// }
	// }

	// @Test
	// public void getAnalytics_test_2() {
	// AnalyticsModel model = analyticsService.getAnalytics("1");
	// Assert.assertTrue(model!=null);
	// }

}
