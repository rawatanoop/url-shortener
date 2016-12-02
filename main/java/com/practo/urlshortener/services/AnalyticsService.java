package com.practo.urlshortener.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.urlshortener.Logger;
import com.practo.urlshortener.Utility;
import com.practo.urlshortener.daos.AnalyticsDao;
import com.practo.urlshortener.daos.ShortenerDao;
import com.practo.urlshortener.entities.Browser;
import com.practo.urlshortener.entities.Country;
import com.practo.urlshortener.entities.Referer;
import com.practo.urlshortener.entities.UrlVisit;
import com.practo.urlshortener.entities.Urls;
import com.practo.urlshortener.models.AnalyticsModel;
import com.practo.urlshortener.models.CountModel;
import com.practo.urlshortener.models.URLModel;

@Service
public class AnalyticsService {

	@Autowired
	private AnalyticsDao analyticsDao;

	Logger logger = Logger.getInstance(UserService.class);

	@Autowired
	private ShortenerDao shortenerDao;

	private static HashMap<String, Integer> browsers_1 = new HashMap<String, Integer>();
	private static HashMap<String, Integer> countries_1 = new HashMap<String, Integer>();
	private static HashMap<String, Integer> referers_1 = new HashMap<String, Integer>();
	private static HashMap<Integer, String> browsers_2 = new HashMap<Integer, String>();
	private static HashMap<Integer, String> countries_2 = new HashMap<Integer, String>();
	private static HashMap<Integer, String> referers_2 = new HashMap<Integer, String>();
	private static boolean isInitialised;

	@Transactional
	public List<URLModel> getURLs(int userID, int pageNo, int pageSize) {
		return getModelList(analyticsDao.getURLs(userID, pageNo, pageSize));
	}

	@Transactional
	public List<URLModel> getURLs(int userID) {
		return getModelList(analyticsDao.getURLs(userID));
	}

	/***
	 * This method must be called before any Browser,Country/Referer table is
	 * used to read/write. This method will initialise the caching for these
	 * tables.
	 */
	private void initialiseCaching() {
		logger.info("Initialising the caching");
		initializeBrowserCaching();
		initializeCountryCaching();
		initializeRefererCaching();
		isInitialised = true;
		logger.info("Initialised the caching");
	}

	/***
	 * Caching all countries into maps so that the reading from data bases can
	 * be avoided.
	 */
	private void initializeCountryCaching() {
		logger.info("Initialising the caching for All Countries");
		List<Country> list = analyticsDao.getCountryList();
		for (Iterator<Country> iterator = list.iterator(); iterator.hasNext();) {
			Country country = iterator.next();
			countries_1.put(country.getName(), country.getId());
			countries_2.put(country.getId(), country.getName());
		}
		logger.info("Initialised successfully the caching for All Countries");
	}

	/**
	 * Caching all browsers into maps so that reading from data bases can be
	 * avoided.
	 */
	private void initializeBrowserCaching() {
		logger.info("Initialising the caching for All Browsers");
		List<Browser> list = analyticsDao.getBrowserList();
		for (Iterator<Browser> iterator = list.iterator(); iterator.hasNext();) {
			Browser browser = iterator.next();
			browsers_1.put(browser.getName(), browser.getId());
			browsers_2.put(browser.getId(), browser.getName());
		}
		logger.info("Initialised successfully the caching for All Browsers");
	}

	/***
	 * Caching all referers into maps so that reading from data bases can be
	 * avoided.
	 */
	private void initializeRefererCaching() {
		logger.info("Initialising the caching for All Refereres");
		List<Referer> list = analyticsDao.getRefererList();
		for (Iterator<Referer> iterator = list.iterator(); iterator.hasNext();) {
			Referer referer = iterator.next();
			referers_1.put(referer.getName(), referer.getId());
			referers_2.put(referer.getId(), referer.getName());
		}
		logger.info("Initialised successfully the caching for All Refereres");
	}

	private List<URLModel> getModelList(List<Urls> urLs) {
		List<URLModel> models = new ArrayList<URLModel>();
		if (urLs == null)
			return models;
		for (Iterator<Urls> iterator = urLs.iterator(); iterator.hasNext();) {
			Urls url = iterator.next();
			URLModel model = new URLModel(url.getUrl(), Utility.encode(url.getId()));
			models.add(model);
		}
		return models;
	}

