package com.practo.urlshortener.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practo.urlshortener.Utility;
import com.practo.urlshortener.entities.Urls;
import com.practo.urlshortener.models.AnalyticsModel;
import com.practo.urlshortener.models.URLModel;
import com.practo.urlshortener.models.UserModel;
import com.practo.urlshortener.services.AnalyticsService;
import com.practo.urlshortener.services.ShortenerService;

@Controller
public class MainController {
	@Autowired
	private ShortenerService shortenerService;

	@Autowired
	private AnalyticsService analyticsService;

	@RequestMapping(value = "/api/shortener", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createShortURL(@RequestBody URLModel url, HttpSession session) {
		if(url==null|url.getLongURL()==null|url.getLongURL().isEmpty())
			return new ResponseEntity<String>("Invalid Parameters : LongUrl", HttpStatus.BAD_REQUEST);	
		if (session == null | session.getAttribute(Utility.UserID_Session) == null )
			return new ResponseEntity<String>("Invalid User", HttpStatus.UNAUTHORIZED);		
			int userID = (int) session.getAttribute(Utility.UserID_Session);
			Urls oldUrl = shortenerService.getURL(url.getLongURL(), userID);
			if(oldUrl!=null){
				return new ResponseEntity<String>(Utility.URL_Prefix+Utility.encode(oldUrl.getId()), HttpStatus.OK);
			}
			String shortUrl = shortenerService.createShortURL(url.getLongURL(), userID);
			if (shortUrl != null)
			return new ResponseEntity<String>(Utility.URL_Prefix+shortUrl, HttpStatus.CREATED);
		return new ResponseEntity<String>("Error during creating short-url", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/api/shortener", method = RequestMethod.GET)
	@ResponseBody
	public String getLongURL(String shortUrl) {
		String url = shortenerService.getLongURL(shortUrl).getUrl();
		return Utility.URL_Prefix+url;
	}

	@RequestMapping(value = "/api/list", method = RequestMethod.GET)
	@ResponseBody
	public List<URLModel> getAllUrls(Integer pageNo, Integer pageSize, HttpSession session) {
		
		if (session != null & session.getAttribute(Utility.UserID_Session) != null) {
			int userID = (int) session.getAttribute(Utility.UserID_Session);
			if(pageNo==null|pageSize==null){
				return analyticsService.getURLs(userID);
			}			
			return analyticsService.getURLs(userID, pageNo, pageSize);
		}
		return null;
	}


	@RequestMapping(value = "/go/{shortURL}", method = RequestMethod.GET)
	@ResponseBody
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
	public AnalyticsModel getAnalytics(@PathVariable(value = "shortURL") String shortURL, HttpSession session) {
		Urls url = shortenerService.getLongURL(shortURL);
		 if(url !=null && url.getUserID().getId().equals(session.getAttribute(Utility.UserID_Session)))
		{
			return analyticsService.getAnalytics(shortURL);
		}
		 else
			 return null;

	}

}
