package com.practo.urlshortener.services;

import java.net.URI;
import java.net.URISyntaxException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.urlshortener.Utility;
import com.practo.urlshortener.daos.ShortenerDao;
import com.practo.urlshortener.entities.Urls;
import com.practo.urlshortener.entities.Users;

@Service
public class ShortenerService {

	@Autowired
	private ShortenerDao shortenerDao;

	/***
	 * Returns the long url corresponding to the shortLink.
	 * 
	 * @param shortLink
	 * @return
	 */
	@Transactional
	public Urls getLongURL(String shortLink) {
		if (Utility.isValidShortLink(shortLink)) {
			return shortenerDao.getFullLink(Utility.decode(shortLink));
		}
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
		try {
			URI uri = new URI(longUrl);
			return Utility.encode(shortenerDao.createShortURL(new Urls(longUrl, new Users(userID))));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
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
		try {
			URI uri = new URI(longUrl);
			return shortenerDao.getURL(new Urls(longUrl, new Users(userID)));

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

}
