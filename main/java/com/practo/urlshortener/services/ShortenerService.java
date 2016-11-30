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
import com.practo.urlshortener.models.URLModel;

@Service
public class ShortenerService {

	@Autowired
	private ShortenerDao shortenerDao;

	@Transactional
	public Urls getLongURL(String shortLink) {
		String fullLink = null;
		if (Utility.isValidShortLink(shortLink)) {
			return shortenerDao.getFullLink(Utility.decode(shortLink));
		}
		return null;
	}

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