	public void saveURLVisit(String shortURL, String ipAddress, String referer, String userAgent) throws IOException {
		logger.info("Trying to save a url-click info for short-url : " + shortURL);
		if (!isInitialised) {
			initialiseCaching();
		}
		analyticsDao.saveURLVisit(new UrlVisit(new Urls(Utility.decode(shortURL)), getBrowser(userAgent),
				getReferer(referer), getCountry(ipAddress)));
		logger.info("Saved successfully a url-click info for short-url : " + shortURL);
	}

	/***
	 * This method looks for the ID of the browser from caching and if it's not
	 * therer it creates a entry in database and updates the caching too.
	 * 
	 * @param userAgent
	 * @return
	 */
	private Browser getBrowser(String userAgent) {

		String browser = Utility.getBrowserName(userAgent);
		if (browser == null || browser.isEmpty())
			browser = "unknown";

		if (browsers_1.containsKey(browser)) {
			return new Browser(browsers_1.get(browser));
		}
		/****
		 * We will create an entry into Browser Database Table.
		 */
		Browser browEntity = analyticsDao.createBrowser(browser);
		browsers_1.put(browser, browEntity.getId());
		browsers_2.put(browEntity.getId(), browser);
		return browEntity;

	}

	/***
	 * This method looks for the ID of the referer from caching and if it's not
	 * there it creates a entry in database and updates the caching too.
	 * 
	 * @param referer
	 * @return
	 */
	private Referer getReferer(String referer) {
		if (referer == null || referer.isEmpty())
			referer = "unknown";

		if (referers_1.containsKey(referer)) {
			return new Referer(referers_1.get(referer));
		}
		/****
		 * We will create an entry into Referer Database Table.
		 */
		Referer refEntity = analyticsDao.createReferer(referer);
		referers_1.put(referer, refEntity.getId());
		referers_2.put(refEntity.getId(), referer);
		return refEntity;

	}

	/**
	 * This method looks for the ID of the Country from caching and if it's not
	 * there it creates a entry in database and updates the caching too.
	 *
	 * @param ipAddress
	 * @return
	 * @throws IOException
	 */
	private Country getCountry(String ipAddress) throws IOException {
		URL urll = new URL("http://ipinfo.io/" + ipAddress + "/country");
		BufferedReader reader = new BufferedReader(new InputStreamReader(urll.openStream()));
		String line;
		String country = "";
		while ((line = reader.readLine()) != null) {
			country += line;
		}
		reader.close();

		if (country == null | country.isEmpty())
			country = "unknown";

		if (countries_1.containsKey(country)) {
			return new Country(countries_1.get(country));
		}
		/****
		 * We will create an entry into Country Database Table.
		 */
		Country countryEntity = analyticsDao.createCountry(country);
		countries_1.put(country, countryEntity.getId());
		countries_2.put(countryEntity.getId(), country);
		return countryEntity;

	}

	/****
	 * This method return the analytics for the given short-url, the analytics
	 * include clicks count for different browsers,locations and referers.
	 * 
	 * @param shortURL
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AnalyticsModel getAnalytics(String shortURL) {
		logger.info("Trying to get the analytics info for short-url : " + shortURL);
		if (!isInitialised) {
			initialiseCaching();
		}
		Long urlId = Utility.decode(shortURL);
		Urls url = shortenerDao.getFullLink(urlId);
		if (url == null) {
			logger.info("The short-url : " + shortURL + " is not a valid row in database");
			return null;
		}

		AnalyticsModel model = new AnalyticsModel(url.getUrl(), shortURL, new BigInteger("0"), url.getCreatedAt());
		List browserAnytcs = fillClickProperties(analyticsDao.groupByBrowser(urlId), browsers_2);
		List locationAnytcs = fillClickProperties(analyticsDao.groupByLocation(urlId), countries_2);
		List refererAnytcs = fillClickProperties(analyticsDao.groupByReferer(urlId), referers_2);
		for (Iterator iterator = browserAnytcs.iterator(); iterator.hasNext();) {
			CountModel countModel = (CountModel) iterator.next();
			model.setTotalCount(model.getTotalCount().add(countModel.getCount()));
		}
		model.setBrowsersAnytcs(browserAnytcs);
		model.setLocationAnytcs(locationAnytcs);
		model.setRefererAnytcs(refererAnytcs);
		logger.info("Successfully retrieved the analytics info for short-url : " + shortURL);
		return model;
	}

	/***
	 * This methods fill the Browser/Refereres/Country details.
	 * 
	 * @param list
	 * @param cache
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List fillClickProperties(List list, Map<Integer, String> cache) {
		List filledList = new ArrayList<CountModel>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			filledList.add(new CountModel(cache.get(obj[0]), (BigInteger) obj[1]));
		}
		return filledList;
	}

}
