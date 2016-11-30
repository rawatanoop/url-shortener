package com.practo.urlshortener.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

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

	@Autowired
	private ShortenerDao shortenerDao;
	
	private static HashMap<String,Integer> browsers_1 = new HashMap<String,Integer>();
	private static HashMap<String,Integer> countries_1 = new HashMap<String,Integer>();
	private static HashMap<String,Integer> referers_1 = new HashMap<String,Integer>();
	private static HashMap<Integer,String> browsers_2 = new HashMap<Integer,String>();
	private static HashMap<Integer,String> countries_2 = new HashMap<Integer,String>();
	private static HashMap<Integer,String> referers_2 = new HashMap<Integer,String>();
	private static boolean isInitialised;


	@Transactional
	public List<URLModel> getURLs(int userID, int pageNo, int pageSize) {
		return getModelList(analyticsDao.getURLs(userID, pageNo, pageSize));
	}
	
	@Transactional
	public List<URLModel> getURLs(int userID) {
		return getModelList(analyticsDao.getURLs(userID));
	}

	private void initializeRefererCaching() {
		List<Referer> list = analyticsDao.getRefererList();
		for (Iterator<Referer> iterator = list.iterator(); iterator.hasNext();) {
			Referer referer =  iterator.next();
			referers_1.put(referer.getName(),referer.getId() );
			referers_2.put(referer.getId(),referer.getName() );
		}
	}

	private void initializeCountryCaching() {
		List<Country> list = analyticsDao.getCountryList();
		for (Iterator<Country> iterator = list.iterator(); iterator.hasNext();) {
			Country country =  iterator.next();
			countries_1.put(country.getName(),country.getId() );
			countries_2.put(country.getId(),country.getName() );
		}
	}

	private void initializeBrowserCaching() {
		List<Browser> list = analyticsDao.getBrowserList();
		for (Iterator<Browser> iterator = list.iterator(); iterator.hasNext();) {
			Browser browser =  iterator.next();
			browsers_1.put(browser.getName(),browser.getId() );
			browsers_2.put(browser.getId(),browser.getName() );
		}
	}

	private List<URLModel> getModelList(List<Urls> urLs) {
		List<URLModel> models = new ArrayList<URLModel>();
		for (Iterator<Urls> iterator = urLs.iterator(); iterator.hasNext();) {
			Urls url = iterator.next();
			URLModel model = new URLModel(url.getUrl(), Utility.encode(url.getId()));
			models.add(model);
		}
		return models;
	}

	public void saveURLVisit(String shortURL, String ipAddress, String referer, String userAgent) throws IOException {
		if(!isInitialised){
			initialiseCaching();
		}

		analyticsDao.saveURLVisit(new UrlVisit( new Urls(Utility.decode(shortURL)), getBrowser(userAgent) ,getReferer(referer), getCountry(ipAddress)));
	}
	
	private void initialiseCaching() {
		
		initializeBrowserCaching();
		initializeCountryCaching();
		initializeRefererCaching();	
		isInitialised = true;
	}

	private Browser getBrowser(String userAgent){
		
		String browser = userAgent.split(" ")[0];
		if(browser==null||browser.isEmpty())
			browser = "unknown";
		
		if(browsers_1.containsKey(browser)){
			return new Browser(browsers_1.get(browser));
		}
		/****
		 * We will create an entry into Browser Database Table.
		 */
		Browser browEntity = analyticsDao.createBrowser(browser);
		browsers_1.put(browser, browEntity.getId());
		browsers_2.put( browEntity.getId(),browser);
		return browEntity;
		
	}
	private Referer getReferer(String referer){
		if(referer==null||referer.isEmpty())
			referer = "unknown";
		
		if(referers_1.containsKey(referer)){
			return new Referer(referers_1.get(referer));
		}
		/****
		 * We will create an entry into Referer Database Table.
		 */
		Referer refEntity = analyticsDao.createReferer(referer);
		referers_1.put(referer, refEntity.getId());
		referers_2.put( refEntity.getId(),referer);
		return refEntity;
		
	}
	private Country getCountry(String ipAddress) throws IOException{
		URL urll = new URL("http://ipinfo.io/" + ipAddress + "/country");
		BufferedReader reader = new BufferedReader(new InputStreamReader(urll.openStream()));
		String line;
		String country = "";
		while ((line = reader.readLine()) != null) {
			country += line;
		}
		reader.close();
		
		if(country==null|country.isEmpty())
			country = "unknown";
		
		if(countries_1.containsKey(country)){
			return new Country(countries_1.get(country));
		}
		/****
		 * We will create an entry into Country Database Table.
		 */
		Country countryEntity = analyticsDao.createCountry(country);
		countries_1.put(country, countryEntity.getId());
		countries_2.put(countryEntity.getId(),country);
		return countryEntity;
		
	
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AnalyticsModel getAnalytics(String shortURL) {
		if(!isInitialised){
			initialiseCaching();
		}
		Long urlId = Utility.decode(shortURL);
		Urls url = shortenerDao.getFullLink(urlId);
		if (url == null)
			return null;
		List browserAnytcs = analyticsDao.groupByBrowser(urlId);
		List locationAnytcs = analyticsDao.groupByLocation(urlId);
		List refererAnytcs = analyticsDao.groupByReferer(urlId);
		AnalyticsModel model = new AnalyticsModel(url.getUrl(), shortURL, 100, url.getCreatedAt(),
				fillClickProperties(browserAnytcs,browsers_2),
				 fillClickProperties(refererAnytcs,referers_2),fillClickProperties(locationAnytcs,countries_2));
		return model;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List fillClickProperties(List list,Map<Integer,String> cache){
		List filledList = new ArrayList<CountModel>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			 Object[] obj = (Object[]) iterator.next();
			 filledList.add(new CountModel(cache.get(obj[0]), (BigInteger) obj[1]));
		}
		return filledList;
	}

}
