package com.practo.urlshortener.services;

import java.net.URI;
import java.net.URISyntaxException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.urlshortener.Logger;
import com.practo.urlshortener.Utility;
import com.practo.urlshortener.daos.ShortenerDao;
import com.practo.urlshortener.entities.Urls;
import com.practo.urlshortener.entities.Users;

@Service
public class ShortenerService {

	@Autowired
	private ShortenerDao shortenerDao;

	Logger logger = Logger.getInstance(UserService.class);

	/***
	 * Returns the long url corresponding to the shortLink.
	 * 
	 * @param shortLink
	 * @return
	 */
	@Transactional
	public Urls getLongURL(String shortLink) {
		logger.info("Returning the long url for " + shortLink);
		if (Utility.isValidShortLink(shortLink)) {
			return shortenerDao.getFullLink(Utility.decode(shortLink));
		}
		logger.info("No long-url info is there for " + shortLink);
		return null;
	}

	/***
	 * Creates and returns a short url for the given long url for the particuar
	 * userID.
	 * 
	 * @param longUrl
	 * @param userID
	 * @return
	 */
	@Transactional
	public String createShortURL(String longUrl, int userID) {
		logger.info("Creating a short url for " + longUrl);
		try {
			URI uri = new URI(longUrl);
			return Utility.encode(shortenerDao.createShortURL(new Urls(longUrl, new Users(userID))));
		} catch (URISyntaxException e) {
			logger.info(longUrl + " is not a valid url");
		}
		logger.info("Can not create short url for " + longUrl + " user with userID : " + userID);
		return null;
	}

	/****
	 * Returns the row object corresponding to particular longURL created by the
	 * userID.
	 * 
	 * @param longUrl
	 * @param userID
	 * @return
	 */
	@Transactional
	public Urls getURL(String longUrl, int userID) {
		logger.info("Returning the url-details for " + longUrl + " with userID: " + userID);
		try {
			URI uri = new URI(longUrl);
			return shortenerDao.getURL(new Urls(longUrl, new Users(userID)));

		} catch (URISyntaxException e) {
			logger.info("Can't return the url-details for invalid" + longUrl);
		}
		logger.info("Can not return the url-details for " + longUrl + " with userID: " + userID);
		return null;
	}

}
