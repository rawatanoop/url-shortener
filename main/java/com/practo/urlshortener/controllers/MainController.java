package com.practo.urlshortener.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.practo.urlshortener.Utility;
import com.practo.urlshortener.entities.Urls;
import com.practo.urlshortener.models.AnalyticsModel;
import com.practo.urlshortener.models.URLModel;
import com.practo.urlshortener.services.AnalyticsService;
import com.practo.urlshortener.services.ShortenerService;

import inti.ws.spring.exception.client.UnauthorizedException;

@Controller
public class MainController {
	@Autowired
	private ShortenerService shortenerService;

	@Autowired
	private AnalyticsService analyticsService;

	@RequestMapping(value = "/api/shortener", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public URLModel createShortURL(@RequestBody URLModel url, HttpSession session) throws Exception {
		if (url == null || url.getLongURL() == null || url.getLongURL().isEmpty()
				|| !Utility.isValidURL(url.getLongURL()))
			throw new IllegalArgumentException("Invalid Parameters : LongUrl");
		if (session == null | session.getAttribute(Utility.UserID_Session) == null)
			throw new UnauthorizedException("Invalid user or session is logged-out");
		int userID = (int) session.getAttribute(Utility.UserID_Session);
		Urls oldUrl = shortenerService.getURL(url.getLongURL().trim(), userID);
		if (oldUrl != null) {
			return new URLModel(oldUrl.getUrl(), Utility.encode(oldUrl.getId()));
		}
		String longUrl = url.getLongURL().trim();
		String shortUrl = shortenerService.createShortURL(longUrl, userID);
		if (shortUrl != null)
			return new URLModel(longUrl, shortUrl);
		throw new Exception("Error during creating short-url");
	}

	@RequestMapping(value = "/api/shortener", method = RequestMethod.GET)
	@ResponseBody
	public String getLongURL(String shortUrl) {
		if (shortUrl == null || shortUrl.isEmpty())
			return null;
		String url = shortenerService.getLongURL(shortUrl.trim()).getUrl();
		return url;
	}

	@RequestMapping(value = "/api/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<URLModel> getAllUrls(Integer pageNo, Integer pageSize, HttpSession session) {

		if (session != null & session.getAttribute(Utility.UserID_Session) != null) {
			int userID = (int) session.getAttribute(Utility.UserID_Session);
			if (pageNo == null | pageSize == null) {
				return analyticsService.getURLs(userID);
			}
			if (pageNo > 0 && pageSize > 0)
				return analyticsService.getURLs(userID, pageNo, pageSize);
		}
		return null;
	}

	@RequestMapping(value = "/go/{shortURL}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void redirect(@PathVariable(value = "shortURL") String shortURL, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String url = shortenerService.getLongURL(shortURL).getUrl();
		if (url != null) {
			analyticsService.saveURLVisit(shortURL, request.getRemoteAddr(), request.getHeader(HttpHeaders.REFERER),
					request.getHeader(HttpHeaders.USER_AGENT));
			response.sendRedirect(url);
		}

		else
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@RequestMapping(value = "/analytics/{shortURL}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public AnalyticsModel getAnalytics(@PathVariable(value = "shortURL") String shortURL, HttpSession session)
			throws UnauthorizedException {
		if (session.getAttribute(Utility.UserID_Session) == null)
			throw new UnauthorizedException("User is not logged-in");
		Urls url = shortenerService.getLongURL(shortURL);
		if (url != null && url.getUserID().getId().equals(session.getAttribute(Utility.UserID_Session))) {
			return analyticsService.getAnalytics(shortURL);
		}
		throw new UnauthorizedException("User is not allowed to access these details");
	}

}
